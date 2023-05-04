package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.booklyn.R;
import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.User;
import com.example.booklyn.entities.UserGetter;
import com.example.booklyn.text_watchers.HotelTextWatcher;

import java.util.ArrayList;

public class HotelSelectionFragment extends Fragment {

    UserGetter userGetter;

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
    User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainPageController = (MainPageController) context;
        userGetter = (UserGetter) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Установка пользователя
        user = getArguments().getParcelable(User.SELECTED_USER);
        if (user != null) {
            mainPageController.setUser(user);
            mainPageController.setMainPage(this);
        }
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
        adapter = new HotelsAdapter(getActivity(), R.layout.hotels_list_item, mainPageController.getHotels(), userGetter.getUser().getID() ==  User.ADMIN_ID);
        listViewMainHotels.setAdapter(adapter);

        //Установка компонента сортировщика отелей
        sortFragment = new SortFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_container_for_sort_and_filter, sortFragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().hide(sortFragment).commit();

        //Кнопка открытия сортировщика
        TextView textViewSort = view.findViewById(R.id.main_textView_sort);
        textViewSort.setOnClickListener(this::clickOpenSort);

        //Кнопка создания нового отеля
        TextView textViewAddNewHotel = view.findViewById(R.id.main_textView_add_new_hotel);
        if (userGetter.getUser().getID() != User.ADMIN_ID) {
            textViewAddNewHotel.setVisibility(View.GONE);
        } else {
            textViewAddNewHotel.setOnClickListener(this::clickActionToAddNewHotel);
        }

        //Поле для выборки отелей
        EditText editText = view.findViewById(R.id.main_editText_search_hotel);
        editText.addTextChangedListener(new HotelTextWatcher(listViewMainHotels, adapter, userGetter.getUser()));
    }

    @Override
    public void onStart() {
        super.onStart();
        dataChanged();
    }

    private void clickActionToAddNewHotel(View view){
        Navigation.findNavController(view).navigate(R.id.action_hotelSelectionFragment_to_addNewHotelFragment);
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