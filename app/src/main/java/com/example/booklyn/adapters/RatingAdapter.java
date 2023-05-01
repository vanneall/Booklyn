package com.example.booklyn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Rate;

import java.util.List;

public class RatingAdapter extends ArrayAdapter<Rate> {

    public interface RateSetter {
        void updateRate();
    }

    private LayoutInflater inflater;
    private List<Rate> rates;
    private int layout;
    private boolean isAdmin;
    private Hotel hotel;
    RateSetter rateSetter;

    public RatingAdapter(@NonNull Context context, int resource, @NonNull List<Rate> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        rates = objects;
        this.isAdmin = false;

    }

    public RatingAdapter(@NonNull Context context, int resource, @NonNull List<Rate> objects, boolean isAdmin, Hotel hotel) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        rates = objects;
        this.isAdmin = isAdmin;
        this.hotel = hotel;
        rateSetter = (RateSetter) context;
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

        Rate rate = rates.get(i);
        viewHolder.ratingBar.setRating(rate.getRate());
        viewHolder.feedbackRating.setText(String.valueOf(rate.getRate()));
        viewHolder.feedbackDate.setText("Комментарий оставлен " + rate.getDate());
        viewHolder.feedbackCommentary.setText(rate.getInfo());
        int num = (int) (rate.getRate() * 10);
        if (num >= 45) {
            viewHolder.textViewEstimation.setText(view.getResources().getString(R.string.very_good));
        } else if (num >= 35) {
            viewHolder.textViewEstimation.setText(view.getResources().getString(R.string.good));
        } else if (num >= 30) {
            viewHolder.textViewEstimation.setText(view.getResources().getString(R.string.normal));
        } else if (num > 20) {
            viewHolder.textViewEstimation.setText(view.getResources().getString(R.string.not_bad));
        } else {
            viewHolder.textViewEstimation.setText(view.getResources().getString(R.string.terrible));
        }
        if (!isAdmin) {
            viewHolder.buttonDelete.setVisibility(View.GONE);
        } else {
            viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                    dataBaseHelper.openDataBase();
                    dataBaseHelper.deleteFeedbackFromDatabase(rate, hotel.getID());
                    dataBaseHelper.close();
                    rates.remove(rate);
                    hotel.resetRate();
                    rateSetter.updateRate();
                    notifyDataSetChanged();
                }
            });
        }
        return view;
    }


    private class ViewHolder{
        final RatingBar ratingBar;
        final TextView feedbackRating;
        final TextView textViewEstimation;
        final TextView feedbackDate;
        final TextView feedbackCommentary;
        final Button buttonDelete;
        ViewHolder(View view){
            ratingBar  = view.findViewById(R.id.feedback_list_item_ratingBar_rate);
            feedbackRating = view.findViewById(R.id.feedback_list_item_textView_rating);
            textViewEstimation = view.findViewById(R.id.feedback_list_item_textView_rating_estimantion);
            feedbackDate  = view.findViewById(R.id.feedback_list_item_textView_commentary_date);
            feedbackCommentary = view.findViewById(R.id.feedback_list_item_textView_commentary);
            buttonDelete = view.findViewById(R.id.feedback_list_item_button_delete_feedback);
        }
    }
}
