package com.example.booklyn.entities;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.booklyn.MainActivity;
import com.example.booklyn.R;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Hotel implements Parcelable {

    public static final String SELECTED_HOTEL = "SELECTED_HOTEL";
    public static ArrayList<Hotel> hotels = new ArrayList<>(10);

    private String location;
    private String telephone;
    private String email;
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
    private String mainPicture;
    // Дополнительные фотографии
    private ArrayList<String> additionalPictures;

    public Hotel(String name, String info, String location, String telephone, String email) {
        this.name = name;
        this.info = info;
        this.location = location;
        this.telephone = telephone;
        this.email = email;
        additionalPictures = new ArrayList<>();
        rates = new ArrayList<>();
        rooms = new ArrayList<>();
        mainPicture = "no_photo";
    }

    public Hotel(int ID, String name, String info, String namePicture, ArrayList<Rate> rates,
                 ArrayList<Room> rooms, ArrayList<String> images, String email, String telephone, String location) {
        this.ID = ID;
        this.name = name;
        this.info = info;
        this.mainPicture = namePicture;
        this.rates = rates;
        this.rooms = rooms;
        resetRate();
        this.additionalPictures = images;
        if (rooms != null) resetMinPrice();
        else minPrice = 0;
        this.email = email;
        this.location = location;
        this.telephone = telephone;
    }

    protected Hotel(Parcel in) {
        rooms = in.createTypedArrayList(Room.CREATOR);
        allRatingSum = in.readFloat();
        name = in.readString();
        info = in.readString();
        avgRate = in.readFloat();
        rateCount = in.createIntArray();
        minPrice = in.readInt();
        mainPicture = in.readString();
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

    public String getMainPicture() {
        return mainPicture;
    }

    public void resetMinPrice() {
        minPrice = Integer.MAX_VALUE;
        for (Room r : rooms) {
            minPrice = Math.min(r.getPrice(), minPrice);
        }
    }

    public int getMinPrice() {
        return minPrice;
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

    public void resetRate(){
        this.rateCount = makeRateCount(rates);
        this.allRatingSum = makeAllRate(rates);
        this.avgRate =  (float) allRatingSum / rates.size();
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

    public ArrayList<String> getAdditionalPictures() {
        return additionalPictures;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getTelephone() {
        return telephone;
    }

    public static ArrayList<Hotel> getHotelsByName(String name){
        ArrayList<Hotel> newHotels = new ArrayList<Hotel>(5);
        for (int i = 0; i < hotels.size(); i++) {
            if (hotels.get(i).getName().startsWith(name)){
                newHotels.add(hotels.get(i));
            }
        }
        return newHotels;
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
        parcel.writeString(mainPicture);
    }
}
