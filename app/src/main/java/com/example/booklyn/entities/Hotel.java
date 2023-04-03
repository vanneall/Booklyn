package com.example.booklyn.entities;

import java.util.ArrayList;

public class Hotel {
    private String name;
    private String info;
    private float rate;
    private ArrayList<Rate> rates;
    private float price;
    private int mainPicture;
    private ArrayList<Integer> additionalPictures;


    public Hotel(String name, float price, int picture){
        this.name = name;
        this.price = price;
        this.mainPicture = picture;
    }

    public String getName() {
        return name;
    }

    public float getRate() {
        return rate;
    }

    public int getMainPicture() {
        return mainPicture;
    }

    public float getPrice() {
        return price;
    }
}
