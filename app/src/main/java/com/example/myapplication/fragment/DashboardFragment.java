package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.RecordViewActivity;
import com.example.myapplication.core.Brand;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;
import com.example.myapplication.core.Record;
import com.example.myapplication.core.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;

public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView productsStat = view.findViewById(R.id.productsStatTextView);
        TextView brandsStat = view.findViewById(R.id.brandsStatTextView);
        TextView recordsStat = view.findViewById(R.id.recordsStatTextView);

        List<Product> productList = Database.withContext(getContext()).getProductList();
        productsStat.setText(Integer.toString(productList.size()));
        List<Brand> brandsList = Database.withContext(getContext()).getBrandList();
        brandsStat.setText(Integer.toString(brandsList.size()));
        List<Record> recordsList = Database.withContext(getContext()).getRecordList();
        recordsStat.setText(Integer.toString(recordsList.size()));

        // Last Purchase
        Record lastPurchaseRecord = Database.withContext(getContext()).getRecordList().stream()
                .filter(Record::isPurchase)
                .min(Comparator.comparing(Record::getPurchaseDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
        if (lastPurchaseRecord != null) {
            View lastPurchaseView = inflater.inflate(R.layout.fragment_record_list_item, null);
            populateCardView(lastPurchaseView, lastPurchaseRecord);
            CardView lastPurchaseCardView = view.findViewById(R.id.lastPurchaseCardView);
            lastPurchaseCardView.addView(lastPurchaseView);
            lastPurchaseCardView.setOnClickListener(new OnCardViewClickListener(lastPurchaseRecord, getContext()));
        }

        // Best Purchase
        Record bestPurchaseRecord = Database.withContext(getContext()).getRecordList().stream()
                .filter(Record::isPurchase)
                .min(Comparator.comparing(Record::getPricePerUom, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
        if (bestPurchaseRecord != null) {
            View bestPurchaseView = inflater.inflate(R.layout.fragment_record_list_item, null);
            populateCardView(bestPurchaseView, bestPurchaseRecord);
            CardView bestPurchaseCardView = view.findViewById(R.id.bestPurchaseCardView);
            bestPurchaseCardView.addView(bestPurchaseView);
            bestPurchaseCardView.setOnClickListener(new OnCardViewClickListener(bestPurchaseRecord, getContext()));
        }

        return view;
    }

    private void populateCardView(View view, Record record) {
        TextView brandName = view.findViewById(R.id.recordBrandNameTextView);
        brandName.setText(record.getBrandName());
        TextView productName = view.findViewById(R.id.recordProductNameTextView);
        productName.setText(record.getProductName());
        ImageView locationIcon = view.findViewById(R.id.locationIconImageView);
        if (TextUtils.isEmpty(record.getLocation()))
            locationIcon.setVisibility(View.GONE);
        else locationIcon.setVisibility(View.VISIBLE);
        ImageView linkIcon = view.findViewById(R.id.linkIconImageView);
        if (TextUtils.isEmpty(record.getLink()))
            linkIcon.setVisibility(View.GONE);
        else linkIcon.setVisibility(View.VISIBLE);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setRating((float) record.getRating());
        TextView perPrice = view.findViewById(R.id.perPriceTextView);
        Product product = Database.withContext(getContext()).getProductByName(record.getProductName());
        perPrice.setText(Utils.formatDecimal(record.getPricePerUom()) + " per " + product.getUom());
    }

    public static class OnCardViewClickListener implements View.OnClickListener {
        private Record record;
        private Context context;

        public OnCardViewClickListener(Record record, Context context) {
            this.record = record;
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            Gson gson = new Gson();
            try {
                JSONObject jsonObject = new JSONObject(gson.toJson(record));
                Intent intent = new Intent(context, RecordViewActivity.class);
                intent.putExtra("record", jsonObject.toString());
                context.startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Record could not be opened", Toast.LENGTH_SHORT).show();
            }
        }
    }
}