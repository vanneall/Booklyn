package com.example.booklyn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;

public class HotelSelectionFragment extends Fragment {

    ListView listViewMainHotels;

    HotelsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewMainHotels = view.findViewById(R.id.listView_main_hotels);
        Hotel.hotels.clear();
        Hotel.addHotels(Hotel.hotels);
        adapter = new HotelsAdapter(getActivity(), R.layout.hotels_list_item, Hotel.hotels);
        listViewMainHotels.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshRating();
    }

    public void refreshRating(){
        adapter.notifyDataSetChanged();
    }
}