package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;

public class HotelSelectionFragment extends Fragment{


    public interface MainPageSetter {
        void setMainPage(HotelSelectionFragment hotelSelectionFragment);
    }
    MainPageSetter mainPageFragment;

    ListView listViewMainHotels;

    private boolean isHideSortAndFiltrFragment = true;

    HotelsAdapter adapter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainPageFragment = (MainPageSetter) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            listViewMainHotels = view.findViewById(R.id.listView_main_hotels);
            Hotel.hotels.clear();
            Hotel.addHotels(Hotel.hotels);
            adapter = new HotelsAdapter(getActivity(), R.layout.hotels_list_item, Hotel.hotels);
            listViewMainHotels.setAdapter(adapter);
        }
        mainPageFragment.setMainPage(this);


        SortFragment sortFragment = new SortFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_container_for_sort_and_filter, sortFragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().hide(sortFragment).commit();

        TextView textViewSort = view.findViewById(R.id.main_textView_sort);
        textViewSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHideSortAndFiltrFragment) {
                    getActivity().getSupportFragmentManager().beginTransaction().show(sortFragment).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().hide(sortFragment).commit();
                }
                isHideSortAndFiltrFragment = !isHideSortAndFiltrFragment;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        dataChanged();
    }

    public void dataChanged() {
        adapter.notifyDataSetChanged();
    }
}