package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.core.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        FloatingActionButton addProductButton = findViewById(R.id.addProductButton);

        List<Product> productList = getProductList();
        ProductAdapter adapter = new ProductAdapter(this, productList);
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

    List<Product> getProductList(){
        List<Product> list = new ArrayList<>();
        Product test = new Product();
        test.setLastPurchaseLocation("Lulu");
        test.setLastPurchasePrice(123.123);
        list.add(test);
        Product test2 = new Product();
        test2.setLastPurchaseLocation("Safeer Market");
        test2.setLastPurchasePrice(151.13);
        list.add(test2);
        Product test3 = new Product();
        test3.setLastPurchaseLocation("Safeer Market");
        test3.setLastPurchasePrice(151.13);
        list.add(test3);
        Product test4 = new Product();
        test4.setLastPurchaseLocation("Safeer Market");
        test4.setLastPurchasePrice(151.13);
        list.add(test4);
        return list;
    }
}