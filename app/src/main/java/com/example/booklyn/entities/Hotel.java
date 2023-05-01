package com.example.booklyn.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Hotel implements Parcelable {

    public static final String SELECTED_HOTEL = "SELECTED_HOTEL";
    public static ArrayList<Hotel> hotels = new ArrayList<>(10);

    public ArrayList<Room> rooms;

    private float allRatingSum = 0;

    private int ID;

    // Название отеля
    private final String name;

    // Информация об отеля
    private String info;

    // Средний рейтинг
    private float avgRate;

    // Распределение по количеству звезд
    private int[] rateCount = new int[5];

    // Весь рейтинг
    private ArrayList<Rate> rates;

    // Цена за отель
    private int minPrice;

    // Главная картинка отеля
    private int mainPicture;

    // Дополнительные фотографии
    private ArrayList<Integer> additionalPictures;

    public Hotel(String name, int minPrice, int picture) {
        this.name = name;
        this.minPrice = minPrice;
        this.mainPicture = picture;

    }

    public Hotel(int ID, String name, String info, int picture, ArrayList<Rate> rates,
                 ArrayList<Room> rooms, ArrayList<Integer> images) {
        this.ID = ID;
        this.name = name;
        this.info = info;
        this.mainPicture = picture;
        this.rates = rates;
        this.rooms = rooms;
        this.rateCount = makeRateCount(rates);
        this.allRatingSum = makeAllRate(rates);
        this.avgRate =  (float) allRatingSum / rates.size();
        this.additionalPictures = images;
        resetMinPrice();
    }

    protected Hotel(Parcel in) {
        rooms = in.createTypedArrayList(Room.CREATOR);
        allRatingSum = in.readFloat();
        name = in.readString();
        info = in.readString();
        avgRate = in.readFloat();
        rateCount = in.createIntArray();
        minPrice = in.readInt();
        mainPicture = in.readInt();
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public float getAvgRate() {
        return (float)((int)(avgRate*10))/10;
    }

    public int getMainPicture() {
        return mainPicture;
    }

    public void resetMinPrice() {
        minPrice = Integer.MAX_VALUE;
        for (Room r : rooms) {
            minPrice = r.getPrice() < minPrice ? r.getPrice() : minPrice;
        }
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public int[] makeRateCount(ArrayList<Rate> rates){
        int[] count = new int[5];
        for (int i = 0; i < rates.size(); i++) {
            count[Math.round((rates.get(i).getRate() - 1))]++;
        }
        return count;
    }

    public float makeAllRate(ArrayList<Rate> rates){
        float allRate = 0;
        for (Rate r: rates) {
            allRate += r.getRate();
        }
        return allRate;
    }

    public void addRate(Rate rate) {
        rates.add(rate);

        allRatingSum += rate.getRate();
        avgRate = (float) allRatingSum / rates.size();
        rateCount[Math.round((rate.getRate() - 1))]++;
    }

    public int[] getRateCount() {
        return rateCount;
    }

    public ArrayList<Rate> getRates() {
        return rates;
    }

    public ArrayList<Integer> getAdditionalPictures() {
        return additionalPictures;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeTypedList(rooms);
        parcel.writeFloat(allRatingSum);
        parcel.writeString(name);
        parcel.writeString(info);
        parcel.writeFloat(avgRate);
        parcel.writeIntArray(rateCount);
        parcel.writeInt(minPrice);
        parcel.writeInt(mainPicture);
    }
}
