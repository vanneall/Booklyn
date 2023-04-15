package com.example.booklyn.hotel_page;

import androidx.appcompat.app.AppCompatActivity;
import com.example.booklyn.R;
import com.example.booklyn.adapters.PhotosAdapter;

import android.os.Bundle;
import android.widget.ImageView;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        int photo = getIntent().getIntExtra(PhotosAdapter.PHOTO_KEY,0);
        ((ImageView)findViewById(R.id.activity_photo_view_photo)).setImageResource(photo);
    }
}