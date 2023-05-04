package com.example.booklyn.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.booklyn.R;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends ArrayAdapter<String> {

    public static final String ALL_HOTEL_PHOTOS = "ALL_PHOTOS";
    public static final String PHOTO_POSITION = "POSITION";
    public static final String AMOUNT_OF_PAGES = "AMOUNT_OF_PAGES";

    public static List<String> hotelsImagies;
    int layout;
    LayoutInflater inflater;

    public PhotosAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        hotelsImagies = objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.imageView.setImageResource(getContext().getResources().getIdentifier(hotelsImagies.get(i), "drawable", getContext().getPackageName()));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ALL_HOTEL_PHOTOS, (ArrayList<String>) hotelsImagies);
                bundle.putInt(PHOTO_POSITION, i);
                try {
                    Navigation.findNavController(v).navigate(R.id.action_photosFragment_to_viewPagerPhotosFragment, bundle);
                } catch (Exception e) {
                    Navigation.findNavController(v).navigate(R.id.action_mainPageFragment_to_viewPagerPhotosFragment2, bundle);
                }

            }
        });
        return view;
    }

    private class ViewHolder {
        final ImageView imageView;
        private ViewHolder(View view) {
            this.imageView = view.findViewById(R.id.photos_list_item_imageView);
        }
    }
}
