package com.example.booklyn;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;

public class HotelSelectionActivity extends AppCompatActivity {

    ListView listViewMainHotels;

    HotelsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selection);
        listViewMainHotels = findViewById(R.id.listView_main_hotels);
        Hotel.hotels.clear();
        Hotel.addHotels(Hotel.hotels);
        adapter = new HotelsAdapter(this, R.layout.hotels_list_item, Hotel.hotels);
        listViewMainHotels.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshRating();
    }

    public void refreshRating(){
        adapter.notifyDataSetChanged();
    }
}