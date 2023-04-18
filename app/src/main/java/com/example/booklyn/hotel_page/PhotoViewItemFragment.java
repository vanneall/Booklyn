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
import com.example.booklyn.entities.Hotel;

import java.util.ArrayList;

public class PhotoViewItemFragment extends Fragment {

    ArrayList<Integer> additionalPictures;

    private int pageNumber;

    public PhotoViewItemFragment(){
    }

    public static PhotoViewItemFragment newInstance(int page, ArrayList<Integer> pictures) {
        PhotoViewItemFragment fragment = new PhotoViewItemFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        args.putIntegerArrayList("pictures", pictures);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
        additionalPictures = getArguments() != null ? getArguments().getIntegerArrayList("pictures") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_view_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ImageView)view.findViewById(R.id.activity_photo_view_photo)).setImageResource(additionalPictures.get(pageNumber));
    }
}