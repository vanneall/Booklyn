package com.example.booklyn.authorization;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;
import com.google.android.material.snackbar.Snackbar;

public class ChangePasswordFragment extends Fragment {

    EditText editTextNewPassword;
    EditText editTextNewPasswordAgain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    int id;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id = getArguments().getInt(User.SELECTED_USER);

        //Поля для ввода нового пароля
        editTextNewPassword = view.findViewById(R.id.change_password_editText_new_password);
        editTextNewPasswordAgain = view.findViewById(R.id.change_password_editText_new_password_again);

        Button button = view.findViewById(R.id.change_password_button_continue);
        button.setOnClickListener(this::clickActionToHotelSelection);
    }

    private void clickActionToHotelSelection(View view) {
        if (editTextNewPassword.getText().toString().equals(editTextNewPasswordAgain.getText().toString())){
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
            dataBaseHelper.openDataBase();
            dataBaseHelper.userUpdatePassword(id, editTextNewPassword.getText().toString());
            Bundle bundle = new Bundle();
            User user = dataBaseHelper.getUser(id);
            bundle.putParcelable(User.SELECTED_USER, user);
            dataBaseHelper.close();
            Navigation.findNavController(view).navigate(R.id.action_changePasswordFragment_to_hotelSelectionFragment, bundle);
        } else {
            Snackbar.make(view, "Пароли не совпадают", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editTextNewPassword = null;
        editTextNewPasswordAgain = null;
    }
}