package com.penukonda.baccarat.service;

public enum Result {
    BANKER("BANKER"),
    PLAYER("PLAYER"),
    TIE("TIE"),
    DRAGON("!!!***DRAGON***!!!");
    private String value;
    Result(String value) {
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
