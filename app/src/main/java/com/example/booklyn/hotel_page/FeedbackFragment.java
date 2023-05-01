package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.adapters.RatingAdapter;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.Rate;
import com.example.booklyn.entities.User;

import java.util.Date;

public class FeedbackFragment extends Fragment {

    Hotel hotel;
    RatingAdapter ratingAdapter;
    ListView listView;

    View homeView;

    public interface FeedbackController {
        void setFeedback(FeedbackFragment feedbackFragment);
        void writeToDB(Rate rate, int hotelID);
        User getUser();
    }
    FeedbackController feedbackController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        feedbackController = (FeedbackController) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedbackController.setFeedback(this);

        //Кнопка выхода
        ImageView imageViewBack = view.findViewById(R.id.feedback_imageView_sign_back);
        imageViewBack.setOnClickListener(this::clickBack);

        homeView = view;
        hotel = getArguments().getParcelable(Hotel.SELECTED_HOTEL);
        setRatingProcents(view);
        setProgressBarRating(view);

        //Список для вывода комментариев
        listView = view.findViewById(R.id.feedback_listView_feedbacks);
        ratingAdapter = new RatingAdapter(view.getContext(), R.layout.feedback_list_item, hotel.getRates(),
                feedbackController.getUser().getID() == User.ADMIN_ID, hotel.getID());
        listView.setAdapter(ratingAdapter);

        //Добавление комментария
        TextView textViewAddFeedback = view.findViewById(R.id.feedback_textView_add_feedback);
        textViewAddFeedback.setOnClickListener(this::clickAddFeedback);
    }

    private void clickAddFeedback(View view){
        DialogFragment dialogFragment = new DialogAddFragment();
        dialogFragment.show(getFragmentManager(), "tag1");
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    public void addRate(float rate, String info) {
        Rate rate1 = new Rate(rate, info, new Date());
        hotel.addRate(rate1);
        feedbackController.writeToDB(rate1, hotel.getID());
        setRatingProcents(homeView);
        setProgressBarRating(homeView);
        ratingAdapter.notifyDataSetChanged();
    }

    private void setProgressBarRating(View view){
        int[] rateCount = hotel.getRateCount();
        int sum = hotel.getRates().size();
        ((ProgressBar)view.findViewById(R.id.feedback_ProgressBar_five)).setProgress((int) (((float) rateCount[4] / sum) * 100));
        ((ProgressBar)view.findViewById(R.id.feedback_ProgressBar_four)).setProgress((int) (((float) rateCount[3] / sum) * 100));
        ((ProgressBar)view.findViewById(R.id.feedback_ProgressBar_three)).setProgress((int) (((float) rateCount[2] / sum) * 100));
        ((ProgressBar)view.findViewById(R.id.feedback_ProgressBar_two)).setProgress((int) (((float) rateCount[1] / sum) * 100));
        ((ProgressBar)view.findViewById(R.id.feedback_ProgressBar_one)).setProgress((int) (((float) rateCount[0] / sum) * 100));
    }

    private void setRatingProcents(View view) {
        TextView textView5stars = view.findViewById(R.id.feedback_textView_percent5);
        TextView textView4stars = view.findViewById(R.id.feedback_textView_percent4);
        TextView textView3stars = view.findViewById(R.id.feedback_textView_percent3);
        TextView textView2stars = view.findViewById(R.id.feedback_textView_percent2);
        TextView textView1stars = view.findViewById(R.id.feedback_textView_percent1);

        int[] rateCount = hotel.getRateCount();
        int sum = hotel.getRates().size();
        textView5stars.setText((int) (((float) rateCount[4] / sum) * 100) + "%");
        textView4stars.setText((int) (((float) rateCount[3] / sum) * 100) + "%");
        textView3stars.setText((int) (((float) rateCount[2] / sum) * 100) + "%");
        textView2stars.setText((int) (((float) rateCount[1] / sum) * 100) + "%");
        textView1stars.setText((int) (((float) rateCount[0] / sum) * 100) + "%");
    }

    @Override
    public void onDestroyView() {
        listView = null;
        ratingAdapter = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        feedbackController = null;
        super.onDetach();
    }
}