package com.example.booklyn.authorization;

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

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;
import com.google.android.material.snackbar.Snackbar;

public class RegistrationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editTextFullName = view.findViewById(R.id.registration_editText_full_name);
        EditText editTextEmail = view.findViewById(R.id.registration_editText_email);
        EditText editTextTelephone = view.findViewById(R.id.registration_editText_telephone);
        EditText editTextPassword = view.findViewById(R.id.registration_editText_password);
        Button buttonEnter = view.findViewById(R.id.registration_button_enter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextFullName.getText().toString().equals("") &&
                        !editTextEmail.getText().toString().equals("") &&
                        !editTextTelephone.getText().toString().equals("") &&
                        !editTextPassword.getText().toString().equals("")){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", editTextFullName.getText().toString());
                    bundle.putString("email",editTextEmail.getText().toString());
                    bundle.putString("telephone", editTextTelephone.getText().toString());
                    bundle.putString("password", editTextPassword.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_verificationFragment, bundle);
                }
                else {
                    Snackbar.make(view, "Необходимо заполнить все поля для продолжения", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}