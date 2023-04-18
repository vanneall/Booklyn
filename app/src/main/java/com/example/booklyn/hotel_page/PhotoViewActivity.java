package com.example.booklyn.hotel_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.booklyn.R;
import com.example.booklyn.adapters.PhotoAdapter;
import com.example.booklyn.adapters.PhotosAdapter;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ArrayList<Integer> photos = getIntent().getIntegerArrayListExtra(PhotosAdapter.PHOTO_KEY);
        ViewPager2 pager = findViewById(R.id.pager);
        PhotoAdapter adapter = new PhotoAdapter(this, photos);
        pager.setAdapter(adapter);
    }
}