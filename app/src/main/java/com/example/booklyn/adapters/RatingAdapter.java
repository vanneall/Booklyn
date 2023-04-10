package com.example.booklyn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        TextView feedbackRating = view.findViewById(R.id.feedback_rating);
        TextView feedbackCommentary = view.findViewById(R.id.feedback_commentary);

        feedbackRating.setText(String.valueOf(rate.getRate()));
        feedbackCommentary.setText(rate.getInfo());
        return view;
    }
}
