package com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;
import com.example.myapplication.core.Record;
import com.example.myapplication.core.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RecordViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_view);

        String jsonData = getIntent().getStringExtra("record");
        Gson gson = new Gson();
        Type type = new TypeToken<Record>() {
        }.getType();
        Record record = gson.fromJson(jsonData, type);
        Product product = Database.withContext(this).getProductByName(record.getProductName());

        TextView productTextView = findViewById(R.id.productTextView);
        productTextView.setText(record.getProductName());
        TextView brandTextView = findViewById(R.id.brandTextView);
        brandTextView.setText(record.getBrandName());
        TextView uomTextView = findViewById(R.id.uomTextView);
        uomTextView.setText(product.getUom());
        double measure = record.getMeasure();
        if (measure != 0) {
            TextView measureTextView = findViewById(R.id.measureTextView);
            measureTextView.setText(Utils.formatDecimal(measure));
        }
        TextView locationTextView = findViewById(R.id.locationTextView);
        locationTextView.setText(record.getLocation());
        TextView linkTextView = findViewById(R.id.linkTextView);
        linkTextView.setText(record.getLink());
        TextView isPurchaseTextView = findViewById(R.id.isPurchaseTextView);
        if (record.isPurchase())
            isPurchaseTextView.setText("Yes");
        else isPurchaseTextView.setText("No");
        TextView isPackagedTextView = findViewById(R.id.isPackagedTextView);
        if (record.isPackaged())
            isPackagedTextView.setText("Yes");
        else isPackagedTextView.setText("No");
        int packageQuantity = record.getPackageQuantity();
        if (packageQuantity != 0) {
            TextView packageQuantityTextView = findViewById(R.id.packageQuantityTextView);
            packageQuantityTextView.setText(Integer.toString(packageQuantity));
        }
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        quantityTextView.setText(Integer.toString(record.getQuantity()));
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText(Utils.formatDecimal(record.getPrice()));
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(record.getRating());
        TextView noteTextView = findViewById(R.id.noteTextView);
        noteTextView.setText(record.getNote());
        TextView createdAtTextView = findViewById(R.id.createdAtTextView);
        createdAtTextView.setText(Utils.formatDate(record.getCreationDate()));
        TextView perPriceTextView = findViewById(R.id.perPriceTextView);
        perPriceTextView.setText(Utils.formatDecimal(record.getPricePerUom()));

        MaterialButton backButton = findViewById(R.id.recordBackButton);
        backButton.setOnClickListener(view0 -> this.onBackPressed());
    }
}