package com.example.booklyn.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Room implements Parcelable {

    public static final String SELECTED_ROOM = "selected_room";

    private String info;

    private int price;

    public Room(String info, int price){
        this.info = info;
        this.price = price;
    }

    protected Room(Parcel in) {
        info = in.readString();
        price = in.readInt();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public String getInfo() {
        return info;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(info);
        parcel.writeInt(price);
    }
}
