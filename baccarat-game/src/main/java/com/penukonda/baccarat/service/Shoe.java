package com.penukonda.baccarat.service;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.IntStream;

public class Shoe {
    private final List<Card> cards;
    private Stack<Card> dealtCards = new Stack<Card>();
    private Stack<Card> undealtCards = new Stack<Card>();

    public Shoe( int decksCount){
        this.cards = new ArrayList<Card>();
        IntStream.range(1,decksCount).forEach(i -> cards.addAll( new Deck().getCards()));
    }

    public void shuffle(){
        Collections.shuffle(cards);
        undealtCards.addAll(cards);
        dealtCards = new Stack<Card>();
    }

    public Optional<Card> draw(){
        if(!undealtCards.empty()){
            dealtCards.add(undealtCards.peek());
            return Optional.of(undealtCards.pop());
        }
        return Optional.empty();
    }

}
