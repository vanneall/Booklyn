package com.example.booklyn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.booklyn.R;
import com.example.booklyn.entities.Rate;

import java.util.List;

public class RatingAdapter extends ArrayAdapter<Rate> {

    private LayoutInflater inflater;

    private List<Rate> rates;

    private int layout;

    public RatingAdapter(@NonNull Context context, int resource, @NonNull List<Rate> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        rates = objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }

        Rate rate = rates.get(i);

        RatingBar ratingBar = view.findViewById(R.id.feedback_list_item_ratingBar_rate);
        ratingBar.setRating(rate.getRate());

        TextView feedbackRating = view.findViewById(R.id.feedback_list_item_textView_rating);
        feedbackRating.setText(String.valueOf(rate.getRate()));

        TextView textViewEstimation = view.findViewById(R.id.feedback_list_item_textView_rating_estimantion);
        int num = (int) (rate.getRate() * 10);
        if (num >= 45) {
            textViewEstimation.setText(view.getResources().getString(R.string.very_good));
        } else if (num >= 35) {
            textViewEstimation.setText(view.getResources().getString(R.string.good));
        } else if (num >= 30) {
            textViewEstimation.setText(view.getResources().getString(R.string.normal));
        } else if (num > 20) {
            textViewEstimation.setText(view.getResources().getString(R.string.not_bad));
        } else {
            textViewEstimation.setText(view.getResources().getString(R.string.terrible));
        }

        TextView feedbackDate = view.findViewById(R.id.feedback_list_item_textView_commentary_date);
        feedbackDate.setText("Комментарий оставлен " + rate.getDate());

        TextView feedbackCommentary = view.findViewById(R.id.feedback_list_item_textView_commentary);
        feedbackCommentary.setText(rate.getInfo());
        return view;
    }
}
