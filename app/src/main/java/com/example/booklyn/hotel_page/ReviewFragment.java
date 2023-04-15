package com.example.booklyn.hotel_page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;

public class ReviewFragment extends Fragment {

    Room room = null;

    TextView textViewSelectRoom;

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
        textViewCheckIn.setOnClickListener(this::onCheckClick);

        TextView textViewCheckOut = view.findViewById(R.id.fragment_review_textView_check_out);
        textViewCheckOut.setOnClickListener(this::onCheckClick);


        textViewSelectRoom = view.findViewById(R.id.review_textView_room_select);
        textViewSelectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RoomSelectActivity.class);
                mStartForResult.launch(intent);
            }
        });
    }

    public void onCheckClick(View view) {
        TripDateFragment tripDateFragment = new TripDateFragment();
        tripDateFragment.show(getFragmentManager(), "tag1");
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                room = hotel.rooms.get(intent.getIntExtra(Room.SELECTED_ROOM, 0));
                textViewSelectRoom.setText(room.getInfo());
            }
        }
    });

    public void setDate(String checkIn, String checkOut) {
        ((TextView)main.findViewById(R.id.fragment_review_textView_check_in)).setText(checkIn);
        ((TextView)main.findViewById(R.id.fragment_review_textView_check_out)).setText(checkOut);
    }

    public Room getRoom(){
        return room;
    }
}