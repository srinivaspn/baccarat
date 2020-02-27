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
import java.util.List;

@WebServlet(name="DragonStatsNoReset", value="/dragoncounter")
public class DragonsCounter extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Game game = new Game(new Shoe(8));
        int handCount =10000000;
        int shoeCount = 0,  dragonCount = 0;
        List<Integer> dragonIndexes = new ArrayList<>();
        ArrayList<String> output = new ArrayList<>();
        for(int i =0 ; i < handCount; i++){
            try {
                Result result = game.playHand();
                shoeCount++;
                if(result.equals(Result.DRAGON)){
                    dragonCount++;
                    dragonIndexes.add(shoeCount);
                }
            }catch (ShoeEmptyException e){
                if(dragonCount > 1){
                    output.add(Joiner.on(",").join(dragonIndexes));
                }
                game.initiaize();
                shoeCount =0;
                dragonCount = 0;
                dragonIndexes.clear();
            }
        }


        response.setContentType("text/plain");
        response.getWriter().println(Joiner.on("\n").join(output));
    }
}
