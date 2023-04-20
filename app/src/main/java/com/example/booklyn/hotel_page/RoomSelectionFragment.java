package com.example.booklyn.hotel_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.booklyn.MainActivity;
import com.example.booklyn.R;
import com.example.booklyn.adapters.RoomsAdapter;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class RoomSelectionFragment extends Fragment {

    public interface SelectedRoomGetter {
        void setRoom(Room room);
    }
    SelectedRoomGetter selectedRoomGetter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedRoomGetter = (SelectedRoomGetter) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Hotel hotel = getArguments().getParcelable(Hotel.SELECTED_HOTEL);
        ArrayList<Room> rooms = hotel.getRooms();
        ListView listView = view.findViewById(R.id.room_select_listView);
        RoomsAdapter roomsAdapter = new RoomsAdapter(getActivity(), R.layout.room_selection_list_item, rooms, hotel);
        listView.setAdapter(roomsAdapter);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("fuck").commit();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
                selectedRoomGetter.setRoom(rooms.get(i));
                Snackbar.make(getActivity(), v, "Номер выбран", Snackbar.LENGTH_LONG).show();
            }
        });

    }
}