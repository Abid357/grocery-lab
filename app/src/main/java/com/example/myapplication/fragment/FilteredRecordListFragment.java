package com.example.myapplication.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.RecordViewActivity;
import com.example.myapplication.adapter.RecordAdapter;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Record;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilteredRecordListFragment extends Fragment implements RecordAdapter.OnRecordClickListener {

    private final static String SORT_BY_UNIT_PRICE = "SORT_BY_UNIT_PRICE";
    private final static String SORT_BY_RATING = "SORT_BY_RATING";

    private List<Record> recordList;
    private RecordAdapter adapter;

    private String sortBy;
    private String filterByProduct;
    private String filterByBrand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtered_record_list, container, false);

        SwitchMaterial sortBySwitch = view.findViewById(R.id.sortBySwitch);
        TextView unitPriceTextView = view.findViewById(R.id.unitPriceTextView);
        TextView ratingTextView = view.findViewById(R.id.ratingTextView);

        sortBySwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                unitPriceTextView.setTypeface(null, Typeface.BOLD_ITALIC);
                ratingTextView.setTypeface(null, Typeface.NORMAL);
                sortBy = SORT_BY_UNIT_PRICE;
            } else {
                unitPriceTextView.setTypeface(null, Typeface.NORMAL);
                ratingTextView.setTypeface(null, Typeface.BOLD_ITALIC);
                sortBy = SORT_BY_RATING;
            }
            updateView(false);
        });

        List<String> brandStringList = Database.withContext(getContext()).getBrandStringList(null);
        AutoCompleteTextView brandAutoComplete = view.findViewById(R.id.brandAutoComplete);
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(getContext(), R.layout.product_auto_complete_text_view, brandStringList);

        List<String> productStringList = Database.withContext(getContext()).getProductStringList();
        AutoCompleteTextView productAutoComplete = view.findViewById(R.id.productAutoComplete);
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(getContext(), R.layout.product_auto_complete_text_view, productStringList);
        productAutoComplete.setAdapter(productAdapter);
        productAutoComplete.setOnFocusChangeListener((view1, b) -> {
            if (!b) {
                String productName = productAutoComplete.getText().toString();
                for (int i = 0; i < productAdapter.getCount(); i++) {
                    if (productAdapter.getItem(i).equals(productName)) {
                        filterByProduct = productName;
                        brandStringList.addAll(Database.withContext(getContext()).getBrandStringList(productName));
                        brandAdapter.notifyDataSetChanged();
                        updateView(true);
                        return;
                    }
                }
                productAutoComplete.setText("");
                brandStringList.clear();
                filterByProduct = "";
                updateView(true);
            }
        });
        productAutoComplete.setOnItemClickListener((adapterView, view12, i, l) -> {
            String productName = productAutoComplete.getText().toString();
            filterByProduct = productName;
            updateView(true);
        });


        brandAutoComplete.setAdapter(brandAdapter);
        brandAutoComplete.setOnFocusChangeListener((view1, b) -> {
            if (!b) {
                String brandName = brandAutoComplete.getText().toString();
                for (int i = 0; i < brandAdapter.getCount(); i++) {
                    if (brandAdapter.getItem(i).equals(brandName)) {
                        filterByBrand = brandName;
                        updateView(true);
                        return;
                    }
                }
                brandAutoComplete.setText("");
                filterByBrand = "";
                updateView(true);
            }
        });
        brandAutoComplete.setOnItemClickListener((adapterView, view12, i, l) -> {
            String brandName = brandAutoComplete.getText().toString();
            filterByBrand = brandName;
            updateView(true);
        });

        CardView filtersCardView = view.findViewById(R.id.filtersCardView);
        RecyclerView recyclerView = view.findViewById(R.id.filteredRecordsRecyclerView);
        recordList = new ArrayList<>();
        recordList.addAll(Database.withContext(getContext()).getRecordList());
        adapter = new RecordAdapter(getContext(), recordList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    filtersCardView.setVisibility(View.INVISIBLE);
                else
                    filtersCardView.setVisibility(View.VISIBLE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        sortBySwitch.setChecked(true);
        return view;
    }

    private void updateView(boolean doFilter) {
        // filter
        if (doFilter) {
            recordList.clear();
            recordList.addAll(Database.withContext(getContext()).getRecordList());
            if (filterByProduct != null && !filterByProduct.isEmpty())
                recordList.removeIf(record -> !record.getProductName().equals(filterByProduct));
            if (filterByBrand != null && !filterByBrand.isEmpty())
                recordList.removeIf(record -> !record.getBrandName().equals(filterByBrand));
        }

        if (recordList.isEmpty())
            return;

        // sort
        switch (sortBy) {
            case SORT_BY_UNIT_PRICE:
                recordList.sort((o1, o2) -> {
                    int result = Double.compare(o1.getPricePerUom(), o2.getPricePerUom());
                    if (result != 0) {
                        return result;
                    }
                    return Double.compare(o2.getRating(), o1.getRating());
                });
                break;
            case SORT_BY_RATING:
                recordList.sort((o1, o2) -> {
                    int result = Double.compare(o2.getRating(), o1.getRating());
                    if (result != 0) {
                        return result;
                    }
                    return Double.compare(o1.getPricePerUom(), o2.getPricePerUom());
                });
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRecordClick(int position) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(recordList.get(position)));
            Intent intent = new Intent(getContext(), RecordViewActivity.class);
            intent.putExtra("record", jsonObject.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Record could not be opened", Toast.LENGTH_SHORT).show();
        }
    }
}