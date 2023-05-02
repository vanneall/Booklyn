package com.example.booklyn.user_page;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;
import com.example.booklyn.text_watchers.TelephoneTextWatcher;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class UserPageFragment extends Fragment {


    public interface UserGetter {
        User getUser();
    }

    UserGetter userGetter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userGetter = (UserGetter) context;
    }
    User user;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextTelephone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = userGetter.getUser();

        //Поля для ввода информации
        editTextName = view.findViewById(R.id.user_page_textView_full_name);
        editTextEmail = view.findViewById(R.id.user_page_textView_email);
        editTextTelephone = view.findViewById(R.id.user_page_textView_telephone);
        editTextTelephone.addTextChangedListener(new TelephoneTextWatcher());

        editTextName.setText(user.getFullName());
        editTextEmail.setText(user.getEmail());
        editTextTelephone.setText(user.getTelephone());

        //Кнопка для обновления данных о пользователе
        Button button = view.findViewById(R.id.user_page_upload_new_data);
        button.setOnClickListener(this::clickUpload);

        //Кнопка для просмотра всех пользователей
        Button buttonCheckAllUsers = view.findViewById(R.id.user_page_check_all_users);
        if (user.getID() == User.ADMIN_ID) {
            buttonCheckAllUsers.setOnClickListener(this::clickActionToAllUsers);
        } else {
            buttonCheckAllUsers.setVisibility(View.GONE);
        }
    }

    private void clickActionToAllUsers(View view){
        Navigation.findNavController(view).navigate(R.id.action_userPageFragment_to_allUsersPageFragment);
    }

    private void clickUpload(View view) {
        if (!editTextName.getText().toString().equals("") &&
                Pattern.matches(User.EMAIL_PATTERN, editTextEmail.getText().toString()) &&
                editTextTelephone.getText().toString().length() == 18) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
            dataBaseHelper.openDataBase();
            dataBaseHelper.userUpdate(user.getID(), editTextName.getText().toString(),
                    editTextEmail.getText().toString(),
                    editTextTelephone.getText().toString());
            dataBaseHelper.close();
            Toast.makeText(getActivity(), "Успешно", Toast.LENGTH_LONG).show();
        } else if (!Pattern.matches(User.EMAIL_PATTERN, editTextEmail.getText().toString())) {
            Snackbar.make(view, "Некорректные данные почты!", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, "Обнаружены пустые поля", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        editTextName = null;
        editTextEmail = null;
        editTextTelephone = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        userGetter = null;
        super.onDetach();
    }
}