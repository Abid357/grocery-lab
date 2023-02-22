package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;
import com.example.myapplication.core.Record;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RecordQuantityFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_quantity, container, false);

        Bundle bundle = getArguments();
        String productName;
        assert bundle != null;
        productName = bundle.getString("product");
        Product product = Database.withContext(getContext()).getProductByName(productName);

        TextInputEditText measureEditText = view.findViewById(R.id.measureEditText);
        TextInputLayout measureInputLayout = view.findViewById(R.id.measureInputLayout);
        if (!product.getUom().equals("Unit"))
            measureInputLayout.setSuffixText(product.getUom());
        else
            measureInputLayout.setVisibility(View.GONE);

        TextInputEditText packageQuantityEditText = view.findViewById(R.id.packageQuantityEditText);
        TextInputEditText priceEditText = view.findViewById(R.id.priceEditText);
        TextInputEditText quantityEditText = view.findViewById(R.id.quantityEditText);
        TextView switchTextView = view.findViewById(R.id.packageSwitchTextView);
        SwitchMaterial packageSwitch = view.findViewById(R.id.packageSwitch);
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
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                String productName = bundle.getString("product");
                String brandName = bundle.getString("brand");
                Record record = new Record(productName, brandName, quantity, totalPrice, 0);
                if (Database.withContext(getContext()).addRecord(record)) {
                    getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
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