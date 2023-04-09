package com.example.booklyn.hotel_page;

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

    public ReviewFragment(Bundle bundle) {
        hotel =  Hotel.hotels.get(bundle.getInt(Hotel.SELECTED_HOTEL));
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
    }
}