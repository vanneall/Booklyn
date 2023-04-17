package com.example.booklyn.entities;

import java.util.Comparator;

public class HotelPriceComparator implements Comparator<Hotel> {

    @Override
    public int compare(Hotel o1, Hotel o2) {
        return o1.getMinPrice() - o2.getMinPrice();
    }
}
