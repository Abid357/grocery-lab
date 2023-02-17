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
import com.example.myapplication.activity.RecordActivity;
import com.example.myapplication.adapter.RecordAdapter;
import com.example.myapplication.core.Record;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecordListFragment extends Fragment {
    private RecordAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recordRecyclerView);
        FloatingActionButton addRecordButton = view.findViewById(R.id.addRecordButton);

        List<Record> recordList = new ArrayList<>(); //Database.withContext(getContext()).getRecordList();
        adapter = new RecordAdapter(getContext(), recordList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    addRecordButton.hide();
                else
                    addRecordButton.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        addRecordButton.setOnClickListener(listener -> {
            Intent intent = new Intent(getContext(), RecordActivity.class);
            startActivity(intent);
        });
        return view;
    }
}