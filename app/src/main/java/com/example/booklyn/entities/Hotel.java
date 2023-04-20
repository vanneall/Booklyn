package com.example.booklyn.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.booklyn.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Hotel implements Parcelable {

    public static final String SELECTED_HOTEL = "SELECTED_HOTEL";

    // TODO: Убрать
    public static ArrayList<Hotel> hotels = new ArrayList<>(3);

    public ArrayList<Room> rooms;

    private float allRatingSum = 0;

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

    // TODO: Убрать
    public static void addHotels(ArrayList<Hotel> hotels) {
        hotels.add(new Hotel("Holiday Inn", 7000, R.drawable.holiday_inn));
        hotels.get(0).setInfo("Holiday Inn — американская сеть гостиниц и дочерняя компания InterContinental Hotels Group. Компания является одной из крупнейших в мире гостиничных сетей и насчитывает 1173 действующих отелей и более чем 214 тысяч арендуемых номеров по состоянию на 30 сентября 2018 года.");
        hotels.add(new Hotel("Azbuka", 2500, R.drawable.novotel));
        hotels.get(1).setInfo("Новотель (англ. Novotel сокращение Новгородский отель) — бренд, под которым работает французская гостиничная сеть, входящая в состав группы Accor. «Новотель» представляет собой бренд стандартизированных отелей верхнего сегмента среднего класса. Сеть включает около четырёхсот гостиниц в шестидесяти странах мира. Отели Новотель располагаются преимущественно в крупнейших мегаполисах мира, бизнес-центрах и туристических направлениях.");
        hotels.add(new Hotel("Националь 5*", 3750, R.drawable.nacional5));
        hotels.get(2).setInfo("Гости́ница «Национа́ль» — московский отель, расположенный на Моховой улице, 15/1. Построен в 1900—1902 годах по проекту архитектора Александра Иванова. На момент открытия считался одним из самых престижных в Москве. После Революции 1917 года несколько лет в здании размещалось общежитие для чиновников советского правительства. В 1932 году гостиницу возродили под прежним названием");
        Random random = new Random();
        for (int j = 0; j < hotels.size(); j++) {
            hotels.get(j).rates = new ArrayList<>(5);
            hotels.get(j).additionalPictures = new ArrayList<>(5);
            hotels.get(j).rooms = new ArrayList<>(5);
            for (int i = 0; i < 5; i++) {
                hotels.get(j).rooms.add(new Room("Улучшенные апартаменты", "1-комнатная с видом на море", hotels.get(j).getMinPrice()));
                hotels.get(j).addRate(((float) (10 + random.nextInt(40)) / 10), "Какой-то комментарий");
                hotels.get(j).additionalPictures.add(hotels.get(j).mainPicture);
            }
        }
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

    public int getMinPrice() {
        return minPrice;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void addRate(float rate, String info) {
        rates.add(new Rate(rate, info, new Date()));

        allRatingSum += rate;
        avgRate = (float) allRatingSum / rates.size();
        rateCount[Math.round((rate - 1))]++;
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
