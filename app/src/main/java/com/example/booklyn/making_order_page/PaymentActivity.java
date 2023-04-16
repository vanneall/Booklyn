package com.example.booklyn.making_order_page;

import androidx.appcompat.app.AppCompatActivity;
import com.example.booklyn.R;
import android.os.Bundle;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        PaymentFragment paymentFragment = new PaymentFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.payment_activity_container, paymentFragment).commit();
    }
}