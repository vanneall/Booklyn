package com.example.booklyn.hotel_page;

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

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.User;
import com.example.booklyn.text_watchers.TelephoneTextWatcher;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class AddNewHotelFragment extends Fragment {

    EditText editTextName;
    EditText editTextLocation;
    EditText editTextTelephone;
    EditText editTextEmail;
    EditText editTextInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_hotel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Кнопка назад
        ImageView imageViewBack = view.findViewById(R.id.add_new_room_imageView_sign_back);
        imageViewBack.setOnClickListener(this::clickBack);

        //Поля для ввода
        editTextName = view.findViewById(R.id.add_new_hotel_textView_name);
        editTextLocation = view.findViewById(R.id.add_new_hotel_textView_location);
        editTextEmail = view.findViewById(R.id.add_new_hotel_textView_email);
        editTextTelephone = view.findViewById(R.id.add_new_hotel_textView_telephone);
        editTextTelephone.addTextChangedListener(new TelephoneTextWatcher());
        editTextInfo = view.findViewById(R.id.add_new_hotel_textView_info);

        //Кнопка добавления
        Button button = view.findViewById(R.id.add_new_hotel_button_add);
        button.setOnClickListener(this::clickAddNewHotel);
    }

    private void clickAddNewHotel(View view) {
        if (!editTextName.getText().toString().equals("") &&
                !editTextLocation.getText().toString().equals("") &&
                editTextTelephone.getText().toString().length() == 18 &&
                !editTextInfo.getText().toString().equals("") &&
                (editTextEmail.getText().toString().equals("") || Pattern.matches(User.EMAIL_PATTERN, editTextEmail.getText().toString()))) {
            Hotel.hotels.add(new Hotel(editTextName.getText().toString(), editTextInfo.getText().toString(),
                    editTextLocation.getText().toString(), editTextTelephone.getText().toString(),
                    editTextEmail.getText().toString()));
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            dataBaseHelper.openDataBase();
            dataBaseHelper.writeHotelToDatabase(Hotel.hotels.get(Hotel.hotels.size() - 1));
            dataBaseHelper.close();
            Navigation.findNavController(view).popBackStack();
        } else if (!editTextEmail.getText().toString().equals("") &&
                !Pattern.matches(User.EMAIL_PATTERN, editTextEmail.getText().toString())){
            Snackbar.make(view, "Некорректные данные почты!", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, "Все поля должны быть заполнены!", Snackbar.LENGTH_LONG).show();
        }
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }
}