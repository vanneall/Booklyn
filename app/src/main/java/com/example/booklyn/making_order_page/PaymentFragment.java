package com.example.booklyn.making_order_page;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.widget.ImageView;

import com.example.booklyn.R;
import com.example.booklyn.text_watchers.CardDataTextWatcher;
import com.example.booklyn.text_watchers.CardHolderTextWatcher;
import com.example.booklyn.text_watchers.CardNumberTextWatcher;
import com.google.android.material.snackbar.Snackbar;

public class PaymentFragment extends Fragment {

    public static final String BOOKING_CHANNEL = "BOOKING_CHANNEL";

    public static final int NOTIFY_ID = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    EditText editTextCVV;
    EditText editTextCardNumber;
    EditText editTextDate;
    EditText editTextHolderName;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Кнопка назад
        ImageView imageView = view.findViewById(R.id.payment_imageView_back);
        imageView.setOnClickListener(this::clickBack);

        //Ввод номера карты
        editTextCardNumber = view.findViewById(R.id.payment_editText_card_number);
        editTextCardNumber.addTextChangedListener(new CardNumberTextWatcher(view));

        //Ввод срока действия карты
        editTextDate = view.findViewById(R.id.payment_editText_date);
        editTextDate.addTextChangedListener(new CardDataTextWatcher());

        //Ввод имени держателя карты
        editTextHolderName = view.findViewById(R.id.payment_editText_holder_name);
        editTextHolderName.addTextChangedListener(new CardHolderTextWatcher());

        //Ввод CVV карты
        editTextCVV = view.findViewById(R.id.payment_editText_cvv);

        //Кнопка бронирования
        Button button = view.findViewById(R.id.payment_button_pay);
        button.setOnClickListener(this::clickGetBookingMessage);
    }

    private void clickBack(View view) {
        Navigation.findNavController(view).popBackStack();
    }

    private void clickGetBookingMessage(View view){
        if (editTextCVV.getText().toString().length() == 3 &&
                editTextCardNumber.getText().toString().length() == 19 &&
                editTextDate.getText().toString().length() == 5 &&
                !editTextHolderName.getText().toString().equals("")) {
            Bundle bundle = getArguments();
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), BOOKING_CHANNEL)
                    .setAutoCancel(false)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Уведомление о бронировании")
                    .setContentText("Был забронирован номер")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(
                            "Забронирова номер \"" +  bundle.getString("ROOM_NAME") + "\" в отеле \"" +
                                    bundle.getString("HOTEL_NAME") + "\" на " + bundle.getString("NAME") +
                                    " с " + bundle.getString("CHECK_IN") + " по " + bundle.getString("CHECK_OUT")
                    ));
            createChannelIfNeeded(notificationManager, BOOKING_CHANNEL);
            notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
        } else {
            Snackbar.make(view, "Есть незаполненные поля!", Snackbar.LENGTH_LONG).show();
        }

    }

    private static void createChannelIfNeeded(NotificationManager manager, String CHANNEL){
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL, CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
        manager.createNotificationChannel(notificationChannel);
    }

    @Override
    public void onDestroyView() {
        editTextCardNumber = null;
        editTextCVV = null;
        editTextDate = null;
        editTextHolderName = null;
        super.onDestroyView();
    }
}