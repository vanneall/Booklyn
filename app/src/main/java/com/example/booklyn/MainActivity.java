package com.example.booklyn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;
import com.example.booklyn.hotel_page.DialogAddFragment;
import com.example.booklyn.hotel_page.FeedbackFragment;
import com.example.booklyn.hotel_page.HotelSelectionFragment;
import com.example.booklyn.hotel_page.MainPageFragment;
import com.example.booklyn.hotel_page.PhotosFragment;
import com.example.booklyn.hotel_page.RoomSelectionFragment;
import com.example.booklyn.hotel_page.SortFragment;
import com.example.booklyn.hotel_page.TripDateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogAddFragment.NewRateGetter,
        TripDateFragment.DateSetter, MainPageFragment.Transfer, SortFragment.GetNotifyDataChanged, HotelSelectionFragment.MainPage, RoomSelectionFragment.SelectedRoomGetter {


    public static TripDate tripDateCheckIn;
    public static TripDate tripDateCheckOut;

    public static Room room;
    HotelSelectionFragment hotelSelectionFragment;
    PhotosFragment photosFragment;
    FeedbackFragment feedbackFragment;


    BottomNavigationView menu;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hotel.addHotels(Hotel.hotels);
    }

    @Override
    protected void onStart() {
        super.onStart();
        menu = findViewById(R.id.bottom_navigation);
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(menu, navController);
    }

    @Override
    public void setRate(float rate, String info) {
        feedbackFragment.addRate(rate, info);
    }

    @Override
    public void setDate(TripDate date1, TripDate date2) {
        tripDateCheckIn = date1;
        tripDateCheckOut = date2;
    }

    @Override
    public TripDate[] getTripDate() {
        return new TripDate[]{tripDateCheckIn, tripDateCheckOut};
    }

    @Override
    public Room getRoom() {
        return null;
    }

    @Override
    public void dataChanged() {
        hotelSelectionFragment.dataChanged();
    }

    @Override
    public void setMainPage(HotelSelectionFragment hotelSelectionFragment) {
        this.hotelSelectionFragment = hotelSelectionFragment;
    }

    @Override
    public ArrayList<Hotel> getHotels() {
        return Hotel.hotels;
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

//    @Override
//    public void setRoom(Room room) {
//        reviewFragment.setRoom(room);
//    }
}