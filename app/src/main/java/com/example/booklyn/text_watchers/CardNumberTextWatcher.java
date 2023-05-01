package com.example.booklyn.text_watchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.example.booklyn.R;

public class CardNumberTextWatcher implements TextWatcher {

    StringBuilder sb = new StringBuilder();
    boolean ignore;
    View view;

    public CardNumberTextWatcher(View view){
        this.view = view;
    }

    private final char numPlace = 'X';

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
        if (!ignore) {
            removeFormat(editable.toString());
            applyFormat(sb.toString());
            ignore = true;
            editable.replace(0, editable.length(), sb.toString());
            ignore = false;
        }
    }

    private void removeFormat(String text) {
        sb.setLength(0);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isNumberChar(c)) {
                sb.append(c);
            }
        }
    }

    private void applyFormat(String text) {
        String template = "XXXX XXXX XXXX XXXX";
        sb.setLength(0);
        for (int i = 0, textIndex = 0; i < template.length() && textIndex < text.length(); i++) {
            if (template.charAt(i) == numPlace) {
                sb.append(text.charAt(textIndex));
                textIndex++;
            } else {
                sb.append(template.charAt(i));
            }
        }
    }

    private boolean isNumberChar(char c) {
        return c >= '0' && c <= '9';
    }
}
