package com.example.booklyn.hotel_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.adapters.RoomsAdapter;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;

import java.util.ArrayList;


public class RoomSelectionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Кнопка назад
        ImageView imageViewBack = view.findViewById(R.id.room_selection_imageView_sign_back);
        imageViewBack.setOnClickListener(this::clickBack);

        Hotel hotel = getArguments().getParcelable(Hotel.SELECTED_HOTEL);
        ArrayList<Room> rooms = hotel.getRooms();

        //Список доступных комнат
        ListView listView = view.findViewById(R.id.room_selection_listView_apartaments);
        RoomsAdapter roomsAdapter = new RoomsAdapter(getActivity(), R.layout.room_selection_list_item, rooms, hotel);
        listView.setAdapter(roomsAdapter);
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }
}