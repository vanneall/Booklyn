package com.example.booklyn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;

import java.util.ArrayList;

public class HotelSelectionActivity extends AppCompatActivity {

    ListView listViewMainHotels;

    ArrayList<Hotel> hotels = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selection);
        listViewMainHotels = findViewById(R.id.listView_main_hotels);
        // TODO Убрать
        Hotel.addHotels(Hotel.hotels);

        HotelsAdapter adapter = new HotelsAdapter(this, R.layout.hotels_list_item, Hotel.hotels);
        listViewMainHotels.setAdapter(adapter);
    }


}