package com.example.booklyn;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;

public class HotelSelectionActivity extends AppCompatActivity {

    HotelSelectionFragment hotelSelectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selection);
        hotelSelectionFragment = new HotelSelectionFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.hotel_selection_activity_container, hotelSelectionFragment).commit();
    }
}