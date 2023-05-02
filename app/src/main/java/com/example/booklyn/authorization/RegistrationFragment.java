package com.example.booklyn.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;
import com.example.booklyn.text_watchers.TelephoneTextWatcher;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {

    public static final String USER_NAME = "NAME";
    public static final String USER_EMAIL = "EMAIL";
    public static final String USER_TELEPHONE = "TELEPHONE";
    public static final String USER_PASSWORD = "PASSWORD";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    EditText editTextFullName;
    EditText editTextEmail;
    EditText editTextTelephone;
    EditText editTextPassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Поля для данных пользователя
        editTextFullName = view.findViewById(R.id.registration_editText_full_name);
        editTextEmail = view.findViewById(R.id.registration_editText_email);
        editTextTelephone = view.findViewById(R.id.registration_editText_telephone);
        editTextTelephone.addTextChangedListener(new TelephoneTextWatcher());
        editTextPassword = view.findViewById(R.id.registration_editText_password);

        //Кнопка для перехода к верификации
        Button buttonEnter = view.findViewById(R.id.registration_button_enter);
        buttonEnter.setOnClickListener(this::clickRegistrate);

        //Кнопка для перехода к авторизации
        TextView textViewEnterTheSystem = view.findViewById(R.id.registration_textView_enter_the_system);
        textViewEnterTheSystem.setOnClickListener(this::clickBack);

    }

    private void clickRegistrate(View view) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        dataBaseHelper.openDataBase();
        int id = dataBaseHelper.findUserByTelephone(editTextTelephone.getText().toString());
        dataBaseHelper.close();
        if (!editTextFullName.getText().toString().equals("") &&
                Pattern.matches(User.EMAIL_PATTERN, editTextEmail.getText().toString()) &&
                editTextTelephone.getText().toString().length() == 18 &&
                !editTextPassword.getText().toString().equals("") && id == -1){

            Bundle bundle = new Bundle();
            bundle.putString(USER_NAME, editTextFullName.getText().toString());
            bundle.putString(USER_EMAIL,editTextEmail.getText().toString());
            bundle.putString(USER_TELEPHONE, editTextTelephone.getText().toString());
            bundle.putString(USER_PASSWORD, editTextPassword.getText().toString());
            Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_verificationFragment, bundle);
        } else if (id != -1) {
            Snackbar.make(view, "Пользователь с таким номером телефона уже существует", Snackbar.LENGTH_LONG).show();
        } else if (!Pattern.matches(User.EMAIL_PATTERN, editTextEmail.getText().toString())){
            Snackbar.make(view, "Некорректные данные почты!", Snackbar.LENGTH_LONG).show();
        }
        else {
            Snackbar.make(view, "Необходимо заполнить все поля для продолжения", Snackbar.LENGTH_LONG).show();
        }
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    @Override
    public void onDestroyView() {
        editTextFullName = null;
        editTextEmail = null;
        editTextTelephone = null;
        editTextPassword = null;
        super.onDestroyView();
    }
}