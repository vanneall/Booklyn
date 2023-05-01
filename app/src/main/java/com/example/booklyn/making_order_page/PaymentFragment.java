package com.example.booklyn.making_order_page;

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
import android.widget.ImageView;

import com.example.booklyn.R;

public class PaymentFragment extends Fragment {

    public static final String BOOKING_CHANNEL = "BOOKING_CHANNEL";

    public static final int NOTIFY_ID = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.payment_imageView_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        EditText editTextCardNumber = view.findViewById(R.id.payment_editText_card_number);
        editTextCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().startsWith("2")) ((ImageView)view.findViewById(R.id.payment_card_image_type)).setImageResource(R.drawable.mir_logo);
                else if (charSequence.toString().startsWith("4")) ((ImageView)view.findViewById(R.id.payment_card_image_type)).setImageResource(R.drawable.visa_logo);
                else if (charSequence.toString().startsWith("5")) ((ImageView)view.findViewById(R.id.payment_card_image_type)).setImageResource(R.drawable.mastercard_logo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button button = view.findViewById(R.id.payment_button_pay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBookingMessage();
            }
        });
    }


    void getBookingMessage(){
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
    }

    public static void createChannelIfNeeded(NotificationManager manager, String CHANNEL){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL, CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}