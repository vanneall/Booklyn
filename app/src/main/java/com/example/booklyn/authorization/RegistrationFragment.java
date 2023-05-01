package com.example.booklyn.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.text_watchers.TelephoneTextWatcher;
import com.google.android.material.snackbar.Snackbar;

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

        Button buttonEnter = view.findViewById(R.id.registration_button_enter);
        buttonEnter.setOnClickListener(this::clickRegistrate);
    }

    private void clickRegistrate(View view) {
        if (!editTextFullName.getText().toString().equals("") &&
                !editTextEmail.getText().toString().equals("") &&
                !editTextTelephone.getText().toString().equals("") &&
                !editTextPassword.getText().toString().equals("")){

            Bundle bundle = new Bundle();
            bundle.putString(USER_NAME, editTextFullName.getText().toString());
            bundle.putString(USER_EMAIL,editTextEmail.getText().toString());
            bundle.putString(USER_TELEPHONE, editTextTelephone.getText().toString());
            bundle.putString(USER_PASSWORD, editTextPassword.getText().toString());
            Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_verificationFragment, bundle);
        }
        else {
            Snackbar.make(view, "Необходимо заполнить все поля для продолжения", Snackbar.LENGTH_LONG).show();
        }
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