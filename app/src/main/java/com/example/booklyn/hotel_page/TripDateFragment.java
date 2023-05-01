package com.example.booklyn.hotel_page;


import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;

import java.util.Date;


public class TripDateFragment extends Fragment {

    TripDate checkIn;
    TripDate checkOut;
    boolean isCheckInDay = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Кнопка назад
        ImageView imageViewBack = view.findViewById(R.id.trip_date_imageView_sign_back);
        imageViewBack.setOnClickListener(this::clickBack);

        //Установка сегодняшней даты
        checkIn = new TripDate(new Date().getTime());
        Date temp = new Date();
        setInnerDate(checkIn, temp.getDate(), temp.getMonth(), 2000 + temp.getYear() % 100);
        ((TextView)view.findViewById(R.id.trip_date_textView_check_in_info)).setText(checkIn.toString());
        checkOut = new TripDate(new Date().getTime());
        setInnerDate(checkOut, temp.getDate(), temp.getMonth(), 2000 + temp.getYear() % 100);
        ((TextView)view.findViewById(R.id.trip_date_textView_check_out_info)).setText(checkOut.toString());

        //Работа с календарем
        CalendarView calendarView = view.findViewById(R.id.fragment_trip_date_calendarView);
        calendarView.setMinDate((new Date()).getTime());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String selectedDate = i2 + "/" + (i1 + 1) + "/" + i;
                if (isCheckInDay) {
                    ((TextView)view.findViewById(R.id.trip_date_textView_check_in_info)).setText(selectedDate);
                    setInnerDate(checkIn, i2, i1, i);
                } else {
                    ((TextView)view.findViewById(R.id.trip_date_textView_check_out_info)).setText(selectedDate);
                    setInnerDate(checkOut, i2, i1, i);
                }
                isCheckInDay = !isCheckInDay;
            }
        });

        //Кнопка перехода к информации о заказе
        Button buttonApply = view.findViewById(R.id.trip_date_button_apply);
        buttonApply.setOnClickListener(this::onApplyClick);
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    private void setInnerDate(@NonNull TripDate date, int day, int month, int year){
        date.getInnerDate().setDate(day);
        date.getInnerDate().setMonth(month + 1);
        date.getInnerDate().setYear(year);
    }

    private void onApplyClick(View view){
        if (!checkIn.getInnerDate().after(checkOut.getInnerDate())) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Hotel.SELECTED_HOTEL, getArguments().getParcelable(Hotel.SELECTED_HOTEL));
            bundle.putParcelable(Room.SELECTED_ROOM, getArguments().getParcelable(Room.SELECTED_ROOM));
            bundle.putParcelable(TripDate.CHECK_IN, checkIn);
            bundle.putParcelable(TripDate.CHECK_OUT, checkOut);
            Navigation.findNavController(view).navigate(R.id.action_tripDateFragment_to_orderInfoFragment, bundle);
        } else {
            Toast.makeText(getActivity(), "Некорректная дата", Toast.LENGTH_LONG).show();
        }
    }
}