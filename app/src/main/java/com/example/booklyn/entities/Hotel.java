package com.example.booklyn.entities;

import com.example.booklyn.R;

import java.util.ArrayList;

public class Hotel {
    // TODO: Убрать
    public static ArrayList<Hotel> hotels = new ArrayList<>(3);

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

    // TODO: Убрать
    public static void addHotels(ArrayList<Hotel> hotels){
        hotels.add(new Hotel("Holiday Inn", 7000f, R.drawable.holiday_inn));
        hotels.get(0).setInfo("Holiday Inn — американская сеть гостиниц и дочерняя компания InterContinental Hotels Group. Компания является одной из крупнейших в мире гостиничных сетей и насчитывает 1173 действующих отелей и более чем 214 тысяч арендуемых номеров по состоянию на 30 сентября 2018 года.");
        hotels.add(new Hotel("Novotel", 2500f, R.drawable.novotel));
        hotels.get(1).setInfo("Новотель (англ. Novotel сокращение Новгородский отель) — бренд, под которым работает французская гостиничная сеть, входящая в состав группы Accor. «Новотель» представляет собой бренд стандартизированных отелей верхнего сегмента среднего класса. Сеть включает около четырёхсот гостиниц в шестидесяти странах мира. Отели Новотель располагаются преимущественно в крупнейших мегаполисах мира, бизнес-центрах и туристических направлениях.");
        hotels.add(new Hotel("Националь 5*", 3750f, R.drawable.nacional5));
        hotels.get(2).setInfo("Гости́ница «Национа́ль» — московский отель, расположенный на Моховой улице, 15/1. Построен в 1900—1902 годах по проекту архитектора Александра Иванова. На момент открытия считался одним из самых престижных в Москве. После Революции 1917 года несколько лет в здании размещалось общежитие для чиновников советского правительства. В 1932 году гостиницу возродили под прежним названием");
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

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
