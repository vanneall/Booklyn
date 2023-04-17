package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.HotelNameComparator;
import com.example.booklyn.entities.HotelPriceComparator;
import com.example.booklyn.entities.HotelRateComparator;

import java.util.Collections;

public class SortFragment extends Fragment {


    RadioButton radioButtonName;

    RadioButton radioButtonByPrice;

    RadioButton radioButtonByRate;

    public interface GetNotifyDataChanged{
        void dataChanged();
    }
    GetNotifyDataChanged dataChanged;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataChanged = (GetNotifyDataChanged) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RadioButton radioButton = view.findViewById(R.id.sort_radio_button_descending);

        radioButton.setChecked(true);
        radioButtonName = view.findViewById(R.id.sort_by_name);
        radioButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hotel.hotels.sort(new HotelNameComparator());
                if (!radioButton.isChecked()) {
                    Collections.reverse(Hotel.hotels);
                }
                dataChanged.dataChanged();
            }
        });

        radioButtonByPrice = view.findViewById(R.id.sort_by_price);
        radioButtonByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hotel.hotels.sort(new HotelPriceComparator());
                if (radioButton.isChecked()) {
                    Collections.reverse(Hotel.hotels);
                }
                dataChanged.dataChanged();
            }
        });

        radioButtonByRate = view.findViewById(R.id.sort_by_rate);
        radioButtonByRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hotel.hotels.sort(new HotelRateComparator());
                if (radioButton.isChecked()) {
                    Collections.reverse(Hotel.hotels);
                }
                dataChanged.dataChanged();

            }
        });

        radioButton.setOnCheckedChangeListener(checkedChangeListener);
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             if (radioButtonName.isChecked()) radioButtonName.callOnClick();
             else if (radioButtonByPrice.isChecked()) radioButtonByPrice.callOnClick();
             else if (radioButtonByRate.isChecked()) radioButtonByRate.callOnClick();
        }
    };
}