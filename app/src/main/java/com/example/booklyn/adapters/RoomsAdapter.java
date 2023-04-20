package com.example.booklyn.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.booklyn.MainActivity;
import com.example.booklyn.R;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.hotel_page.TripDateFragment;

import org.w3c.dom.Text;

import java.util.List;

public class RoomsAdapter extends ArrayAdapter<Room> {

    List<Room> rooms;
    LayoutInflater inflater;
    int layout;
    Hotel hotel;

    public RoomsAdapter(@NonNull Context context, int resource, @NonNull List<Room> objects, Hotel hotel) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        rooms = objects;
        this.hotel = hotel;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }

        Room room = rooms.get(i);
        TextView textViewName = view.findViewById(R.id.room_selection_item_name);
        TextView textViewInfo = view.findViewById(R.id.room_selection_item_info);
        TextView textViewPrice = view.findViewById(R.id.textView_hotel_list_item_price);
        textViewName.setText(room.getName());
        textViewInfo.setText("Информация: " + room.getInfo());
        textViewPrice.setText("Цена: " +String.valueOf(room.getPrice()) + "₽");

        Button button = view.findViewById(R.id.room_selection_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Room.SELECTED_ROOM, room);
                bundle.putParcelable(Hotel.SELECTED_HOTEL, hotel);
                Navigation.findNavController(view).navigate(R.id.action_roomSelectionFragment_to_tripDateFragment, bundle);
            }
        });
        return view;
    }
}
