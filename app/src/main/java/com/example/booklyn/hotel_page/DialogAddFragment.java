package com.example.booklyn.hotel_page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booklyn.R;

public class DialogAddFragment extends DialogFragment {

    View mainView;

    public interface RateSetter {
        void setRate(float rate, String info);
    }

    RateSetter rateSetter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        rateSetter = (RateSetter) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = view;
        Button buttonApply = view.findViewById(R.id.dialog_button_apply);
        Button buttonCancel = view.findViewById(R.id.dialog_button_cancel);
        buttonApply.setOnClickListener(this::onClickApply);
        buttonCancel.setOnClickListener(this::onClickCancel);
    }

    public void onClickApply(View view) {
        //Поля для ввода комментария
        EditText editTextRate = mainView.findViewById(R.id.dialog_editText_rate);
        EditText editTextMsg = mainView.findViewById(R.id.dialog_editText_msg);
        String str = editTextMsg.getText().toString();
        float flt = Float.parseFloat(editTextRate.getText().toString());
        rateSetter.setRate(flt, str);

        Toast.makeText(getActivity(), "Комментарий добавлен", Toast.LENGTH_LONG).show();
        this.dismiss();
    }

    public void onClickCancel(View view) {
        this.dismiss();
    }

    @Override
    public void onDetach() {
        rateSetter = null;
        super.onDetach();
    }
}