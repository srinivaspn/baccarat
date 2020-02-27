package com.penukonda.baccarat;

import com.google.common.base.Joiner;
import com.penukonda.baccarat.service.Game;
import com.penukonda.baccarat.service.Result;
import com.penukonda.baccarat.service.Shoe;
import com.penukonda.baccarat.service.ShoeEmptyException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="DragonStatsNoReset", value="/dragonstatsextended")
public class DragronStatsExtended extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Game game = new Game(new Shoe(8));
        int handCount =10000000;
        int shoeCount = 0, previousDragonIndex=0, dragonCount =0;
        HashMap<Integer,Integer> countBySpread = new HashMap<>();
        HashMap<Integer, Integer> occurrencesByFirstDragon = new HashMap<>();
        HashMap<Integer,Integer> dragonCountFrequencyMap = new HashMap<>();
        for(int i =0 ; i < handCount; i++){
            try {
                Result result = game.playHand();
                shoeCount++;
                if(result.equals(Result.DRAGON)){
                    if(previousDragonIndex != 0){
                        int spread = shoeCount - previousDragonIndex;
                        int count = 1;
                        if(countBySpread.containsKey(spread)){
                            count = countBySpread.get(spread);
                            count++;
                        }
                        countBySpread.put(spread,count);
                    }else{
                        int numOfOccurrences = 1;
                        if(occurrencesByFirstDragon.containsKey(shoeCount)){
                            numOfOccurrences = occurrencesByFirstDragon.get(shoeCount) + 1;
                        }
                        occurrencesByFirstDragon.put(shoeCount,numOfOccurrences);
                    }
                    previousDragonIndex = shoeCount;
                    dragonCount++;
                }
            }catch (ShoeEmptyException e){
                int frequency = 1;
                if(dragonCountFrequencyMap.containsKey(dragonCount)){
                    frequency = dragonCountFrequencyMap.get(dragonCount) + 1;
                }
                dragonCountFrequencyMap.put(dragonCount,frequency);
                game.initiaize();
                previousDragonIndex = 0;
                shoeCount =0;
                dragonCount = 0;

            }
        }
        ArrayList<String> output = new ArrayList<>();
        output.add("*********** SPREAD TO OCCURRENCES MAP***************");
        output.add("spread,count");
        for (Map.Entry<Integer, Integer> entry : countBySpread.entrySet()) {
            output.add(entry.getKey() + "," +entry.getValue());
        }
        output.add("*********** FIRST DRAGON INDEX***************");
        output.add("index,count");
        for (Map.Entry<Integer, Integer> entry : occurrencesByFirstDragon.entrySet()) {
            output.add(entry.getKey() + "," +entry.getValue());
        }
        output.add("*********** DRAGON FREQUENCY***************");
        output.add("dragoncount,count");
        for (Map.Entry<Integer, Integer> entry : dragonCountFrequencyMap.entrySet()) {
            output.add(entry.getKey() + "," +entry.getValue());
        }
        response.setContentType("text/plain");
        response.getWriter().println(Joiner.on("\n").join( output));
    }
}
