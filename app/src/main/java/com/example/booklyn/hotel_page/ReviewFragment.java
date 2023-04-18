package com.example.booklyn.hotel_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;

public class ReviewFragment extends Fragment {

    public interface SaveInfo {
        TripDate[] getDates();

        Room getSelectedRoom();
    }

    SaveInfo saveInfo;
    Room room = null;

    int i;

    TextView textViewSelectRoom;

    Hotel hotel;
    View main;

    public ReviewFragment(Hotel hotel, int i) {
        this.hotel = hotel;
        this.i = i;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        saveInfo = (SaveInfo) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main = view;
        TripDate[] savedInfoTrips = saveInfo.getDates();
        if (savedInfoTrips != null) {
            setDate(savedInfoTrips[0].toString(), savedInfoTrips[1].toString());
        }
        if (saveInfo.getSelectedRoom() != null) {
            this.room = saveInfo.getSelectedRoom();
            ((TextView)(view.findViewById(R.id.review_textView_room_select))).setText(room.getName());
        }
        TextView textViewInfo = view.findViewById(R.id.review_textView_info);
        textViewInfo.setText(hotel.getInfo());
        TextView textViewCheckIn = view.findViewById(R.id.fragment_review_textView_check_in);
        textViewCheckIn.setOnClickListener(this::onCheckClick);

        TextView textViewCheckOut = view.findViewById(R.id.fragment_review_textView_check_out);
        textViewCheckOut.setOnClickListener(this::onCheckClick);


        textViewSelectRoom = view.findViewById(R.id.review_textView_room_select);
        textViewSelectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Hotel.SELECTED_HOTEL, i);
                Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_roomSelectionFragment);
            }
        });
    }

    public void onCheckClick(View view) {
        TripDateFragment tripDateFragment = new TripDateFragment();
        tripDateFragment.show(getFragmentManager(), "tag1");
    }



    //TODO сделать нормальное получение комнаты из фрагмента
//    public void setRoom(Room room){
//        this.room = room;
//    }

    public void setDate(String checkIn, String checkOut) {
        ((TextView)main.findViewById(R.id.fragment_review_textView_check_in)).setText(checkIn);
        ((TextView)main.findViewById(R.id.fragment_review_textView_check_out)).setText(checkOut);
    }

    public Room getRoom(){
        return room;
    }
}