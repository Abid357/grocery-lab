package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ProductListFragment extends Fragment {
    AutoCompleteTextView uomAutoComplete;
    ArrayAdapter<String> uomAdapter;

    private boolean validateUom() {
        String uom = uomAutoComplete.getText().toString();
        for (int i = 0; i < uomAdapter.getCount(); i++) {
            if (uomAdapter.getItem(i).equals(uom))
                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.productRecyclerView);
        MaterialButton addProductButton = view.findViewById(R.id.addProductButton);
        EditText productNameEditText = view.findViewById(R.id.productInput);

        List<Product> productList = Database.withContext(getContext()).getProductList();
        ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> uomList = Database.withContext(getContext()).getUomList();
        uomAutoComplete = view.findViewById(R.id.uomAutoCompleteTextView);
        uomAdapter = new ArrayAdapter<>(getContext(), R.layout.product_auto_complete_text_view, uomList);
        uomAutoComplete.setAdapter(uomAdapter);
        uomAutoComplete.setOnFocusChangeListener((view0, b) -> {
            if (!b && validateUom()) return;
            uomAutoComplete.setText("");
        });

        addProductButton.setOnClickListener(listener -> {
            String productName = productNameEditText.getText().toString().trim();
            if (productName.isEmpty()) {
                Toast.makeText(getContext(), "Enter a product name.", Toast.LENGTH_SHORT).show();
                return;
            }
            String uom = uomAutoComplete.getEditableText().toString();
            if (uom.isEmpty()) {
                Toast.makeText(getContext(), "Select a unit of measure.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validateUom()) {
                Toast.makeText(getContext(), "You cannot add a new unit of measure.", Toast.LENGTH_SHORT).show();
                return;
            }
            Product product = new Product(productName, uom);
            if (Database.withContext(getContext()).addProduct(product)) {
                productAdapter.notifyItemInserted(0);
                recyclerView.smoothScrollToPosition(0);
            }
            productNameEditText.setText("");
        });
        return view;
    }
}