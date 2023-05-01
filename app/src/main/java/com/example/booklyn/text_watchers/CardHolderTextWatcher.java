package com.example.booklyn.text_watchers;

import android.text.Editable;
import android.text.TextWatcher;

public class CardHolderTextWatcher implements TextWatcher {

    StringBuilder sb = new StringBuilder();
    boolean ignore;

    private final char numPlace = 'X';

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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
            if (isNumberAlph(c)) {
                sb.append(c);
            }
        }
    }

    private void applyFormat(String text) {
        String template = "XXXXXXXXXXXXXXXXXXXXXXXXX";
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

    private boolean isNumberAlph(char c) {
        return c >= 'A' && c <= 'Z' || c == ' ';
    }
}
