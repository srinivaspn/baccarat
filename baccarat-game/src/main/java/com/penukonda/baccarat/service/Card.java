package com.penukonda.baccarat.service;

import com.google.common.collect.ImmutableMap;

public class Card {
    private final Suit suit;
    private final Value value;
    private static ImmutableMap<Value, Integer> BACCARAT_VALUE_BY_FACE
            = ImmutableMap.<Value, Integer>builder()
            .put(Value.ACE,1)
            .put(Value.TWO,2)
            .put(Value.THREE,3)
            .put(Value.FOUR,4)
            .put(Value.FIVE,5)
            .put(Value.SIX,6)
            .put(Value.SEVEN,7)
            .put(Value.EIGHT,8)
            .put(Value.NINE,9)
            .put(Value.TEN,0)
            .put(Value.JACK,0)
            .put(Value.QUEEN,0)
            .put(Value.KING,0)
            .build();


    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public int getValue(){
        return BACCARAT_VALUE_BY_FACE.get(value);
    }
}
