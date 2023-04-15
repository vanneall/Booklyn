package com.example.booklyn.hotel_page;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.booklyn.R;
import com.example.booklyn.entities.TripDate;

import java.util.Date;


public class TripDateFragment extends DialogFragment {

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
        checkIn = new TripDate();
        checkOut = new TripDate();

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
                    checkIn.setDay(i2);
                    checkIn.setMonth(i1+1);
                    checkIn.setYear(i);
                } else {
                    ((TextView)view.findViewById(R.id.textView_check_out_info)).setText(selectedDate);
                    checkOut.setDay(i2);
                    checkOut.setMonth(i1+1);
                    checkOut.setYear(i);
                }
                isCheckInDay = !isCheckInDay;
            }
        });

        Button buttonApply = view.findViewById(R.id.trip_date_apply_button);
        buttonApply.setOnClickListener(this::onApplyClick);

        Button buttonCancel = view.findViewById(R.id.trip_date_cancel_button);
        buttonCancel.setOnClickListener(this::onCancelClick);
    }


    public void onCancelClick(View view){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    public void onApplyClick(View view){
        if (checkIn.compareTo(checkOut) < 0) {
            dateSetter.setDate(checkIn, checkOut);
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else {
            Toast.makeText(getActivity(), "Некорректная дата", Toast.LENGTH_LONG).show();
        }
    }
}