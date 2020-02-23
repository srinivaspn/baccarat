package com.penukonda.baccarat;

import com.google.inject.Guice;
import com.penukonda.baccarat.service.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

@WebServlet(name="BaccaratServlet", value="/baccarat")
public class BaccaratServlet  extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<Result> results = new ArrayList<>();
        Game game = new Game(new Shoe(8));
        while (true){
            try {
                results.add(game.playHand());
            }catch (ShoeEmptyException e){
                break;
            }
        }

        response.setContentType("text/plain");
        response.getWriter().println(Joiner.on("\n").join( groupResults(results)));
    }

    private List<String> groupResults(List<Result> results) {
        List<String> grouped = new ArrayList<>();

        Result prev = results.get(0);
        StringBuffer stringBuffer = new StringBuffer(prev.toString());
        for(int i =1; i < results.size(); i++){
            Result current = results.get(i);
            if(current.equals(prev)){
                stringBuffer.append(", "+current.toString());
                continue;
            }else{
                grouped.add(stringBuffer.toString());
                stringBuffer = new StringBuffer(current.toString());
            }
            prev = current;
        }
        grouped.add(stringBuffer.toString());
        return grouped;
    }
}
