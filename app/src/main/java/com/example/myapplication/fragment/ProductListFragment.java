package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addProductButton.setOnClickListener(listener -> {
            String productName = productNameEditText.getText().toString().trim();
            Product product = new Product(productName);
            if (Database.withContext(getContext()).addProduct(product)) {
                adapter.notifyItemInserted(0);
                recyclerView.smoothScrollToPosition(0);
            }
            productNameEditText.setText("");
        });
        return view;
    }
}