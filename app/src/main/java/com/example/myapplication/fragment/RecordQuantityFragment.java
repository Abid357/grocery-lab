package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

public class RecordQuantityFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_quantity, container, false);

        Bundle bundle = getArguments();
        String productName;
        assert bundle != null;
        productName = bundle.getString("product");
        Product product =  Database.withContext(getContext()).getProductByName(productName);

        TextInputEditText measureEditText = view.findViewById(R.id.measureEditText);
        TextInputLayout measureInputLayout = view.findViewById(R.id.measureInputLayout);
        if (!product.getUom().equals("Unit"))
            measureInputLayout.setSuffixText(product.getUom());
         else
            measureInputLayout.setVisibility(View.GONE);

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

//        List<String> productStringList = Database.withContext(getContext()).getProductStringList();
//        AutoCompleteTextView recordProductAutoComplete = view.findViewById(R.id.recordProductAutoCompleteTextView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.product_auto_complete_text_view, productStringList);
//        recordProductAutoComplete.setAdapter(adapter);
//        recordProductAutoComplete.setOnItemClickListener((adapterView, view1, i, l) -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("product", recordProductAutoComplete.getEditableText().toString());
//            RecordBrandFragment fragment = new RecordBrandFragment();
//            fragment.setArguments(bundle);
//
//            getParentFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
//                    .replace(R.id.mainFrameLayout, fragment)
//                    .setReorderingAllowed(true)
//                    .addToBackStack(null)
//                    .commit();
//        });

        MaterialButton backButton = view.findViewById(R.id.quantityBackButton);
        backButton.setOnClickListener(view0 -> getActivity().onBackPressed());

        MaterialButton nextButton = view.findViewById(R.id.quantityNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!product.getUom().equals("Unit")){
                    String measureString = measureEditText.getText().toString();
                    double measure = measureString.isEmpty() ? 0.0 : Double.parseDouble(measureString);
                    if (isZeroOrNegativeValue("Measure", measure)) return;
                }
                String quantityString = quantityEditText.getText().toString();
                int quantity = quantityString.isEmpty() ? 0 : Integer.parseInt(quantityString);
                String quantityLabel = packageSwitch.isSelected() ? "Package Quantity" : "Quantity";
                if (isZeroOrNegativeValue(quantityLabel, quantity)) return;

                String priceString = priceEditText.getText().toString();
                double totalPrice = priceString.isEmpty() ? 0 : Double.parseDouble(priceString);
                if (isZeroOrNegativeValue("Total Price", totalPrice)) return;
            }
        });
        return view;
    }

    private boolean isZeroOrNegativeValue(@NonNull String field, double value){
        if (value == 0) {
            Toast.makeText(getContext(), field + " cannot be zero.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (value < 0) {
            Toast.makeText(getContext(), field + " cannot be negative.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}