package com.example.booklyn.making_order_page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;
import com.example.booklyn.hotel_page.MainPageFragment;

import java.util.Date;

public class OrderInfoFragment extends Fragment {

    Hotel hotel;
    Room room;

    TripDate checkIn;
    TripDate checkOut;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        hotel = bundle.getParcelable(Hotel.SELECTED_HOTEL);
        room = bundle.getParcelable(Room.SELECTED_ROOM);
        checkIn = bundle.getParcelable(TripDate.CHECK_IN);
        checkOut = bundle.getParcelable(TripDate.CHECK_OUT);
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
        ImageView imageViewBack = view.findViewById(R.id.order_info_imageView_sign_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();;
            }
        });

        ImageView imageView = view.findViewById(R.id.order_info_imageView_photo);
        imageView.setImageResource(hotel.getMainPicture());

        TextView textViewTitle = view.findViewById(R.id.order_info_textView_title);
        textViewTitle.setText(hotel.getName());

        TextView textViewRoomName = view.findViewById(R.id.order_room_number);
        textViewRoomName.setText(room.getName());

        TextView textViewRoomInfo = view.findViewById(R.id.order_room_info);
        textViewRoomInfo.setText(room.getInfo());

        TextView textViewDuration = view.findViewById(R.id.order_info_duration);
        int days = new Date(checkOut.getInnerDate().getTime() - checkIn.getInnerDate().getTime()).getDate();
        float sum = days * room.getPrice();
        textViewDuration.setText(days + " " + getResources().getQuantityString(R.plurals.days, days) + " (" +
                room.getPrice() + "₽ x " + days + " = " + (int)sum + "₽)");

        TextView textViewVAT = view.findViewById(R.id.order_info_VAT);
        float vat = sum * 0.1f;
        textViewVAT.setText(Float.toString(vat) + "₽");

        TextView textViewResult = view.findViewById(R.id.order_info_result_sum);
        textViewResult.setText(Float.toString(sum + vat) + "₽");

        Button button = view.findViewById(R.id.order_info_button);
        button.setOnClickListener(this::onClick);
    }

    public void onClick(View view){
        Navigation.findNavController(view).navigate(R.id.action_orderInfoFragment_to_paymentFragment);
    }
}