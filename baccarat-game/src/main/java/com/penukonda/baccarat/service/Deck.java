package com.penukonda.baccarat.service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    private final List<Card> cards;
    @Inject
    public Deck(){
        this.cards = new ArrayList<Card>();
        for (Suit suit: Suit.values()) {
            for (Value value: Value.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }

    public List<Card> getCards(){
        return cards;
    }
}
