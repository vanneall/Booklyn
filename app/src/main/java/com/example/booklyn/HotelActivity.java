package com.example.booklyn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booklyn.entities.Hotel;
import com.example.booklyn.hotel_page.MainPageFragment;

public class HotelActivity extends AppCompatActivity {

    MainPageFragment mainPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        // TODO: Убрать
        Bundle bundle = getIntent().getExtras();
        MainPageFragment mainPageFragment1 = new MainPageFragment(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.hotel_activity, mainPageFragment1).commit();
    }
}