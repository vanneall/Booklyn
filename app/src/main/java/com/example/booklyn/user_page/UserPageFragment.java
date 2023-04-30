package com.example.booklyn.user_page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;

public class UserPageFragment extends Fragment {


    public interface UserGetter{
        User getUser();
    }
    UserGetter userGetter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userGetter = (UserGetter) context;
    }

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = userGetter.getUser();
        EditText editTextName = view.findViewById(R.id.user_page_textView_full_name);
        EditText editTextEmail = view.findViewById(R.id.user_page_textView_email);
        EditText editTextTelephone = view.findViewById(R.id.user_page_textView_telephone);

        editTextName.setText(user.getFullName());
        editTextEmail.setText(user.getEmail());
        editTextTelephone.setText(user.getTelephone());

        Button button = view.findViewById(R.id.user_page_upload_new_data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextName.getText().toString().equals("") &&
                        !editTextEmail.getText().toString().equals("") &&
                        !editTextTelephone.getText().toString().equals("")){
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                    dataBaseHelper.openDataBase();
                    dataBaseHelper.userUpdate(user.getID(), editTextName.getText().toString(),
                            editTextEmail.getText().toString(),
                            editTextTelephone.getText().toString());
                    dataBaseHelper.close();
                    Toast.makeText(getActivity(), "Успешно", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Обнаружены пустые поля", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}