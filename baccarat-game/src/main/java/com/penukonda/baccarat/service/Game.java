package com.penukonda.baccarat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game {
    private final Shoe shoe;
    public Game(Shoe shoe){
        this.shoe = shoe;
        initiaize();
    }

    public void initiaize(){
        shoe.shuffle();
    }

    public Result playHand(){
        Hand playerHand = new Hand();
        Hand bankerHand = new Hand();
        //initial draw
        playerHand.takeCard(getCardFromShoe());
        bankerHand.takeCard(getCardFromShoe());
        playerHand.takeCard(getCardFromShoe());
        bankerHand.takeCard(getCardFromShoe());
        //handle naturals
        if(playerHand.getValue()< 8 && bankerHand.getValue() < 8){
            // Handle Player
            if(playerHand.getValue() < 6){
                playerHand.takeCard(getCardFromShoe());
            }
            // Handle Banker
            switch (bankerHand.getValue()){
                case 0:
                case 1:
                case 2:
                    bankerHand.takeCard(getCardFromShoe());
                    break;
                case 3:
                    if(playerHand.getValue() != 8){
                        bankerHand.takeCard(getCardFromShoe());
                    }
                    break;
                case 4:
                    if(playerHand.getValue() >= 2 && playerHand.getValue() <= 7){
                        bankerHand.takeCard(getCardFromShoe());
                    }
                    break;
                case 5:
                    if(playerHand.getValue() >= 4 && playerHand.getValue() <= 7){
                        bankerHand.takeCard(getCardFromShoe());
                    }
                    break;
                case 6:
                    if(playerHand.getValue() == 6 || playerHand.getValue() == 7){
                        bankerHand.takeCard(getCardFromShoe());
                    }
                    break;
            }

        }
        return getResult(playerHand,bankerHand);
    }

    private Result getResult(Hand playerHand, Hand bankerHand) {
        if(playerHand.getValue() > bankerHand.getValue()){
            return Result.PLAYER;
        }else if(playerHand.getValue() == bankerHand.getValue()){
            return Result.TIE;
        }else{
            return bankerHand.cardCount() == 3 && bankerHand.getValue() ==7 ? Result.DRAGON : Result.BANKER;
        }
    }

    private Card getCardFromShoe(){
        Optional<Card> card = shoe.draw();
        if(card.isPresent()){
            return card.get();
        }
        throw new ShoeEmptyException("Shoe is empty.");
    }

    class Hand {
        private List<Card> cards;

        Hand(){
            cards = new ArrayList<>();
        }

        void takeCard(Card card){
            cards.add(card);
        }

        int getValue(){
           return cards.stream().map(card-> card.getValue()).mapToInt(Integer::intValue).sum() % 10 ;
        }

        int cardCount(){
            return cards.size();
        }
    }

}
