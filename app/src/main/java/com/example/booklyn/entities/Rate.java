package com.example.booklyn.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Rate implements Parcelable {
    private String info;
    private float rate;

    private Date date;

    public Rate(float rate, String info, Date date){
        this.rate = rate;
        this.info = info;
        this.date = date;
    }

    protected Rate(Parcel in) {
        info = in.readString();
        rate = in.readFloat();
    }

    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel in) {
            return new Rate(in);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };

    public float getRate() {
        return rate;
    }

    public String getInfo() {
        return info;
    }

    public String getDate() {return date.getDate() + "/" + date.getMonth() + "/" + date.getYear();}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(info);
        parcel.writeFloat(rate);
    }
}
