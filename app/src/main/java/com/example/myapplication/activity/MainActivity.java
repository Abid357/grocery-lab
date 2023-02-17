package com.example.myapplication.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.fragment.BrandListFragment;
import com.example.myapplication.fragment.ProductListFragment;
import com.example.myapplication.fragment.RecordListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mainNavigation = findViewById(R.id.mainBottomNavigationView);
        mainNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.productsMenu:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new ProductListFragment()).commit();
                    return true;
                case R.id.brandsMenu:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new BrandListFragment()).commit();
                    return true;
                case R.id.recordsMenu:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new RecordListFragment()).commit();
                    return true;
            }
            return false;
        });
        mainNavigation.setSelectedItemId(R.id.homeMenu);
    }
}