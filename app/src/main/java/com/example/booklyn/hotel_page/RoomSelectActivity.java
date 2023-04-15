package com.example.booklyn.hotel_page;

import androidx.appcompat.app.AppCompatActivity;
import com.example.booklyn.R;
import com.example.booklyn.adapters.RoomsAdapter;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RoomSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_select);
        ArrayList<Room> rooms = Hotel.hotels.get(getIntent().getIntExtra(Room.SELECTED_ROOM, 0)).getRooms();
        ListView listView = findViewById(R.id.room_select_listView);
        RoomsAdapter roomsAdapter = new RoomsAdapter(this, R.layout.room_selection_list_item, rooms);
        listView.setAdapter(roomsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent data = new Intent();
                data.putExtra(Room.SELECTED_ROOM, i);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}