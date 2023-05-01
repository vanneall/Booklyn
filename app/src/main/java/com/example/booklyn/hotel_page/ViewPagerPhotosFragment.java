package com.example.booklyn.hotel_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booklyn.R;
import com.example.booklyn.adapters.PhotoViewPagerAdapter;

import java.util.ArrayList;

public class ViewPagerPhotosFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Integer> photos = getArguments().getIntegerArrayList("ALL_PHOTOS");
        int position = getArguments().getInt("POSITION");
        ViewPager2 pager = view.findViewById(R.id.view_pager_photos_viewPager);
        PhotoViewPagerAdapter adapter = new PhotoViewPagerAdapter(getActivity(), photos);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position, false);
    }
}
