package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;


public class MainPageFragment extends Fragment {

    public static final int FEEDBACK_PAGE = 0;

    public static final int REVIEW_PAGE = 1;

    public static final int PHOTOS_PAGE = 2;

    Hotel hotel;

    public interface PageController {
        public void switchPage(int page);
    }
    PageController pageController;

    public MainPageFragment(Bundle bundle){
        hotel =  Hotel.hotels.get(bundle.getInt("hotel_selected"));
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pageController = (PageController) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewName = view.findViewById(R.id.main_page_textView_title);
        textViewName.setText(hotel.getName());

        ImageView imageViewMain = view.findViewById(R.id.main_page_imageView_photo);
        imageViewMain.setImageResource(hotel.getMainPicture());

        RadioButton radioButtonFeedback = view.findViewById(R.id.radio_button_feedback);
        RadioButton radioButtonReview = view.findViewById(R.id.radio_button_review);
        radioButtonReview.toggle();
        RadioButton radioButtonPhotos = view.findViewById(R.id.radio_button_photos);
        radioButtonFeedback.setOnCheckedChangeListener(checkedChangeListener);
        radioButtonReview.setOnCheckedChangeListener(checkedChangeListener);
        radioButtonPhotos.setOnCheckedChangeListener(checkedChangeListener);
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            boolean isChecked = ((RadioButton) compoundButton).isChecked();
            switch (compoundButton.getId()){
                case R.id.radio_button_feedback:
                    if (isChecked) {
                        compoundButton.setBackgroundResource(R.drawable.interactive_reactangle_fill_left);
                        compoundButton.setTextColor(getResources().getColor(R.color.white));
                        pageController.switchPage(FEEDBACK_PAGE);
                    } else {
                        compoundButton.setBackgroundResource(R.drawable.interactive_reactangle_left);
                        compoundButton.setTextColor(getResources().getColor(R.color.primary_color));
                    }
                    break;
                case R.id.radio_button_review:
                    if (isChecked) {
                        compoundButton.setBackgroundResource(R.drawable.interactive_reactangle_fill_middle);
                        compoundButton.setTextColor(getResources().getColor(R.color.white));
                        pageController.switchPage(REVIEW_PAGE);
                    } else {
                        compoundButton.setBackgroundResource(R.drawable.interactive_reactangle_middle);
                        compoundButton.setTextColor(getResources().getColor(R.color.primary_color));
                    }
                    break;
                case R.id.radio_button_photos:
                    if (isChecked) {
                        compoundButton.setBackgroundResource(R.drawable.interactive_reactangle_fill_right);
                        compoundButton.setTextColor(getResources().getColor(R.color.white));
                        pageController.switchPage(PHOTOS_PAGE);
                    } else {
                        compoundButton.setBackgroundResource(R.drawable.interactive_reactangle_right);
                        compoundButton.setTextColor(getResources().getColor(R.color.primary_color));
                    }
                    break;
            }
        }
    };



    @Override
    public void onResume() {
        super.onResume();
    }
}