package com.example.booklyn.entities;

public class Rate {
    private String info;
    private float rate;

    public Rate(float rate, String info){
        this.rate = rate;
        this.info = info;
    }

    public float getRate() {
        return rate;
    }

    public String getInfo() {
        return info;
    }
}
