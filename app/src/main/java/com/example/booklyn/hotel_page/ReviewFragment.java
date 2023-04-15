package com.example.booklyn.hotel_page;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;

public class ReviewFragment extends Fragment {

    Hotel hotel;
    View main;

    public ReviewFragment(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewInfo = view.findViewById(R.id.review_textView_info);
        textViewInfo.setText(hotel.getInfo());
        main = view;
        TextView textViewCheckIn = view.findViewById(R.id.fragment_review_textView_check_in);
        textViewCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripDateFragment tripDateFragment = new TripDateFragment();
                tripDateFragment.show(getFragmentManager(), "tag1");
            }
        });
    }


    public void setDate(String checkIn, String checkOut) {
        ((TextView)main.findViewById(R.id.fragment_review_textView_check_in)).setText(checkIn);
        ((TextView)main.findViewById(R.id.fragment_review_textView_check_out)).setText(checkOut);
    }
}