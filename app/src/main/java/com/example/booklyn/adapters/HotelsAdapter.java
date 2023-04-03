package com.example.booklyn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;

import org.w3c.dom.Text;

import java.util.List;

public class HotelsAdapter extends ArrayAdapter<Hotel> {
    private LayoutInflater inflater;

    private List<Hotel> hotels;

    private int layout;

    public HotelsAdapter(@NonNull Context context, int resource, @NonNull List<Hotel> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        hotels = objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }
        ImageView imageView = view.findViewById(R.id.imageView_hotel_list_item_picture);
        TextView textViewName = view.findViewById(R.id.textView_hotel_list_item_name);
        TextView textViewRate = view.findViewById(R.id.textView_hotel_list_item_rate);
        TextView textViewPrice = view.findViewById(R.id.textView_hotel_list_item_price);
        Button button = view.findViewById(R.id.button_selection_hotel);

        Hotel hotel = hotels.get(i);
        imageView.setImageResource(hotel.getMainPicture());
        textViewName.setText(hotel.getName());
        textViewRate.setText(String.valueOf(hotel.getRate()));
        textViewPrice.setText(String.valueOf(hotel.getPrice()));
        return view;
    }
}
