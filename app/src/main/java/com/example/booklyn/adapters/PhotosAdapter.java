package com.example.booklyn.adapters;

import com.example.booklyn.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import java.util.List;

public class PhotosAdapter extends ArrayAdapter<Integer> {

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
        return view;
    }
}
