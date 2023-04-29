package com.example.booklyn.making_order_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booklyn.R;

public class PaymentFragment extends Fragment {

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

        EditText editTextCardNumber = view.findViewById(R.id.payment_card_number);
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
    }
}