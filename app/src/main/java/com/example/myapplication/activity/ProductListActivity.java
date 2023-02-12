package com.example.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.core.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

        loadProducts();
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addProductButton.setOnClickListener(view -> {
            String productName = productNameEditText.getText().toString().trim();
            Product product = new Product(productName);

            SharedPreferences sp = getSharedPreferences("grocerylab", MODE_PRIVATE);
            productList.add(0, product);
            productNameEditText.setText("");
            adapter.notifyItemInserted(0);
            recyclerView.smoothScrollToPosition(0);

            try {
                JSONArray jsonArray = new JSONArray();
                Gson gson = new Gson();
                for (Product p : productList)
                    jsonArray.put(new JSONObject(gson.toJson(p)));
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Product.PRODUCT_TAG, jsonArray.toString());
                editor.apply();
                Toast.makeText(this, "Product added.", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    List<Product> loadProducts() {
        productList = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("grocerylab", MODE_PRIVATE);
        String jsonData = sp.getString(Product.PRODUCT_TAG, "");
        if (!jsonData.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {
            }.getType();
            productList = gson.fromJson(jsonData, type);
        }
        return productList;
    }
}