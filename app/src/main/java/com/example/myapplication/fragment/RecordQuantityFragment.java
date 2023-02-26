package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.NumericTextWatcher;
import com.example.myapplication.core.Product;
import com.example.myapplication.core.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RecordQuantityFragment extends Fragment {

    private TextView perPriceTextView;
    private TextInputEditText measureEditText, packageQuantityEditText, priceEditText, quantityEditText;
    private Product product;
    private SwitchMaterial packageSwitch;
    private TextInputLayout packageQuantityInputLayout, measureInputLayout;


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
        if (packageQuantityInputLayout.getVisibility() == View.VISIBLE && packageQuantityEditText.getError() == null)
            packageQuantity = Double.parseDouble(packageQuantityEditText.getText().toString());

        if (totalPrice != 0 && quantity != 0)
            perPrice = totalPrice / quantity;
        if (measure != 0)
            perPrice /= measure;
        if (packageQuantity != 0)
            perPrice /= packageQuantity;
        perPriceTextView.setText(Utils.formatDecimal(perPrice) + " per " + product.getUom());
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

        perPriceTextView = view.findViewById(R.id.perPriceTextView);
        measureEditText = view.findViewById(R.id.measureEditText);
        measureEditText.addTextChangedListener(new NumericTextWatcher(measureEditText, this));
        measureEditText.postDelayed(() -> measureEditText.setText(""), 200);

        measureInputLayout = view.findViewById(R.id.measureInputLayout);
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
        packageQuantityInputLayout = view.findViewById(R.id.packageQuantityInputLayout);
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
                quantityEditText.setHint("Add Number of Packages");
                packageQuantityInputLayout.setVisibility(View.VISIBLE);
            }
            computePerPrice();
        });
        packageSwitch.postDelayed(() -> {
            packageSwitch.setChecked(true);
            packageSwitch.setChecked(false);
        }, 400);

        MaterialButton backButton = view.findViewById(R.id.quantityBackButton);
        backButton.setOnClickListener(view0 -> getActivity().onBackPressed());

        MaterialButton nextButton = view.findViewById(R.id.quantityNextButton);
        nextButton.setOnClickListener(view0 -> {
            double totalPrice, measure = 0, perPrice;
            int quantity, packageQuantity = 0;
            if (measureInputLayout.getVisibility() == View.VISIBLE)
                if (measureEditText.getError() != null) {
                    Toast.makeText(getContext(), "Errors in Measure field", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    measure = Double.parseDouble(measureEditText.getText().toString());
            if (packageQuantityInputLayout.getVisibility() == View.VISIBLE)
                if (packageQuantityEditText.getError() != null) {
                    Toast.makeText(getContext(), "Errors in Package field", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    packageQuantity = Integer.parseInt(packageQuantityEditText.getText().toString());
            if (quantityEditText.getError() != null) {
                Toast.makeText(getContext(), "Errors in Quantity field", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity = Integer.parseInt(quantityEditText.getText().toString());
            if (priceEditText.getError() != null) {
                Toast.makeText(getContext(), "Errors in Price field", Toast.LENGTH_SHORT).show();
                return;
            }
            totalPrice = Double.parseDouble(priceEditText.getText().toString());
            String perPriceString = perPriceTextView.getText().toString();
            perPriceString = perPriceString.substring(0, perPriceString.indexOf(" "));
            perPrice = Double.parseDouble(perPriceString);

            bundle.putDouble("measure", measure);
            bundle.putInt("packageQuantity", packageQuantity);
            bundle.putInt("quantity", quantity);
            bundle.putDouble("price", totalPrice);
            bundle.putDouble("perPrice", perPrice);

            RecordFeedbackFragment fragment = new RecordFeedbackFragment();
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
}