package com.example.booklyn.making_order_page;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;

import java.util.Date;

public class OrderInfoFragment extends Fragment {

    Hotel hotel;

    TripDate[] tripDates;

    Room room;

    public OrderInfoFragment(int i, TripDate[] tripDates, Room room){
        this.hotel = Hotel.hotels.get(i);
        this.tripDates = tripDates;
        this.room = room;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_info, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageViewBack = view.findViewById(R.id.order_info_imageView_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        ImageView imageView = view.findViewById(R.id.order_info_imageView_photo);
        imageView.setImageResource(hotel.getMainPicture());

        TextView textViewTitle = view.findViewById(R.id.order_info_textView_title);
        textViewTitle.setText(hotel.getName());

        TextView textViewRoom = view.findViewById(R.id.order_info_room);
        textViewRoom.setText(room.getInfo());

        TextView textViewDuration = view.findViewById(R.id.order_info_duration);
        int days = new Date(tripDates[1].getInnerDate().getTime() - tripDates[0].getInnerDate().getTime()).getDate();
        float sum = days * room.getPrice();
        textViewDuration.setText(days + " " + getResources().getQuantityString(R.plurals.days, days) + " (" +
                room.getPrice() + "₽ x " + days + " = " + (int)sum + "₽)");

        TextView textViewVAT = view.findViewById(R.id.order_info_VAT);
        float vat = sum * 0.2f;
        textViewVAT.setText(Float.toString(vat) + "₽");

        TextView textViewResult = view.findViewById(R.id.order_info_result_sum);
        textViewResult.setText(Float.toString(sum + vat) + "₽");

    }
}