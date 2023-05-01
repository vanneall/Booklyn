package com.example.booklyn.settings_page;

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

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;

public class SettingsPageFragment extends Fragment {

    public interface UserGetter{
        public User getUser();
    }

    UserGetter userGetter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userGetter = (UserGetter) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_page, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.settings_page_button_privacy_and_policy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(button).navigate(R.id.action_settingsPageFragment_to_policyAndPrivacyFragment);
            }
        });

        Button button1 = view.findViewById(R.id.settings_page_button_terms_and_conditions);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(button1).navigate(R.id.action_settingsPageFragment_to_termsAndConditionsFragment);
            }
        });

        Button button2 = view.findViewById(R.id.settings_page_button_about_the_app);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(button2).navigate(R.id.action_settingsPageFragment_to_aboutAppFragment);
            }
        });

        Button button3 = view.findViewById(R.id.settings_page_button_support);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(button3).navigate(R.id.action_settingsPageFragment_to_supportFragment);
            }
        });

        Button buttonLeave = view.findViewById(R.id.settings_page_button_leave);
        buttonLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                dataBaseHelper.openDataBase();
                dataBaseHelper.leftFromAccount(userGetter.getUser().getID());
                dataBaseHelper.close();
                getActivity().finish();
            }
        });
    }
}