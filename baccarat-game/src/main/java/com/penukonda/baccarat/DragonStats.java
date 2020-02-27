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

@WebServlet(name="DragonStats", value="/dragonstats")
public class DragonStats  extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Game game = new Game(new Shoe(8));
        int handCount =1000000;
        int shoeCount = 0, previousDragonIndex=0;
        HashMap<Integer,Integer> countBySpread = new HashMap<>();
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
                    }
                    previousDragonIndex = shoeCount;
                }
            }catch (ShoeEmptyException e){
                game.initiaize();
                previousDragonIndex = 0;
                shoeCount =0;
            }
        }
        ArrayList<String> output = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : countBySpread.entrySet()) {
            output.add(entry.getKey() + "," +entry.getValue());
        }
        response.setContentType("text/plain");
        response.getWriter().println(Joiner.on("\n").join( output));
    }
}
