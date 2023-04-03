package com.example.booklyn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;

import java.util.ArrayList;

public class HotelSelectionActivity extends AppCompatActivity {

    ListView listViewMainHotels;

    ArrayList<Hotel> arrayList = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selection);
        listViewMainHotels = findViewById(R.id.listView_main_hotels);
        addHotels();
        HotelsAdapter adapter = new HotelsAdapter(this, R.layout.hotels_list_item, arrayList);
        listViewMainHotels.setAdapter(adapter);
    }

    private void addHotels(){
        arrayList.add(new Hotel("Holiday Inn", 7000f, R.drawable.holiday_inn));
        arrayList.add(new Hotel("Novotel", 2500f, R.drawable.novotel));
        arrayList.add(new Hotel("Националь 5*", 3750f, R.drawable.nacional5));
    }
}