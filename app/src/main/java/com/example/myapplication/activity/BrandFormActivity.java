package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.core.Brand;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BrandFormActivity extends AppCompatActivity {
    private TextInputEditText brandProductText, brandNameText;
    private FloatingActionButton productImageButton, saveButton;
    private ImageView productImageView;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_form);

        brandProductText = findViewById(R.id.brandProductEditText);
        brandNameText = findViewById(R.id.brandNameEditText);
        productImageButton = findViewById(R.id.addProductImageButton);
        productImageView = findViewById(R.id.productImageView);
        saveButton = findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(view -> {
            String brandProduct = brandProductText.getText().toString().trim();
            String brandName = brandNameText.getText().toString().trim();
            Brand brand = new Brand(brandName, brandProduct);

            sp = getSharedPreferences("grocerylab", MODE_PRIVATE);
            String jsonData = sp.getString(Brand.BRAND_TAG, "");

            JSONArray jsonArray = new JSONArray();
            try {
                if (!jsonData.isEmpty()) {
                    jsonArray = new JSONArray(jsonData);
                }
                Gson gson = new Gson();
                jsonArray.put(new JSONObject(gson.toJson(brand)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Brand.BRAND_TAG, jsonArray.toString());
            editor.apply();

            Intent intent = new Intent(this, BrandListActivity.class);
            startActivity(intent);
            finishActivity(Brand.INSERT);
            finish();
        });
    }
}