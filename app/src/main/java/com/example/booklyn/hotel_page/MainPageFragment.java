package com.example.booklyn.hotel_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;


public class MainPageFragment extends Fragment {

    Hotel hotel;

    public MainPageFragment(Bundle bundle){
        hotel =  Hotel.hotels.get(bundle.getInt("hotel_selected"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewName = view.findViewById(R.id.main_page_textView_title);
        textViewName.setText(hotel.getName());

        ImageView imageViewMain = view.findViewById(R.id.main_page_imageView_photo);
        imageViewMain.setImageResource(hotel.getMainPicture());

        TextView textViewInfo = view.findViewById(R.id.main_page_textView_info);
        textViewInfo.setText(hotel.getInfo());

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}