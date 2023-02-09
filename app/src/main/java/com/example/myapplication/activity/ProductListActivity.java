package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BrandAdapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.core.Brand;
import com.example.myapplication.core.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        Button addProductButton = findViewById(R.id.addProductButton);
        EditText productNameEditText = findViewById(R.id.productNameEditText);

        loadProductList();
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addProductButton.setOnClickListener(view -> {
            String productName = productNameEditText.getText().toString().trim();
            Product product = new Product(productName);

            SharedPreferences sp = getSharedPreferences("grocerylab", MODE_PRIVATE);
            String jsonData = sp.getString(Product.PRODUCT_TAG, "");

            JSONArray jsonArray = new JSONArray();
            try {
                if (!jsonData.isEmpty()) {
                    jsonArray = new JSONArray(jsonData);
                }
                Gson gson = new Gson();
                jsonArray.put(new JSONObject(gson.toJson(product)));
                productList.add(0, product);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Product.PRODUCT_TAG, jsonArray.toString());
            editor.apply();

            adapter.notifyItemInserted(0);
            recyclerView.smoothScrollToPosition(0);
            Toast.makeText(this, "Product added.", Toast.LENGTH_SHORT).show();
        });
    }

    void loadProductList() {
        productList = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("grocerylab", MODE_PRIVATE);
        String jsonData = sp.getString(Product.PRODUCT_TAG, "");
        if (!jsonData.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {
            }.getType();
            productList = gson.fromJson(jsonData, type);
        }
    }
}