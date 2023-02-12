package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.activity.BrandFormActivity;
import com.example.myapplication.adapter.BrandAdapter;
import com.example.myapplication.core.Brand;
import com.example.myapplication.core.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BrandListFragment extends Fragment {
    private BrandAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brand_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.brandRecyclerView);
        FloatingActionButton addBrandButton = view.findViewById(R.id.addBrandButton);

        List<Brand> brandList = Database.withContext(getContext()).getBrandList();
        adapter = new BrandAdapter(getContext(), brandList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    addBrandButton.hide();
                else
                    addBrandButton.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        addBrandButton.setOnClickListener(listener -> {
            Intent intent = new Intent(getContext(), BrandFormActivity.class);
            startActivity(intent);
        });
        return view;
    }
}