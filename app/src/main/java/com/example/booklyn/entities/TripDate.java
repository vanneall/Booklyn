package com.example.booklyn.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class TripDate extends Date implements Parcelable {

    private Date date;

    private long milliseconds;

    public TripDate(){
        date = new Date();
        milliseconds = date.getTime();
    }

    public TripDate(long milliseconds){
        date = new Date(milliseconds);
    }


    protected TripDate(Parcel in) {
        milliseconds = in.readLong();
        date = new Date(milliseconds);
    }

    public static final Creator<TripDate> CREATOR = new Creator<TripDate>() {
        @Override
        public TripDate createFromParcel(Parcel in) {
            return new TripDate(in);
        }

        @Override
        public TripDate[] newArray(int size) {
            return new TripDate[size];
        }
    };


    @Override
    public String toString() {
        return date.getDate() + "/" + date.getMonth() + "/" + date.getYear();
    }

    public Date getInnerDate() {
        return date;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(milliseconds);
    }
}
