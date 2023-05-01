package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.UserGetter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AddNewRoomFragment extends Fragment {

    UserGetter userGetter;

    EditText editTextRoomTitle;
    EditText editTextRoomPrice;
    EditText editTextRoomInfo;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userGetter = (UserGetter) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Кнопка назад
        ImageView imageViewBack = view.findViewById(R.id.add_new_room_imageView_sign_back);
        imageViewBack.setOnClickListener(this::clickBack);

        //Поля для ввода информации о комнате
        editTextRoomTitle = view.findViewById(R.id.add_new_room_editText_room_title);
        editTextRoomPrice = view.findViewById(R.id.add_new_room_editText_room_price);
        editTextRoomInfo = view.findViewById(R.id.add_new_room_editText_room_info);

        //Кнопка создания новой комнаты
        Button button = view.findViewById(R.id.add_new_room_button_apply);
        button.setOnClickListener(this::clickAddNewRoom);
    }

    private void clickAddNewRoom(View view){
        if (!editTextRoomTitle.getText().toString().equals("") &&
                !editTextRoomPrice.getText().toString().equals("") &&
                !editTextRoomInfo.getText().toString().equals("")){
            Hotel hotel = getArguments().getParcelable(Hotel.SELECTED_HOTEL);
            hotel.rooms.add(new Room(editTextRoomTitle.getText().toString(), editTextRoomInfo.getText().toString(), Integer.parseInt(editTextRoomPrice.getText().toString())));
            //Добавление комнаты в базу данных
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
            dataBaseHelper.openDataBase();
            dataBaseHelper.writeRoomToDatabase(hotel.rooms.get(hotel.rooms.size() - 1), hotel.getID());
            dataBaseHelper.close();

            Toast.makeText(getActivity(), "Комната успешно добавлена", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).popBackStack();
        } else {
            Snackbar.make(view, "Все поля должны быть заполнены!", Snackbar.LENGTH_LONG).show();
        }
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editTextRoomInfo = null;
        editTextRoomTitle = null;
        editTextRoomPrice = null;
    }

    @Override
    public void onDetach() {
        userGetter = null;
        super.onDetach();
    }
}