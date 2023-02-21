package com.example.myapplication.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecordPurchaseFragment extends Fragment {

    TextInputEditText dateEditText;
    SimpleDateFormat dateFormatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_purchase, container, false);

        Bundle bundle = getArguments();
        assert bundle != null;
        String dateFormat = "dd-MM-YYYY";
        dateFormatter = new SimpleDateFormat(dateFormat);
        dateEditText = view.findViewById(R.id.dateEditText);
        dateEditText.setOnClickListener(view1 -> showDatePicker());
        TextInputEditText locationEditText = view.findViewById(R.id.locationTextInput);
        SwitchMaterial purchaseSwitch = view.findViewById(R.id.purchaseSwitch);
        TextView switchTextView = view.findViewById(R.id.purchaseSwitchTextView);
        purchaseSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                switchTextView.setText("This record is simply to keep note of a product's details for future reference and price comparisons.");
                purchaseSwitch.setText("This is Not a Purchase");
            } else {
                switchTextView.setText("This record is a purchase and the product's last purchase details will be updated.");
                purchaseSwitch.setText("This is a Purchase");
            }
        });
        purchaseSwitch.setChecked(true);

        MaterialButton backButton = view.findViewById(R.id.purchaseBackButton);
        backButton.setOnClickListener(view0 -> getActivity().onBackPressed());

        MaterialButton nextButton = view.findViewById(R.id.purchaseNextButton);
        nextButton.setOnClickListener(view12 -> {
            bundle.putBoolean("isPurchase", purchaseSwitch.isSelected());
            bundle.putString("date", dateEditText.getText().toString());
            bundle.putString("location", locationEditText.getText().toString());
            RecordQuantityFragment fragment = new RecordQuantityFragment();
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.mainFrameLayout, fragment)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), (datePicker, year, month, day) -> {
            String dateString = dateFormatter.format(calendar.getTime());
            dateEditText.setText(dateString);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}