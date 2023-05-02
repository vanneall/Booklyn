package com.example.booklyn.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.Hotel;
import com.example.booklyn.entities.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private LayoutInflater inflater;
    private List<User> users;
    private int layout;


    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        layout = resource;
        users = objects;
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

        User user = users.get(i);

        viewHolder.textViewId.setText(user.getID() + "");
        viewHolder.textViewName.setText(user.getFullName());
        viewHolder.textViewEmail.setText(user.getEmail());
        viewHolder.textViewTelephone.setText(user.getTelephone());
        if (user.getID() == User.ADMIN_ID) viewHolder.buttonDelete.setEnabled(false);
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.openDataBase();
                dataBaseHelper.deleteUserFromDatabase(user.getID());
                dataBaseHelper.close();
                users.remove(user);
                notifyDataSetChanged();
            }
        });
        return view;
    }


    private class ViewHolder{
        final TextView textViewId;
        final TextView textViewName;
        final TextView textViewTelephone;
        final TextView textViewEmail;
        final Button buttonDelete;

        private ViewHolder(View view) {
            textViewId = view.findViewById(R.id.user_list_item_textView_id);
            textViewName = view.findViewById(R.id.user_list_item_textView_name);
            textViewEmail = view.findViewById(R.id.user_list_item_textView_email);
            textViewTelephone = view.findViewById(R.id.user_list_item_textView_telephone);
            buttonDelete = view.findViewById(R.id.user_list_item_button_delete);
        }
    }
}
