package com.example.booklyn.hotel_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booklyn.R;

import java.util.ArrayList;

public class PhotoViewItemFragment extends Fragment {

    ArrayList<Integer> additionalPictures;

    private int pageNumber;

    private int allPages;

    public static PhotoViewItemFragment newInstance(int page, ArrayList<Integer> pictures, int allPages) {
        PhotoViewItemFragment fragment = new PhotoViewItemFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", page);
        args.putIntegerArrayList("ALL_PHOTOS", pictures);
        args.putInt("ALL_PAGES", allPages);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        additionalPictures = getArguments() != null ? getArguments().getIntegerArrayList("ALL_PHOTOS") : null;
        pageNumber = getArguments() != null ? getArguments().getInt("POSITION") : 1;
        allPages = getArguments() != null ? getArguments().getInt("ALL_PAGES") : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_view_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.photo_view_textView_position)).setText((pageNumber + 1) + " из " + allPages);
        ((ImageView)view.findViewById(R.id.photo_view_imageView_photo)).setImageResource(additionalPictures.get(pageNumber));
    }
}