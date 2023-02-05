package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.core.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductFormActivity extends AppCompatActivity {
    private TextInputEditText productTypeText, brandNameText;
    private FloatingActionButton productImageButton, saveButton;
    private ImageView productImageView;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        productTypeText = findViewById(R.id.productTypeEditText);
        brandNameText = findViewById(R.id.brandNameEditText);
        productImageButton = findViewById(R.id.addProductImageButton);
        productImageView = findViewById(R.id.productImageView);
        saveButton = findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(view -> {
            String productType = productTypeText.getText().toString();
            String brandName = brandNameText.getText().toString();
            Product product = new Product(brandName, productType);

            sp = getSharedPreferences("grocerylab", MODE_PRIVATE);
            String jsonData = sp.getString(Product.PRODUCT_TAG, "");

            JSONArray jsonArray = new JSONArray();
            try {
                if (!jsonData.isEmpty()) {
                    jsonArray = new JSONArray(jsonData);
                }
                Gson gson = new Gson();
                jsonArray.put(new JSONObject(gson.toJson(product)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Product.PRODUCT_TAG, jsonArray.toString());
            editor.apply();

            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
            finishActivity(Product.INSERT);
            finish();
        });
    }
}