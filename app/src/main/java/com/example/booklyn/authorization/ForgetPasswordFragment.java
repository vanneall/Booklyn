package com.example.booklyn.authorization;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;
import com.example.booklyn.text_watchers.TelephoneTextWatcher;
import com.google.android.material.snackbar.Snackbar;

public class ForgetPasswordFragment extends Fragment {

    EditText editTextTelephone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Поле для ввода телефона
        editTextTelephone = view.findViewById(R.id.forget_password_editText_telephone);
        editTextTelephone.addTextChangedListener(new TelephoneTextWatcher());

        //Кнопка для продолжения
        Button button = view.findViewById(R.id.forget_password_button_continue);
        button.setOnClickListener(this::clickFindUser);

        //Кнопка для возврата
        TextView textViewEnterTheSystem = view.findViewById(R.id.forget_password_textView_enter_the_system);
        textViewEnterTheSystem.setOnClickListener(this::clickBack);

    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    private void clickFindUser(View view){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        dataBaseHelper.openDataBase();
        int id = dataBaseHelper.findUserByTelephone(editTextTelephone.getText().toString());
        if (id == -1) {
            Snackbar.make(view, "Пользователь не найден", Snackbar.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(User.SELECTED_USER, id + 1);
            Navigation.findNavController(view).navigate(R.id.action_forgetPasswordFragment_to_verificationFragment, bundle);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editTextTelephone = null;
    }
}