package com.example.booklyn.making_order_page;

import androidx.appcompat.app.AppCompatActivity;
import com.example.booklyn.R;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;
import com.example.booklyn.hotel_page.MainPageFragment;

import android.os.Bundle;

public class OrderInfoActivity extends AppCompatActivity {

    OrderInfoFragment orderInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        Bundle bundle = getIntent().getExtras();
        int i = bundle.getInt(MainPageFragment.SELECTED_HOTEL);
        TripDate[] tripDates = new TripDate[]{ new TripDate(bundle.getLong(MainPageFragment.SELECTED_DATE_IN)),
                new TripDate(bundle.getLong(MainPageFragment.SELECTED_DATE_OUT))};
        Room room = bundle.getParcelable(MainPageFragment.SELECTED_ROOM);
        orderInfoFragment = new OrderInfoFragment(i, tripDates, room);
        getSupportFragmentManager().beginTransaction().add(R.id.order_info_container, orderInfoFragment).commit();
    }
}