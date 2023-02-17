package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.fragment.BrandListFragment;
import com.example.myapplication.fragment.ProductListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
            }
            return false;
        });
        int defaultFragmentId = getIntent().getIntExtra("fragment", R.id.homeMenu);
        mainNavigation.setSelectedItemId(defaultFragmentId);
    }
}