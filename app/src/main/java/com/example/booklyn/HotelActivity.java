package com.example.booklyn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.booklyn.entities.Hotel;
import com.example.booklyn.hotel_page.DialogAddFragment;
import com.example.booklyn.hotel_page.FeedbackFragment;
import com.example.booklyn.hotel_page.MainPageFragment;
import com.example.booklyn.hotel_page.PhotoViewActivity;
import com.example.booklyn.hotel_page.PhotosFragment;
import com.example.booklyn.hotel_page.ReviewFragment;

public class HotelActivity extends AppCompatActivity implements MainPageFragment.PageController, DialogAddFragment.NewRateGetter {
    ReviewFragment reviewFragment;
    PhotosFragment photosFragment;
    FeedbackFragment feedbackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        // TODO: Убрать
        Bundle bundle = getIntent().getExtras();
        Hotel hotel = Hotel.hotels.get(bundle.getInt(Hotel.SELECTED_HOTEL));

        MainPageFragment mainPageFragment = new MainPageFragment(bundle);
        reviewFragment = new ReviewFragment(hotel);
        photosFragment = new PhotosFragment(hotel.getAdditionalPictures());
        feedbackFragment = new FeedbackFragment(hotel);
        getSupportFragmentManager().beginTransaction().add(R.id.hotel_activity, mainPageFragment).commit();
    }

    @Override
    public void switchPage(int page) {
        switch (page){
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
    public void setRate(float rate, String info) {
        feedbackFragment.addRate(rate, info);
    }
}