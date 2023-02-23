package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.NumericTextWatcher;
import com.example.myapplication.core.Product;
import com.example.myapplication.core.Record;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordQuantityFragment extends Fragment {

    private TextView perPriceTextView;
    private TextInputEditText measureEditText, packageQuantityEditText, priceEditText, quantityEditText;
    private Product product;
    private SwitchMaterial packageSwitch;

    private DecimalFormat decimalFormat;


    /**
     * pricePerUom = price / measure / quantity;
     * pricePerUom = (price / packageQuantity) / measure / quantity; if packaged
     */
    public void computePerPrice() {
        double totalPrice = 0, quantity = 0, packageQuantity = 0, measure = 0, perPrice = 0;
        if (measureEditText.getVisibility() == View.VISIBLE && measureEditText.getError() == null)
            measure = Double.parseDouble(measureEditText.getText().toString());
        if (quantityEditText.getError() == null)
            quantity = Double.parseDouble(quantityEditText.getText().toString());
        if (priceEditText.getError() == null)
            totalPrice = Double.parseDouble(priceEditText.getText().toString());
        if (packageQuantityEditText.getVisibility() == View.VISIBLE && packageQuantityEditText.getError() == null)
            packageQuantity = Double.parseDouble(packageQuantityEditText.getText().toString());

        if (totalPrice != 0 && quantity != 0)
            perPrice = totalPrice / quantity;
        if (measure != 0)
            perPrice /=  measure;
        if (packageQuantity != 0)
            perPrice /= packageQuantity;
        perPriceTextView.setText(decimalFormat.format(perPrice) + " per " + product.getUom());
    }

    private boolean isUnit() {
        return product.getUom().equals("Unit");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_quantity, container, false);

        Bundle bundle = getArguments();
        String productName;
        assert bundle != null;
        productName = bundle.getString("product");
        product = Database.withContext(getContext()).getProductByName(productName);
        decimalFormat = new DecimalFormat("0.##");

        perPriceTextView = view.findViewById(R.id.perPriceTextView);
        measureEditText = view.findViewById(R.id.measureEditText);
        measureEditText.addTextChangedListener(new NumericTextWatcher(measureEditText, this));
        measureEditText.postDelayed(() -> measureEditText.setText(""), 200);

        TextInputLayout measureInputLayout = view.findViewById(R.id.measureInputLayout);
        if (!isUnit())
            measureInputLayout.setSuffixText(product.getUom());
        else
            measureInputLayout.setVisibility(View.GONE);

        packageQuantityEditText = view.findViewById(R.id.packageQuantityEditText);
        packageQuantityEditText.addTextChangedListener(new NumericTextWatcher(packageQuantityEditText, this));
        packageQuantityEditText.postDelayed(() -> packageQuantityEditText.setText(""), 200);

        priceEditText = view.findViewById(R.id.priceEditText);
        priceEditText.addTextChangedListener(new NumericTextWatcher(priceEditText, this));
        priceEditText.postDelayed(() -> priceEditText.setText(""), 200);

        quantityEditText = view.findViewById(R.id.quantityEditText);
        quantityEditText.addTextChangedListener(new NumericTextWatcher(quantityEditText, this));
        quantityEditText.postDelayed(() -> quantityEditText.setText(""), 200);

        TextView switchTextView = view.findViewById(R.id.packageSwitchTextView);
        packageSwitch = view.findViewById(R.id.packageSwitch);
        TextInputLayout packageQuantityInputLayout = view.findViewById(R.id.packageQuantityInputLayout);
        packageQuantityInputLayout.setHint("Units Inside One Package");
        packageSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                switchTextView.setText("For examples, a box of washing powder, three individual bottles of detergent, etc.");
                packageSwitch.setText("Product is in Single Units");
                quantityEditText.setHint("Add Quantity");
                packageQuantityInputLayout.setVisibility(View.GONE);
            } else {
                switchTextView.setText("For examples, one five-pack bundle of noodles, two three-piece set of sponge, etc.");
                packageSwitch.setText("Product is in a Package");
                quantityEditText.setHint("Add Package Quantity");
                packageQuantityInputLayout.setVisibility(View.VISIBLE);
            }
        });
        packageSwitch.setChecked(true);
        packageSwitch.setChecked(false);

        MaterialButton backButton = view.findViewById(R.id.quantityBackButton);
        backButton.setOnClickListener(view0 -> getActivity().onBackPressed());

        MaterialButton nextButton = view.findViewById(R.id.quantityNextButton);
        nextButton.setOnClickListener(view0 -> {
            if (!product.getUom().equals("Unit")) {
                String measureString = measureEditText.getText().toString();
                double measure = measureString.isEmpty() ? 0.0 : Double.parseDouble(measureString);
                if (isZero("Measure", measure)) return;
            }

            if (packageSwitch.isChecked()) {
                String quantityString = packageQuantityEditText.getText().toString();
                int quantity = quantityString.isEmpty() ? 0 : Integer.parseInt(quantityString);
                if (isZero("Units inside one package", quantity)) return;
            }

            String quantityString = quantityEditText.getText().toString();
            int quantity = quantityString.isEmpty() ? 0 : Integer.parseInt(quantityString);
            String quantityLabel = packageSwitch.isChecked() ? "Package Quantity" : "Quantity";
            if (isZero(quantityLabel, quantity)) return;

            String priceString = priceEditText.getText().toString();
            double totalPrice = priceString.isEmpty() ? 0 : Double.parseDouble(priceString);
            if (isZero("Total Price", totalPrice)) return;

            String brandName = bundle.getString("brand");
            Record record = new Record(productName, brandName, quantity, totalPrice, 0);
            if (Database.withContext(getContext()).addRecord(record)) {
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }

    private boolean isZero(@NonNull String field, double value) {
        if (value == 0) {
            Toast.makeText(getContext(), field + " cannot be zero.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}