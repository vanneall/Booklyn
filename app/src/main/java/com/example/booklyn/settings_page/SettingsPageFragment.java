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
        User getUser();
    }

    UserGetter userGetter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userGetter = (UserGetter) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Переход к политике и приватности
        Button button = view.findViewById(R.id.settings_page_button_privacy_and_policy);
        button.setOnClickListener(this::clickActionTo);

        //Переход к правилам и условиям
        Button button1 = view.findViewById(R.id.settings_page_button_terms_and_conditions);
        button1.setOnClickListener(this::clickActionTo);

        //Переход к информации об приложении
        Button button2 = view.findViewById(R.id.settings_page_button_about_the_app);
        button2.setOnClickListener(this::clickActionTo);

        //Переход к поддержке
        Button button3 = view.findViewById(R.id.settings_page_button_support);
        button3.setOnClickListener(this::clickActionTo);

        //Выход из аккаунта
        Button buttonLeave = view.findViewById(R.id.settings_page_button_leave);
        buttonLeave.setOnClickListener(this::clickActionTo);
    }

    public void clickActionTo(View view){
        switch (view.getId()){
            case R.id.settings_page_button_privacy_and_policy:{
                Navigation.findNavController(view).navigate(R.id.action_settingsPageFragment_to_policyAndPrivacyFragment);
                break;
            }
            case R.id.settings_page_button_terms_and_conditions:{
                Navigation.findNavController(view).navigate(R.id.action_settingsPageFragment_to_termsAndConditionsFragment);
                break;
            }
            case R.id.settings_page_button_about_the_app:{
                Navigation.findNavController(view).navigate(R.id.action_settingsPageFragment_to_aboutAppFragment);
                break;
            }
            case R.id.settings_page_button_support:{
                Navigation.findNavController(view).navigate(R.id.action_settingsPageFragment_to_supportFragment);
                break;
            }
            case R.id.settings_page_button_leave:{
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                dataBaseHelper.openDataBase();
                dataBaseHelper.leftFromAccount(userGetter.getUser().getID());
                dataBaseHelper.close();
                getActivity().finish();
                break;
            }
        }
    }

    @Override
    public void onDetach() {
        userGetter = null;
        super.onDetach();
    }
}