package com.example.booklyn.hotel_page;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.adapters.PhotosAdapter;
import com.example.booklyn.adapters.RatingAdapter;
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

    public interface Transfer {
        TripDate[] getTripDate();

        Room getRoom();
    }
    Transfer transfer;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        transfer = (Transfer) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotel = getArguments().getParcelable(Hotel.SELECTED_HOTEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.main_page_imageView_sign_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        TextView textViewName = view.findViewById(R.id.main_page_textView_title);
        textViewName.setText(hotel.getName());

        TextView textViewInfo = view.findViewById(R.id.main_page_textView_info);
        textViewInfo.setText(hotel.getInfo());

        TextView textViewMinPrice = view.findViewById(R.id.textView_hotel_list_item_price);
        textViewMinPrice.setText("Цена начинается от " + hotel.getMinPrice() + "₽");

        ImageView imageViewMain = view.findViewById(R.id.main_page_imageView_photo);
        imageViewMain.setImageResource(hotel.getMainPicture());

        TextView textViewAvgRating = view.findViewById(R.id.main_page_textView_avgRating);
        textViewAvgRating.setText(String.valueOf(hotel.getAvgRate()));

        TextView textViewAmountOfFeedbacks = view.findViewById(R.id.main_page_textView_amount_of_feedbacks);
        textViewAmountOfFeedbacks.setText("● " + hotel.getRates().size() + " " + getResources().getQuantityString(R.plurals.feedbacks, hotel.getRates().size()));

        RatingBar ratingBar = view.findViewById(R.id.main_page_ratingBar_rating);
        ratingBar.setRating(hotel.getAvgRate());


        ListView listViewFeedbacks = view.findViewById(R.id.main_page_listView_feedbacks);
        RatingAdapter ratingAdapter = new RatingAdapter(getActivity(), R.layout.feedback_list_item, hotel.getRates());
        listViewFeedbacks.setAdapter(ratingAdapter);

        GridView gridViewPhotos = view.findViewById(R.id.main_page_gridView_photos);
        PhotosAdapter photosAdapter = new PhotosAdapter(getActivity(), R.layout.photos_list_item, hotel.getAdditionalPictures());
        gridViewPhotos.setAdapter(photosAdapter);

        TextView textViewAllPhotos = view.findViewById(R.id.main_page_textView_all_photos);
        textViewAllPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("ALL_PHOTOS", hotel.getAdditionalPictures());
                Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_photosFragment, bundle);
            }
        });

        TextView textViewAllFeedback = view.findViewById(R.id.main_page_textView_all_feedback);
        textViewAllFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Hotel.SELECTED_HOTEL, hotel);
                Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_feedbackFragment, bundle);
            }
        });

        LinearLayout layout = view.findViewById(R.id.my_linear);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Hotel.SELECTED_HOTEL, hotel);
                Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_roomSelectionFragment, bundle);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}