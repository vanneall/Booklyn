package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.adapters.RoomsAdapter;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.User;
import com.example.booklyn.entities.UserGetter;

import java.util.ArrayList;


public class RoomSelectionFragment extends Fragment {

    UserGetter userGetter;
    RoomsAdapter roomsAdapter;
    Hotel hotel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userGetter = (UserGetter) context;
    }

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

        hotel = getArguments().getParcelable(Hotel.SELECTED_HOTEL);

        //Кнопка добавления новой комнаты
        TextView textViewAddNewRoom = view.findViewById(R.id.room_selection_textView_add_new_room);
        if (userGetter.getUser().getID() == User.ADMIN_ID){
            textViewAddNewRoom.setOnClickListener(this::clickActionToAddNewRoom);
        } else {
            textViewAddNewRoom.setVisibility(View.GONE);
        }

        //Список доступных комнат
        ListView listView = view.findViewById(R.id.room_selection_listView_apartaments);
        roomsAdapter = new RoomsAdapter(getActivity(), R.layout.room_selection_list_item, hotel.getRooms(), hotel, userGetter.getUser().getID() == User.ADMIN_ID);
        listView.setAdapter(roomsAdapter);
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    private void clickActionToAddNewRoom(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Hotel.SELECTED_HOTEL, hotel);
        Navigation.findNavController(view).navigate(R.id.action_roomSelectionFragment_to_addNewRoomFragment, bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        roomsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        userGetter = null;
        super.onDetach();
    }
}