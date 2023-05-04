package com.example.booklyn.adapters;

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

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.Hotel;

import java.util.List;

public class HotelsAdapter extends ArrayAdapter<Hotel> {
    private LayoutInflater inflater;

    private List<Hotel> hotels;

    private int layout;

    boolean isAdmin;

    public HotelsAdapter(@NonNull Context context, int resource, @NonNull List<Hotel> objects, boolean isAdmin) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        hotels = objects;
        this.isAdmin = isAdmin;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Hotel hotel = hotels.get(i);
        viewHolder.imageView.setImageResource(getContext().getResources().getIdentifier(hotel.getMainPicture(), "drawable", getContext().getPackageName()));
        int num = (int) (hotel.getAvgRate() * 10);
        if (num >= 45) {
            viewHolder.textViewRatingEstimation.setText(view.getResources().getString(R.string.very_good));
        } else if (num >= 35) {
            viewHolder.textViewRatingEstimation.setText(view.getResources().getString(R.string.good));
        } else if (num >= 30) {
            viewHolder.textViewRatingEstimation.setText(view.getResources().getString(R.string.normal));
        } else if (num > 20) {
            viewHolder.textViewRatingEstimation.setText(view.getResources().getString(R.string.not_bad));
        } else {
            viewHolder.textViewRatingEstimation.setText(view.getResources().getString(R.string.terrible));
        }
        viewHolder.textViewNumberOfFeedbacks.setText("● " + hotel.getRates().size() + " " + view.getResources().getQuantityString(R.plurals.feedbacks, hotel.getRates().size()));
        viewHolder.textViewName.setText(hotel.getName());
        viewHolder.textViewRate.setText(String.valueOf(hotel.getAvgRate()));
        viewHolder.textViewPrice.setText(hotel.getMinPrice() + "₽");
        viewHolder.ratingBar.setRating(hotel.getAvgRate());
        viewHolder.textViewLocation.setText(hotel.getLocation());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Hotel.SELECTED_HOTEL, hotels.get(i));
                Navigation.findNavController(view).navigate(R.id.action_hotelSelectionFragment_to_mainPageFragment, bundle);
            }
        });

        if (isAdmin) {
            viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Hotel.hotels.remove(hotel);
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                    dataBaseHelper.openDataBase();
                    dataBaseHelper.deleteHotelFromDatabase(hotel);
                    dataBaseHelper.close();
                    notifyDataSetChanged();
                }
            });
        } else {
            viewHolder.buttonDelete.setVisibility(View.GONE);
        }

        return view;
    }


    private class ViewHolder{
        final ImageView imageView;
        final TextView textViewName;
        final TextView textViewRate;
        final TextView textViewPrice;
        final RatingBar ratingBar;
        final TextView textViewNumberOfFeedbacks;
        final TextView textViewRatingEstimation;
        final Button button;
        final TextView textViewLocation;
        final Button buttonDelete;

        ViewHolder(View view){
            imageView = view.findViewById(R.id.hotel_list_item_imageView_picture);
            textViewName = view.findViewById(R.id.hotel_list_item_textView_name);
            textViewRate = view.findViewById(R.id.hotel_list_item_textView_rate);
            textViewPrice = view.findViewById(R.id.main_page_textView_min_price);
            ratingBar = view.findViewById(R.id.hote_list_item_ratingBar);
            textViewNumberOfFeedbacks = view.findViewById(R.id.hotel_list_item_textView_number_of_feedbacks);
            textViewRatingEstimation = view.findViewById(R.id.hotel_list_item_textView_rating_estimation);
            button = view.findViewById(R.id.hotel_list_item_button_selection_hotel);
            textViewLocation = view.findViewById(R.id.hotels_list_item_textView_location);
            buttonDelete = view.findViewById(R.id.hotel_list_item_button_delete);
        }
    }
}
