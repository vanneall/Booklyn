package com.example.booklyn.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.booklyn.MainActivity;
import com.example.booklyn.R;
import com.example.booklyn.entities.Hotel;

import java.util.List;

public class HotelsAdapter extends ArrayAdapter<Hotel> {
    private LayoutInflater inflater;

    private List<Hotel> hotels;

    private int layout;

    public HotelsAdapter(@NonNull Context context, int resource, @NonNull List<Hotel> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        hotels = objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }
        ImageView imageView = view.findViewById(R.id.imageView_hotel_list_item_picture);
        TextView textViewName = view.findViewById(R.id.textView_hotel_list_item_name);
        TextView textViewRate = view.findViewById(R.id.textView_hotel_list_item_rate);
        TextView textViewPrice = view.findViewById(R.id.textView_hotel_list_item_price);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView textViewNumberOfFeedbacks = view.findViewById(R.id.number_of_feedbacks);
        TextView textViewRatingEstimation = view.findViewById(R.id.rating_estimation);
        Button button = view.findViewById(R.id.button_selection_hotel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                //TODO: поменять
                bundle.putInt(Hotel.SELECTED_HOTEL, i);
                Navigation.findNavController(button).navigate(R.id.action_hotelSelectionFragment_to_mainPageFragment, bundle);
            }
        });

        Hotel hotel = hotels.get(i);
        imageView.setImageResource(hotel.getMainPicture());
        int num = (int) (hotel.getAvgRate() * 10);
        if (num >= 45) {
            textViewRatingEstimation.setText(view.getResources().getString(R.string.very_good));
        } else if (num >= 35) {
            textViewRatingEstimation.setText(view.getResources().getString(R.string.good));
        } else if (num >= 30) {
            textViewRatingEstimation.setText(view.getResources().getString(R.string.normal));
        } else if (num > 20) {
            textViewRatingEstimation.setText(view.getResources().getString(R.string.not_bad));
        } else {
            textViewRatingEstimation.setText(view.getResources().getString(R.string.terrible));
        }
        textViewNumberOfFeedbacks.setText("● " + hotel.getRates().size() + " " + view.getResources().getQuantityString(R.plurals.feedbacks, hotel.getRates().size()));
        textViewName.setText(hotel.getName());
        textViewRate.setText(String.valueOf(hotel.getAvgRate()));
        textViewPrice.setText(hotel.getMinPrice() + "₽");
        ratingBar.setRating(hotel.getAvgRate());
        return view;
    }
}
