package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.BrandFormActivity;
import com.example.myapplication.adapter.BrandAdapter;
import com.example.myapplication.core.Brand;
import com.example.myapplication.core.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BrandListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.brandRecyclerView);
        FloatingActionButton addBrandButton = view.findViewById(R.id.addBrandButton);

        List<Brand> brandList = Database.withContext(getContext()).getBrandList();
        BrandAdapter adapter = new BrandAdapter(getContext(), brandList);
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

        adapter.setUpItemTouchHelper(recyclerView);

        ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Database.BRAND_INSERTED) {
                        adapter.notifyItemInserted(0);
                    }
                });

        addBrandButton.setOnClickListener(listener -> {
            Intent intent = new Intent(getContext(), BrandFormActivity.class);
            activityResultLaunch.launch(intent);
        });
        return view;
    }
}