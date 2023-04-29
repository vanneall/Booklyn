package com.example.booklyn.hotel_page;


import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;

import java.util.Date;


public class TripDateFragment extends Fragment {
    Room room;

    Hotel hotel;

    public interface DateSetter{
        void setDate(TripDate date1, TripDate date2);
    }

    DateSetter dateSetter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dateSetter = (DateSetter) context;
    }

    TripDate checkIn;
    TripDate checkOut;
    boolean isCheckInDay = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageViewBack = view.findViewById(R.id.trip_date_imageView_sign_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        room = getArguments().getParcelable(Room.SELECTED_ROOM);
        hotel = getArguments().getParcelable(Hotel.SELECTED_HOTEL);
        checkIn = new TripDate(new Date().getTime());
        Date temp = new Date();
        setInnerDate(checkIn, temp.getDate(), temp.getMonth(), 2000 + temp.getYear() % 100);
        ((TextView)view.findViewById(R.id.textView_check_in_info)).setText(checkIn.toString());

        checkOut = new TripDate(new Date().getTime());
        setInnerDate(checkOut, temp.getDate(), temp.getMonth(), 2000 + temp.getYear() % 100);
        ((TextView)view.findViewById(R.id.textView_check_out_info)).setText(checkOut.toString());

        CalendarView calendarView = view.findViewById(R.id.fragment_trip_date_calendarView);
        calendarView.setMinDate((new Date()).getTime());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String selectedDate = new StringBuilder().append(i2)
                        .append("/").append(i1 + 1).append("/").append(i)
                        .append(" ").toString();
                if (isCheckInDay) {
                    ((TextView)view.findViewById(R.id.textView_check_in_info)).setText(selectedDate);
                    setInnerDate(checkIn, i2, i1, i);
                } else {
                    ((TextView)view.findViewById(R.id.textView_check_out_info)).setText(selectedDate);
                    setInnerDate(checkOut, i2, i1, i);
                }
                isCheckInDay = !isCheckInDay;
            }
        });

        Button buttonApply = view.findViewById(R.id.trip_date_apply_button);
        buttonApply.setOnClickListener(this::onApplyClick);
    }

    public void setInnerDate(@NonNull TripDate date, int day, int month, int year){
        date.getInnerDate().setDate(day);
        date.getInnerDate().setMonth(month + 1);
        date.getInnerDate().setYear(year);
    }

    public void onApplyClick(View view){
        if (!checkIn.getInnerDate().after(checkOut.getInnerDate())) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Hotel.SELECTED_HOTEL, hotel);
            bundle.putParcelable(Room.SELECTED_ROOM, room);
            bundle.putParcelable(TripDate.CHECK_IN, checkIn);
            bundle.putParcelable(TripDate.CHECK_OUT, checkOut);
            Navigation.findNavController(view).navigate(R.id.action_tripDateFragment_to_orderInfoFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Некорректная дата", Toast.LENGTH_LONG).show();
        }
    }
}