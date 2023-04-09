package com.example.booklyn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.booklyn.hotel_page.FeedbackFragment;
import com.example.booklyn.hotel_page.MainPageFragment;
import com.example.booklyn.hotel_page.PhotosFragment;
import com.example.booklyn.hotel_page.ReviewFragment;

public class HotelActivity extends AppCompatActivity implements MainPageFragment.PageController {

    MainPageFragment mainPageFragment;

    ReviewFragment reviewFragment = new ReviewFragment();
    PhotosFragment photosFragment = new PhotosFragment();
    FeedbackFragment feedbackFragment = new FeedbackFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        // TODO: Убрать
        Bundle bundle = getIntent().getExtras();

        MainPageFragment mainPageFragment = new MainPageFragment(bundle);
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
}