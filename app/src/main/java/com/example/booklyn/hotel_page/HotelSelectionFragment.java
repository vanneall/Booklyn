package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.User;

import java.util.ArrayList;

public class HotelSelectionFragment extends Fragment {


    public interface MainPageController {
        void setMainPage(HotelSelectionFragment hotelSelectionFragment);
        ArrayList<Hotel> getHotels();
        void setUser(User user);
        void setVisible();
    }

    MainPageController mainPageController;
    ListView listViewMainHotels;
    HotelsAdapter adapter;
    SortFragment sortFragment;

    private boolean isHideSortAndFilterFragment = true;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainPageController = (MainPageController) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Установка пользователя
        mainPageController.setUser(getArguments().getParcelable(User.SELECTED_USER));
        mainPageController.setMainPage(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPageController.setVisible();

        //Список всех отелей
        listViewMainHotels = view.findViewById(R.id.main_listView_hotels);
        adapter = new HotelsAdapter(getActivity(), R.layout.hotels_list_item, mainPageController.getHotels());
        listViewMainHotels.setAdapter(adapter);

        //Установка компонента сортировщика отелей
        sortFragment = new SortFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_container_for_sort_and_filter, sortFragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().hide(sortFragment).commit();

        //Кнопка открытия сортировщика
        TextView textViewSort = view.findViewById(R.id.main_textView_sort);
        textViewSort.setOnClickListener(this::clickOpenSort);
    }

    @Override
    public void onStart() {
        super.onStart();
        dataChanged();
    }

    public void dataChanged() {
        adapter.notifyDataSetChanged();
    }

    private void clickOpenSort(View view) {
        if (isHideSortAndFilterFragment) {
            getActivity().getSupportFragmentManager().beginTransaction().show(sortFragment).commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction().hide(sortFragment).commit();
        }
        isHideSortAndFilterFragment = !isHideSortAndFilterFragment;
    }

    @Override
    public void onDestroyView() {
        listViewMainHotels = null;
        adapter = null;
        sortFragment = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mainPageController = null;
        super.onDetach();
    }
}