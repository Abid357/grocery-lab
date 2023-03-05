package com.example.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.core.Brand;
import com.example.myapplication.core.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

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
        brandNameText = findViewById(R.id.brandNameEditText);
        brandProductAutoComplete = findViewById(R.id.brandProductAutoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.product_auto_complete_text_view, productStringList);
        brandProductAutoComplete.setAdapter(adapter);
        brandProductAutoComplete.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String productName = brandProductAutoComplete.getText().toString();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (adapter.getItem(i).equals(productName))
                        return;
                }
                brandProductAutoComplete.setText("");
            }
        });

        brandProductAutoComplete.setOnItemClickListener((adapterView, view0, i, l) -> {
            brandNameText.requestFocusFromTouch();
        });

        productImageButton = findViewById(R.id.addProductImageButton);
        productImageView = findViewById(R.id.productImageView);
        saveButton = findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(view -> {
            String productName = brandProductAutoComplete.getEditableText().toString();
            if (productName.isEmpty()) {
                Toast.makeText(this, "Select a product.", Toast.LENGTH_SHORT).show();
                return;
            }
            String brandName = brandNameText.getText().toString().trim();
            if (brandName.isEmpty()) {
                Toast.makeText(this, "Select a brand.", Toast.LENGTH_SHORT).show();
                return;
            }
            Brand brand = new Brand(brandName, productName);
            if (Database.withContext(this).addBrand(brand)) {
                setResult(Database.BRAND_INSERTED);
                finish();
            }
        });
    }
}