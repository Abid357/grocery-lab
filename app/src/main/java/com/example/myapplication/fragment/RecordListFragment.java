package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class RecordListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recordRecyclerView);
        FloatingActionButton addBrandButton = view.findViewById(R.id.addRecordButton);

//        List<Brand> brandList = Database.withContext(getContext()).getBrandList();
//        BrandAdapter adapter = new BrandAdapter(getContext(), brandList);
//        recyclerView.setAdapter(adapter);
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

        addBrandButton.setOnClickListener(listener -> getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.mainFrameLayout, new RecordProductFragment())
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit());
        return view;
    }
}