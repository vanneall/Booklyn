package com.example.booklyn.user_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.booklyn.R;
import com.example.booklyn.adapters.UserAdapter;
import com.example.booklyn.database_classes.DataBaseHelper;

public class AllUsersPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_users_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.all_users_page_listView);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        dataBaseHelper.openDataBase();
        UserAdapter userAdapter = new UserAdapter(getActivity(), R.layout.user_list_item, dataBaseHelper.getAllUsers());
        dataBaseHelper.close();
        listView.setAdapter(userAdapter);
    }
}