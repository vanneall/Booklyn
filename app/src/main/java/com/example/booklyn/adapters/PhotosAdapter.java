package com.example.booklyn.adapters;

import com.example.booklyn.R;
import com.example.booklyn.hotel_page.PhotoViewActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends ArrayAdapter<Integer> {

    public static final String PHOTO_KEY = "photo_key";

    List<Integer> hotelsImagies;

    int layout;

    LayoutInflater inflater;

    public PhotosAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        hotelsImagies = objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }
        ImageView imageView = view.findViewById(R.id.photos_list_item_imageView);
        imageView.setImageResource(hotelsImagies.get(i));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(imageView.getContext(), PhotoViewActivity.class);
                intent.putIntegerArrayListExtra(PHOTO_KEY, (ArrayList<Integer>) hotelsImagies);
                imageView.getContext().startActivity(intent);
            }
        });
        return view;
    }
}
