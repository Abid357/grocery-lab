package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.core.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
        FloatingActionButton addProductButton = findViewById(R.id.addProductButton);

        loadProductList();
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    addProductButton.hide();
                else
                    addProductButton.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        addProductButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductFormActivity.class);
            startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Product.INSERT && resultCode == Activity.RESULT_OK) {
            adapter.notifyItemInserted(productList.size() - 1);
            Toast.makeText(this, "Product added.", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Product.EDIT && resultCode == Activity.RESULT_OK) {
//            int menuIndex = data.getIntExtra("menuIndex", -1);
//            boolean menuDeleted = data.getBooleanExtra("menuDeleted", false);
//            if (menuDeleted) {
//                adapter.notifyItemRemoved(menuIndex);
//                Toast.makeText(this, "Menu deleted", Toast.LENGTH_SHORT).show();
//            } else {
//                adapter.notifyItemChanged(menuIndex);
//                Toast.makeText(this, "Menu updated", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}