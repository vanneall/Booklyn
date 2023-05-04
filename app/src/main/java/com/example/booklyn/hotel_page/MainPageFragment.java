package com.example.booklyn.hotel_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.adapters.PhotosAdapter;
import com.example.booklyn.adapters.RatingAdapter;
import com.example.booklyn.entities.Hotel;


public class MainPageFragment extends Fragment {

    Hotel hotel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotel = getArguments().getParcelable(Hotel.SELECTED_HOTEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Кнопка назад
        ImageView imageView = view.findViewById(R.id.main_page_imageView_sign_back);
        imageView.setOnClickListener(this::clickBack);

        //Основная информация об отеле
        TextView textViewName = view.findViewById(R.id.main_page_textView_title);
        textViewName.setText(hotel.getName());

        TextView textViewInfo = view.findViewById(R.id.main_page_textView_info);
        textViewInfo.setText(hotel.getInfo());

        TextView textViewMinPrice = view.findViewById(R.id.main_page_textView_min_price);
        textViewMinPrice.setText("Цена начинается от " + hotel.getMinPrice() + "₽");

        ImageView imageViewMain = view.findViewById(R.id.main_page_imageView_photo);
        imageViewMain.setImageResource(getContext().getResources().getIdentifier(hotel.getMainPicture(), "drawable", getContext().getPackageName()));

        TextView textViewAvgRating = view.findViewById(R.id.main_page_textView_avgRating);
        textViewAvgRating.setText(String.valueOf(hotel.getAvgRate()));

        TextView textViewAmountOfFeedbacks = view.findViewById(R.id.main_page_textView_amount_of_feedbacks);
        textViewAmountOfFeedbacks.setText("● " + hotel.getRates().size() + " " + getResources().getQuantityString(R.plurals.feedbacks, hotel.getRates().size()));

        RatingBar ratingBar = view.findViewById(R.id.main_page_ratingBar_rating);
        ratingBar.setRating(hotel.getAvgRate());

        TextView textViewLocation = view.findViewById(R.id.main_page_textView_location);
        textViewLocation.setText(hotel.getLocation());

        TextView textViewEmail = view.findViewById(R.id.main_page_textView_email);
        textViewEmail.setText(hotel.getEmail());

        TextView textViewTelephone = view.findViewById(R.id.main_page_textView_telephone);
        textViewTelephone.setText(hotel.getTelephone());

        //Список нескольких комментариев
        ListView listViewFeedbacks = view.findViewById(R.id.main_page_listView_feedbacks);
        RatingAdapter ratingAdapter = new RatingAdapter(getActivity(), R.layout.feedback_list_item, hotel.getRates());
        listViewFeedbacks.setAdapter(ratingAdapter);

        //Переход ко всем комментариям
        TextView textViewAllFeedback = view.findViewById(R.id.main_page_textView_all_feedback);
        textViewAllFeedback.setOnClickListener(this::clickActionToAllFeedbacks);

        //Список нескольких фотографий
        GridView gridViewPhotos = view.findViewById(R.id.main_page_gridView_photos);
        PhotosAdapter photosAdapter = new PhotosAdapter(getActivity(), R.layout.photos_list_item, hotel.getAdditionalPictures());
        gridViewPhotos.setAdapter(photosAdapter);

        //Переход ко всем фотографиям
        TextView textViewAllPhotos = view.findViewById(R.id.main_page_textView_all_photos);
        textViewAllPhotos.setOnClickListener(this::clickActionToAllPhotos);

        //Переход к выбору комнаты
        LinearLayout layout = view.findViewById(R.id.main_page_linearLayout_select_room);
        layout.setOnClickListener(this::clickActionToRoomSelection);
    }

    private void clickActionToRoomSelection(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Hotel.SELECTED_HOTEL, hotel);
        Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_roomSelectionFragment, bundle);
    }

    private void clickActionToAllPhotos(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PhotosAdapter.ALL_HOTEL_PHOTOS, hotel.getAdditionalPictures());
        Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_photosFragment, bundle);
    }

    private void clickActionToAllFeedbacks(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Hotel.SELECTED_HOTEL, hotel);
        Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_feedbackFragment, bundle);
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }

}