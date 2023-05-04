package com.example.booklyn.text_watchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;

import com.example.booklyn.R;
import com.example.booklyn.adapters.HotelsAdapter;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.User;

public class HotelTextWatcher implements TextWatcher {

    ListView listView;
    HotelsAdapter hotelsAdapter;
    User user;

    public HotelTextWatcher(ListView listView, HotelsAdapter hotelsAdapter, User user){
        this.listView = listView;
        this.hotelsAdapter = hotelsAdapter;
        this.user = user;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().length() == 0) {
            listView.setAdapter(hotelsAdapter);
        } else {
            HotelsAdapter newHotelsAdapter = new HotelsAdapter(hotelsAdapter.getContext(), R.layout.hotels_list_item, Hotel.getHotelsByName(editable.toString()), user.getID() ==  User.ADMIN_ID);
            listView.setAdapter(newHotelsAdapter);
        }
    }
}
