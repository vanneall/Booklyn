package com.example.booklyn.authorization;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booklyn.R;
import com.example.booklyn.database_classes.DataBaseHelper;
import com.example.booklyn.entities.User;

import java.util.Random;

public class VerificationFragment extends Fragment {

    int code;
    NotificationManager notificationManager;
    public static final int NOTIFY_ID = 1;
    public static final String REGISTRATION_CHANNEL = "REGISTRATION_CHANNEL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        code = getVerificationCode();
        String name = getArguments().getString(RegistrationFragment.USER_NAME);
        String email = getArguments().getString(RegistrationFragment.USER_EMAIL);
        String telephone = getArguments().getString(RegistrationFragment.USER_TELEPHONE);
        String password = getArguments().getString(RegistrationFragment.USER_PASSWORD);

        //Поля для воода кода
        EditText editTextCode1 = view.findViewById(R.id.verification_editText_code_1);
        EditText editTextCode2 = view.findViewById(R.id.verification_editText_code_2);
        EditText editTextCode3 = view.findViewById(R.id.verification_editText_code_3);
        EditText editTextCode4 = view.findViewById(R.id.verification_editText_code_4);

        editTextCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) editTextCode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 1) editTextCode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 1) editTextCode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (code == Integer.parseInt(editTextCode1.getText().toString() +
                editTextCode2.getText().toString() +
                editTextCode3.getText().toString() +
                editTextCode4.getText().toString())){
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                    dataBaseHelper.openDataBase();
                    User user = dataBaseHelper.createUser(name, email, telephone, password);
                    dataBaseHelper.close();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("USER", user);
                    Toast.makeText(getActivity(), "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.action_verificationFragment_to_hotelSelectionFragment, bundle);
                } else {
                    Toast.makeText(getActivity(), "Неправильный код", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Кнопка для повторной отправки кода
        Button buttonRemessage = view.findViewById(R.id.verification_button_remessage);
        buttonRemessage.setOnClickListener(this::clickRemessage);

        //Кнопка для смены телефона
        Button buttonChangeNumber = view.findViewById(R.id.verification_button_change_phone);
        buttonChangeNumber.setOnClickListener(this::clickChangeTelephone);
    }

    private void clickRemessage(View view) {
        code = getVerificationCode();
    }

    private void clickChangeTelephone(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    private int getVerificationCode(){
          int code = getNewCode();
          notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
          NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), REGISTRATION_CHANNEL)
                  .setAutoCancel(false)
                  .setSmallIcon(R.mipmap.ic_launcher_round)
                  .setContentTitle("Код подтверждения")
                  .setContentText("Код для подтверждения регистрации " + code);
          createChannelIfNeeded(notificationManager, REGISTRATION_CHANNEL);
          notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
          return code;
    }

    private int getNewCode(){
        return 1000 + new Random().nextInt(9000);
    }

    public static void createChannelIfNeeded(NotificationManager manager, String CHANNEL){
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL, CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
        manager.createNotificationChannel(notificationChannel);
    }
}