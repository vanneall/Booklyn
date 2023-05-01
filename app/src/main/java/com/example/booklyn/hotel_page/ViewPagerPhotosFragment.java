package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.booklyn.R;
import com.example.booklyn.adapters.PhotoViewPagerAdapter;
import com.example.booklyn.adapters.PhotosAdapter;

public class ViewPagerPhotosFragment extends Fragment {

    public interface BottomNavigationVisibaleController {
        void setInvisible();
        void setVisible();
    }
    BottomNavigationVisibaleController bottomNavigationVisibaleController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bottomNavigationVisibaleController = (BottomNavigationVisibaleController) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationVisibaleController.setInvisible();

        //Установка пейджера фотографий
        ViewPager2 pager = view.findViewById(R.id.view_pager_photos_viewPager);
        PhotoViewPagerAdapter adapter = new PhotoViewPagerAdapter(getActivity(), getArguments().getIntegerArrayList(PhotosAdapter.ALL_HOTEL_PHOTOS));
        pager.setAdapter(adapter);

        //Установка определенной страницы
        pager.setCurrentItem(getArguments().getInt(PhotosAdapter.PHOTO_POSITION), false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bottomNavigationVisibaleController.setVisible();
    }

    @Override
    public void onDetach() {
        bottomNavigationVisibaleController = null;
        super.onDetach();
    }
}
