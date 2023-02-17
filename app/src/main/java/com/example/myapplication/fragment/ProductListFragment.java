package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;

import java.util.List;

public class ProductListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.productRecyclerView);
        Button addProductButton = view.findViewById(R.id.addProductButton);
        EditText productNameEditText = view.findViewById(R.id.productNameEditText);

        List<Product> productList = Database.withContext(getContext()).getProductList();
        ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> uomList = Database.withContext(getContext()).getUomList();
        AutoCompleteTextView uomAutoComplete = view.findViewById(R.id.uomAutoCompleteTextView);
        ArrayAdapter<String> uomAdapter = new ArrayAdapter<>(getContext(), R.layout.brand_product_auto_complete_text_view, uomList);
        uomAutoComplete.setAdapter(uomAdapter);

        addProductButton.setOnClickListener(listener -> {
            String productName = productNameEditText.getText().toString().trim();
            if (productName.isEmpty()){
                Toast.makeText(getContext(), "Enter a product name.", Toast.LENGTH_SHORT).show();
                return;
            }
            Product product = new Product(productName);
            if (Database.withContext(getContext()).addProduct(product)) {
                productAdapter.notifyItemInserted(0);
                recyclerView.smoothScrollToPosition(0);
            }
            productNameEditText.setText("");
        });
        return view;
    }
}