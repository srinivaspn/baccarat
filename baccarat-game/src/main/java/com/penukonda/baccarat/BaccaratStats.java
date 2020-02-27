package com.penukonda.baccarat;


import com.penukonda.baccarat.service.Game;
import com.penukonda.baccarat.service.Result;
import com.penukonda.baccarat.service.Shoe;
import com.penukonda.baccarat.service.ShoeEmptyException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name="BaccaratStats", value="/baccaratstats")
public class BaccaratStats extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Game game = new Game(new Shoe(8));
        float countB = 0, countP =0, countT =0, countD=0;
        float handCount =100000;
        for(int i =0 ; i < handCount; i++){
            try {
                Result result = game.playHand();
                if(result.equals(Result.PLAYER)){
                    countP++;
                } else if(result.equals(Result.BANKER)){
                    countB++;
                }else if(result.equals(Result.TIE)){
                    countT++;
                }else if(result.equals(Result.DRAGON)){
                    countD++;
                    countB++;
                }
            }catch (ShoeEmptyException e){
                game.initiaize();
            }
        }
        float bankerP = countB / handCount;
        float playerP = countP / handCount;
        float tieP = countT / handCount;
        float dragonP = countD / handCount;
        response.setContentType("text/plain");
        response
                .getWriter()
                .println(
                        String.format(
                                "Banker : %s\n" +
                                        "Player : %s\n" +
                                        "Tie : %s\n" +
                                        "Dragon : %s",
                                String.valueOf(bankerP),
                                String.valueOf(playerP),
                                String.valueOf(tieP),
                                String.valueOf(dragonP)));
    }
}
