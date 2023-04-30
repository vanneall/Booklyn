package com.example.booklyn.authorization;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;

public class AuthorizationFragment extends Fragment {

    public interface BottomNavigationSetInvisable {
        void setInvisible();
    }

    BottomNavigationSetInvisable bottomNavigationSetInvisable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bottomNavigationSetInvisable = (BottomNavigationSetInvisable) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authorization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        dataBaseHelper.openDataBase();
        User user = dataBaseHelper.checkActiveUser();
        if (user != null) {
            dataBaseHelper.close();
            Bundle bundle = new Bundle();
            bundle.putParcelable("USER", user);
            Navigation.findNavController(view).navigate(R.id.action_authorizationFragment_to_hotelSelectionFragment, bundle);
        }
        dataBaseHelper.close();

        super.onViewCreated(view, savedInstanceState);
        bottomNavigationSetInvisable.setInvisible();
        TextView textViewCreateAccount = view.findViewById(R.id.authorization_textView_create_account);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_authorizationFragment_to_registrationFragment);
            }
        });

        EditText editTextTelephone = view.findViewById(R.id.authorization_editText_telephone);
        EditText editTextPassword = view.findViewById(R.id.authorization_editText_password);

        Button button = view.findViewById(R.id.authorization_button_enter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                dataBaseHelper.openDataBase();
                User user = dataBaseHelper.getUser(editTextTelephone.getText().toString(), editTextPassword.getText().toString());
                if (user != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("USER", user);
                    Navigation.findNavController(view).navigate(R.id.action_authorizationFragment_to_hotelSelectionFragment, bundle);
                } else {
                    Toast.makeText(getActivity(), "Такого пользователя не существует", Toast.LENGTH_LONG).show();
                }
                dataBaseHelper.close();
            }
        });
    }
}