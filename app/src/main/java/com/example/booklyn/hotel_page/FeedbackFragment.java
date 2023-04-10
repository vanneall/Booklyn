package com.example.booklyn.hotel_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.booklyn.HotelSelectionActivity;
import com.example.booklyn.R;
import com.example.booklyn.adapters.RatingAdapter;
import com.example.booklyn.entities.Hotel;

public class FeedbackFragment extends Fragment {

    Hotel hotel;
    RatingAdapter ratingAdapter;
    ListView listView;

    View homeView;

    public FeedbackFragment(Bundle bundle) {
        hotel = Hotel.hotels.get(bundle.getInt(Hotel.SELECTED_HOTEL));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeView = view;
        listView = view.findViewById(R.id.feedback_list_view);
        ratingAdapter = new RatingAdapter(view.getContext(), R.layout.feedback_list_item, hotel.getRates());
        listView.setAdapter(ratingAdapter);
        setRatingProcents(view);
        TextView textViewAddFeedback = view.findViewById(R.id.feedback_add_feedback);
        textViewAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DialogAddFragment();
                dialogFragment.show(getFragmentManager(), "tag1");
            }
        });
    }

    public void addRate(float rate, String info) {
        hotel.addRate(rate, info);
        setRatingProcents(homeView);
        ratingAdapter.notifyDataSetChanged();
    }

    private void setRatingProcents(View view) {
        TextView textView5stars = view.findViewById(R.id.feedback_percent5);
        TextView textView4stars = view.findViewById(R.id.feedback_percent4);
        TextView textView3stars = view.findViewById(R.id.feedback_percent3);
        TextView textView2stars = view.findViewById(R.id.feedback_percent2);
        TextView textView1stars = view.findViewById(R.id.feedback_percent1);

        int[] rateCount = hotel.getRateCount();
        int sum = hotel.getRates().size();
        textView5stars.setText((int) (((float) rateCount[4] / sum) * 100) + "%");
        textView4stars.setText((int) (((float) rateCount[3] / sum) * 100) + "%");
        textView3stars.setText((int) (((float) rateCount[2] / sum) * 100) + "%");
        textView2stars.setText((int) (((float) rateCount[1] / sum) * 100) + "%");
        textView1stars.setText((int) (((float) rateCount[0] / sum) * 100) + "%");
    }
}