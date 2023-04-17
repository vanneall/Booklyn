package com.example.booklyn.hotel_page;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.os.Bundle;
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
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Room;
import com.example.booklyn.entities.TripDate;


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

        void createFeedbackPage(FeedbackFragment feedbackFragment);

        void createReviewPage(ReviewFragment reviewFragment);

        void createPhotosPage(PhotosFragment photosFragment);
    }
    PageController pageController;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pageController = (PageController) context;
        transfer = (Transfer) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = getArguments().getInt(Hotel.SELECTED_HOTEL);
        hotel = Hotel.hotels.get(i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pageController.createReviewPage(new ReviewFragment(hotel, i));
        pageController.createFeedbackPage(new FeedbackFragment(hotel));
        pageController.createPhotosPage(new PhotosFragment(hotel.getAdditionalPictures()));
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
            public void onClick(View v) {
                check = transfer.getTripDate();
                room = transfer.getRoom();
                if (room != null && check[0] != null && check[1] != null){
                    Bundle bundle = new Bundle();
                    bundle.putInt(SELECTED_HOTEL, i);
                    bundle.putParcelable(SELECTED_ROOM, room);
                    bundle.putLong(SELECTED_DATE_IN, check[0].getInnerDate().getTime());
                    bundle.putLong(SELECTED_DATE_OUT, check[1].getInnerDate().getTime());
                    Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_orderInfoFragment, bundle);
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

    public void dataChanged() {

    }
}