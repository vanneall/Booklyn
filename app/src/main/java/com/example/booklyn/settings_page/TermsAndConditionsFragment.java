package com.example.booklyn.settings_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.booklyn.R;

public class TermsAndConditionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Кнопка назад
        ImageView imageViewBack = view.findViewById(R.id.terms_and_condition_imageView_sign_back);
        imageViewBack.setOnClickListener(this::clickBack);
    }
    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }
}