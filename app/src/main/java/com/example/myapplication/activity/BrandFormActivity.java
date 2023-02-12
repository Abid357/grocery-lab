package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.core.Brand;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BrandFormActivity extends AppCompatActivity {
    private TextInputEditText brandNameText;
    private AutoCompleteTextView brandProductAutoComplete;
    private FloatingActionButton productImageButton, saveButton;
    private ImageView productImageView;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_form);

        List<String> productStringList = Database.withContext(this).getProductStringList();
        brandProductAutoComplete = findViewById(R.id.brandProductAutoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.brand_product_auto_complete_text_view, productStringList);
        brandProductAutoComplete.setAdapter(adapter);

        brandNameText = findViewById(R.id.brandNameEditText);
        productImageButton = findViewById(R.id.addProductImageButton);
        productImageView = findViewById(R.id.productImageView);
        saveButton = findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(view -> {
            String brandProduct = brandProductAutoComplete.getEditableText().toString();
            String brandName = brandNameText.getText().toString().trim();
            Brand brand = new Brand(brandName, brandProduct);
            Database.withContext(this).addBrand(brand);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragment", R.id.brandsMenu);
            startActivity(intent);
            finishActivity(Database.INSERT);
            finish();
        });
    }
}