package com.example.booklyn.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.booklyn.R;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;

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
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Room room = rooms.get(i);
        viewHolder.textViewName.setText(room.getName());
        viewHolder.textViewInfo.setText("Информация: " + room.getInfo());
        viewHolder.textViewPrice.setText("Цена: " + room.getPrice() + "₽");
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
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

    private class ViewHolder{
        final TextView textViewName;
        final TextView textViewInfo;
        final TextView textViewPrice;
        final Button button;
        ViewHolder(View view){
            textViewName = view.findViewById(R.id.room_selection_list_item_name);
            textViewInfo = view.findViewById(R.id.room_selection_list_item_info);
            textViewPrice = view.findViewById(R.id.main_page_textView_min_price);
            button = view.findViewById(R.id.room_selection_list_item_button);
        }
    }
}
