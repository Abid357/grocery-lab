package com.example.myapplication.core;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.example.myapplication.fragment.RecordQuantityFragment;
import com.google.android.material.textfield.TextInputEditText;

public class NumericTextWatcher implements TextWatcher {

    private final TextInputEditText editText;

    private final RecordQuantityFragment fragment;

    public NumericTextWatcher(TextInputEditText editText, RecordQuantityFragment fragment){
        this.editText = editText;
        this.fragment = fragment;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (TextUtils.isEmpty(editable)) {
            editText.setError("Mandatory field");
            return;
        }
        if (Double.parseDouble(editable.toString()) == 0) {
            editText.setError("Cannot be zero");
            return;
        }
        editText.setError(null);
        fragment.computePerPrice();
    }
}
