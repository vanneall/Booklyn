package com.example.booklyn.hotel_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;
import com.example.booklyn.making_order_page.OrderInfoActivity;


public class MainPageFragment extends Fragment {

    public static final int FEEDBACK_PAGE = 0;

    public static final int REVIEW_PAGE = 1;

    public static final int PHOTOS_PAGE = 2;

    public static final String SELECTED_HOTEL = "hotel";
    public static final String SELECTED_ROOM = "room";
    public static final String SELECTED_DATE_IN = "date_in";

    public static final String SELECTED_DATE_OUT = "date_out";

    Hotel hotel;
    TripDate[] check;
    Room room;
    int i;

    public interface Transfer {
        TripDate[] getTripDate();

        Room getRoom();
    }

    Transfer transfer;

    public interface PageController {
        void switchPage(int page);
    }

    PageController pageController;

    public MainPageFragment(Bundle bundle) {
        i = bundle.getInt(Hotel.SELECTED_HOTEL);
        hotel = Hotel.hotels.get(i);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pageController = (PageController) context;
        transfer = (Transfer) context;
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

        TextView textViewPrice = view.findViewById(R.id.main_page_textView_price_for_night);
        textViewPrice.setText(hotel.getMinPrice() + "₽ за ночь");

        ImageView imageViewMain = view.findViewById(R.id.main_page_imageView_photo);
        imageViewMain.setImageResource(hotel.getMainPicture());

        RadioButton radioButtonFeedback = view.findViewById(R.id.radio_button_feedback);
        RadioButton radioButtonReview = view.findViewById(R.id.radio_button_review);
        RadioButton radioButtonPhotos = view.findViewById(R.id.radio_button_photos);
        radioButtonFeedback.setOnCheckedChangeListener(checkedChangeListener);
        radioButtonReview.setOnCheckedChangeListener(checkedChangeListener);
        radioButtonReview.toggle();
        radioButtonPhotos.setOnCheckedChangeListener(checkedChangeListener);

        ImageView imageViewBack = view.findViewById(R.id.main_page_imageView_back);
        Button button = view.findViewById(R.id.main_page_switch_to_orderActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = transfer.getTripDate();
                room = transfer.getRoom();
                if (room != null && check[0] != null && check[1] != null){
                    Intent intent = new Intent(getActivity(), OrderInfoActivity.class);
                    intent.putExtra(SELECTED_HOTEL, i);
                    intent.putExtra(SELECTED_DATE_IN, check[0].getInnerDate().getTime());
                    intent.putExtra(SELECTED_ROOM, room);
                    intent.putExtra(SELECTED_DATE_OUT, check[1].getInnerDate().getTime());
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Некорректные данные", Toast.LENGTH_LONG).show();
                }

            }
        });


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            boolean isChecked = ((RadioButton) compoundButton).isChecked();
            switch (compoundButton.getId()) {
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