package com.example.booklyn.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.booklyn.hotel_page.PhotoViewItemFragment;

import java.util.ArrayList;

public class PhotoViewPagerAdapter extends FragmentStateAdapter {

    ArrayList<String> additionalPictures;

    public PhotoViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<String> additionalPictures) {
        super(fragmentActivity);
        this.additionalPictures = additionalPictures;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (PhotoViewItemFragment.newInstance(position, additionalPictures));
    }

    @Override
    public int getItemCount() {
        return additionalPictures.size();
    }
}
