package com.example.booklyn.entities;

import java.util.Comparator;

public class HotelRateComparator implements Comparator<Hotel> {

    @Override
    public int compare(Hotel o1, Hotel o2) {
        return (int)(o1.getAvgRate() * 10 - o2.getAvgRate() * 10);
    }
}
