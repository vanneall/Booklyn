package com.example.booklyn.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Room implements Parcelable {

    public static final String SELECTED_ROOM = "selected_room";

    private String name;

    private String info;

    private int price;

    public Room(String name, String info, int price){
        this.name = name;
        this.info = info;
        this.price = price;
    }

    protected Room(Parcel in) {
        info = in.readString();
        price = in.readInt();
        name = in.readString();
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

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(info);
        parcel.writeInt(price);
        parcel.writeString(name);
    }
}
