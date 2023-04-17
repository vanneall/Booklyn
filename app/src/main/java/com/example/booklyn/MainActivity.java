package com.example.booklyn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;
import com.example.booklyn.hotel_page.DialogAddFragment;
import com.example.booklyn.hotel_page.FeedbackFragment;
import com.example.booklyn.hotel_page.HotelSelectionFragment;
import com.example.booklyn.hotel_page.MainPageFragment;
import com.example.booklyn.hotel_page.PhotosFragment;
import com.example.booklyn.hotel_page.ReviewFragment;
import com.example.booklyn.hotel_page.RoomSelectionFragment;
import com.example.booklyn.hotel_page.SortFragment;
import com.example.booklyn.hotel_page.TripDateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements MainPageFragment.PageController, DialogAddFragment.NewRateGetter,
        TripDateFragment.DateSetter, MainPageFragment.Transfer, SortFragment.GetNotifyDataChanged, HotelSelectionFragment.MainPageSetter{
        //RoomSelectionFragment.SelectedRoomGetter {

    HotelSelectionFragment hotelSelectionFragment;
    ReviewFragment reviewFragment;
    PhotosFragment photosFragment;
    FeedbackFragment feedbackFragment;

    TripDate tripDateCheckIn;
    TripDate tripDateCheckOut;

    BottomNavigationView menu;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        menu = findViewById(R.id.bottom_navigation);
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(menu, navController);
    }

    @Override
    public void switchPage(int page) {
        switch (page) {
            case MainPageFragment.FEEDBACK_PAGE:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, feedbackFragment).commit();
                break;
            case MainPageFragment.REVIEW_PAGE:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, reviewFragment).commit();
                break;
            case MainPageFragment.PHOTOS_PAGE:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, photosFragment).commit();
                break;
        }
    }

    @Override
    public void createFeedbackPage(FeedbackFragment feedbackFragment) {
        this.feedbackFragment = feedbackFragment;
    }

    @Override
    public void createReviewPage(ReviewFragment reviewFragment) {
        this.reviewFragment = reviewFragment;
    }

    @Override
    public void createPhotosPage(PhotosFragment photosFragment) {
        this.photosFragment = photosFragment;
    }

    @Override
    public void setRate(float rate, String info) {
        feedbackFragment.addRate(rate, info);
    }

    @Override
    public void setDate(TripDate date1, TripDate date2) {
        tripDateCheckIn = date1;
        tripDateCheckOut = date2;
        reviewFragment.setDate(date1.toString(), date2.toString());
    }

    @Override
    public TripDate[] getTripDate() {
        return new TripDate[]{tripDateCheckIn, tripDateCheckOut};
    }

    @Override
    public Room getRoom() {
        return reviewFragment.getRoom();
    }

    @Override
    public void dataChanged() {
        hotelSelectionFragment.dataChanged();
    }

    @Override
    public void setMainPage(HotelSelectionFragment hotelSelectionFragment) {
        this.hotelSelectionFragment = hotelSelectionFragment;
    }

//    @Override
//    public void setRoom(Room room) {
//        reviewFragment.setRoom(room);
//    }
}