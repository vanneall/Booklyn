package com.example.booklyn.authorization;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;
import com.example.booklyn.text_watchers.TelephoneTextWatcher;

public class AuthorizationFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_authorization, container, false);
    }

    EditText editTextTelephone;
    EditText editTextPassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Если есть активный пользователь в БД
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        dataBaseHelper.openDataBase();
        User user = dataBaseHelper.checkActiveUser();
        dataBaseHelper.close();
        if (user != null) {
            clickActionToHotelSelection(user, view);
        }

        super.onViewCreated(view, savedInstanceState);
        bottomNavigationVisibaleController.setInvisible();
        //Переход на создание аккаунта
        TextView textViewCreateAccount = view.findViewById(R.id.authorization_textView_create_account);
        textViewCreateAccount.setOnClickListener(this::clickActionToRegistration);

        //Поля для ввода данных
        editTextTelephone = view.findViewById(R.id.authorization_editText_telephone);
        editTextTelephone.addTextChangedListener(new TelephoneTextWatcher());
        editTextPassword = view.findViewById(R.id.authorization_editText_password);

        Button button = view.findViewById(R.id.authorization_button_enter);
        button.setOnClickListener(this::clickEnter);

        //Переход к восстановлению пароля
        TextView textViewForgetPassword = view.findViewById(R.id.authorization_textView_forget_password);
        textViewForgetPassword.setOnClickListener(this::clickActionToForgetPassword);
    }

    private void clickActionToHotelSelection(User user, View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(User.SELECTED_USER, user);
        bottomNavigationVisibaleController.setVisible();
        Navigation.findNavController(view).navigate(R.id.action_authorizationFragment_to_hotelSelectionFragment, bundle);
    }

    private void clickActionToForgetPassword(View view){
        Navigation.findNavController(view).navigate(R.id.action_authorizationFragment_to_forgetPasswordFragment);
    }

    private void clickActionToRegistration(View view){
        Navigation.findNavController(view).navigate(R.id.action_authorizationFragment_to_registrationFragment);
    }

    private void clickEnter(View view) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        dataBaseHelper.openDataBase();
        User user = dataBaseHelper.getUser(editTextTelephone.getText().toString(), editTextPassword.getText().toString());
        dataBaseHelper.close();
        if (user != null) {
            clickActionToHotelSelection(user, view);
        } else {
            Toast.makeText(getActivity(), "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onDetach() {
        bottomNavigationVisibaleController = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        editTextPassword = null;
        editTextTelephone = null;
        super.onDestroyView();
    }
}