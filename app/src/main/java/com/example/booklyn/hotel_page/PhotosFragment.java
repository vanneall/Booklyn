package com.example.booklyn.hotel_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.booklyn.R;
import com.example.booklyn.adapters.PhotosAdapter;

import java.util.ArrayList;

public class PhotosFragment extends Fragment {

    ArrayList<Integer> photos;

    public PhotosFragment(ArrayList<Integer> photos){
        this.photos = photos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = view.findViewById(R.id.feedback_fragment_gridView_photos);
        PhotosAdapter photosAdapter = new PhotosAdapter(getActivity(), R.layout.photos_list_item, photos);
        gridView.setAdapter(photosAdapter);
    }
}