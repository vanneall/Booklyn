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
import com.example.booklyn.adapters.PhotosAdapter;

import java.util.ArrayList;

public class PhotoViewItemFragment extends Fragment {

    ArrayList<Integer> additionalPictures;
    private int pageNumber;
    private int allPages;

    public static PhotoViewItemFragment newInstance(int page, ArrayList<Integer> pictures) {
        PhotoViewItemFragment fragment = new PhotoViewItemFragment();
        Bundle args = new Bundle();
        args.putInt(PhotosAdapter.PHOTO_POSITION, page);
        args.putIntegerArrayList(PhotosAdapter.ALL_HOTEL_PHOTOS, pictures);
        args.putInt(PhotosAdapter.AMOUNT_OF_PAGES, pictures.size());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        additionalPictures = getArguments().getIntegerArrayList(PhotosAdapter.ALL_HOTEL_PHOTOS);
        pageNumber = getArguments().getInt(PhotosAdapter.PHOTO_POSITION);
        allPages = getArguments().getInt(PhotosAdapter.AMOUNT_OF_PAGES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_view_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Надпись с выбранной страницей
        ((TextView)view.findViewById(R.id.photo_view_textView_position)).setText((pageNumber + 1) + " из " + allPages);
        ((ImageView)view.findViewById(R.id.photo_view_imageView_photo)).setImageResource(additionalPictures.get(pageNumber));
    }
}