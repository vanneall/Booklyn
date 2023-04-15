package com.example.booklyn.entities;

import androidx.annotation.NonNull;

public class TripDate implements Comparable<TripDate> {

    public TripDate(){
        day = 0;
        month = 0;
        year = 0;
    }

    private int day;
    private int month;
    private int year;

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(TripDate tripDate) {
        if (year > tripDate.year) return 1;
        if (month > tripDate.month) return 1;
        if (day > tripDate.day) return 1;
        return -1;
    }

    @NonNull
    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }
}
