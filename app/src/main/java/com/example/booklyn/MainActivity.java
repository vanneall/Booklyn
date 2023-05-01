package com.example.booklyn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.booklyn.authorization.AuthorizationFragment;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Rate;
import com.example.booklyn.entities.User;
import com.example.booklyn.hotel_page.DialogAddFragment;
import com.example.booklyn.hotel_page.FeedbackFragment;
import com.example.booklyn.hotel_page.HotelSelectionFragment;
import com.example.booklyn.hotel_page.SortFragment;
import com.example.booklyn.hotel_page.ViewPagerPhotosFragment;
import com.example.booklyn.making_order_page.OrderInfoFragment;
import com.example.booklyn.settings_page.SettingsPageFragment;
import com.example.booklyn.user_page.UserPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogAddFragment.RateGetter,
        SortFragment.GetNotifyDataChanged, HotelSelectionFragment.MainPageController,
        FeedbackFragment.FeedbackController, AuthorizationFragment.BottomNavigationVisibaleController,
        SettingsPageFragment.UserGetter, UserPageFragment.UserGetter,
        OrderInfoFragment.UserGetter, ViewPagerPhotosFragment.BottomNavigationVisibaleController {

    HotelSelectionFragment hotelSelectionFragment;
    FeedbackFragment feedbackFragment;
    DataBaseHelper dataBaseHelperClass;
    BottomNavigationView menu;
    NavController navController;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelperClass = new DataBaseHelper(this);
        try {
            dataBaseHelperClass.createDataBase();
        } catch (Exception ex) {}
        dataBaseHelperClass.openDataBase();
        Hotel.hotels = dataBaseHelperClass.getHotelsFromDatabase();
        dataBaseHelperClass.close();
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
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setVisible() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(BottomNavigationView.VISIBLE);
    }

    @Override
    public void setFeedback(FeedbackFragment feedbackFragment) {
        this.feedbackFragment = feedbackFragment;
    }

    @Override
    public void writeToDB(Rate rate, int hotelID) {
        dataBaseHelperClass.openDataBase();
        dataBaseHelperClass.writeFeedbackToDatabase(rate, hotelID);
        dataBaseHelperClass.close();
    }

    @Override
    public void setInvisible() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(BottomNavigationView.GONE);
    }

    @Override
    public User getUser() {
        return user;
    }
}