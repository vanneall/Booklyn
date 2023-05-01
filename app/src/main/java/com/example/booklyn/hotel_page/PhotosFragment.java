package com.example.booklyn.hotel_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.adapters.PhotosAdapter;

public class PhotosFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Кнопка назад
        ImageView imageViewBack = view.findViewById(R.id.photos_imageView_sign_back);
        imageViewBack.setOnClickListener(this::clickBack);

        //Список фотографий
        GridView gridView = view.findViewById(R.id.photos_gridView_photos);
        PhotosAdapter photosAdapter = new PhotosAdapter(getActivity(), R.layout.photos_list_item, getArguments().getIntegerArrayList(PhotosAdapter.ALL_HOTEL_PHOTOS));
        gridView.setAdapter(photosAdapter);
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }
}