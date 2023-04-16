package com.example.booklyn.settings_page;

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

public class SettingsPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.privacy_and_policy_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(button).navigate(R.id.action_settingsPageFragment_to_policyAndPrivacyFragment);
            }
        });

        Button button1 = view.findViewById(R.id.terms_and_conditions_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(button1).navigate(R.id.action_settingsPageFragment_to_termsAndConditionsFragment);
            }
        });

        Button button2 = view.findViewById(R.id.about_the_app_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(button2).navigate(R.id.action_settingsPageFragment_to_aboutAppFragment);
            }
        });

        Button button3 = view.findViewById(R.id.support_button);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(button3).navigate(R.id.action_settingsPageFragment_to_supportFragment);
            }
        });
    }
}