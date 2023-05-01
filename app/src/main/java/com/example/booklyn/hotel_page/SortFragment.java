package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    RadioButton radioButtonDescending;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Сортировка по имени
        radioButtonName = view.findViewById(R.id.sort_by_name);
        radioButtonName.setOnClickListener(this::clickSort);

        //Сортировка по цене
        radioButtonByPrice = view.findViewById(R.id.sort_by_price);
        radioButtonByPrice.setOnClickListener(this::clickSort);

        //Сортировка по рейтингу
        radioButtonByRate = view.findViewById(R.id.sort_by_rate);
        radioButtonByRate.setOnClickListener(this::clickSort);


        //Кнопка сортировки по возрастанию/убыванию
        radioButtonDescending = view.findViewById(R.id.sort_radio_button_descending);
        radioButtonDescending.setOnCheckedChangeListener(checkedChangeListener);
        radioButtonDescending.setChecked(true);
    }

    private void clickSort(View view){
        switch (view.getId()){
            case R.id.sort_by_name:{
                Hotel.hotels.sort(new HotelNameComparator());
                if (!radioButtonDescending.isChecked()) {
                    Collections.reverse(Hotel.hotels);
                }
                break;
            }
            case R.id.sort_by_price:{
                Hotel.hotels.sort(new HotelPriceComparator());
                if (radioButtonDescending.isChecked()) {
                    Collections.reverse(Hotel.hotels);
                }
                break;
            }
            case R.id.sort_by_rate:{
                Hotel.hotels.sort(new HotelRateComparator());
                if (radioButtonDescending.isChecked()) {
                    Collections.reverse(Hotel.hotels);
                }
                break;
            }
        }
        dataChanged.dataChanged();
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             if (radioButtonName.isChecked()) radioButtonName.callOnClick();
             else if (radioButtonByPrice.isChecked()) radioButtonByPrice.callOnClick();
             else if (radioButtonByRate.isChecked()) radioButtonByRate.callOnClick();
        }
    };

    @Override
    public void onDestroyView() {
        radioButtonName = null;
        radioButtonByPrice = null;
        radioButtonByRate = null;
        radioButtonDescending = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        dataChanged = null;
        super.onDetach();
    }
}