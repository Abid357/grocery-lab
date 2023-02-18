package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;

import java.util.List;

public class RecordBrandFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_brand, container, false);

        //TODO: get brands specific to product not all
        List<String> brandStringList = Database.withContext(getContext()).getBrandStringList();
        AutoCompleteTextView recordBrandAutoComplete = view.findViewById(R.id.recordBrandAutoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.product_auto_complete_text_view, brandStringList);
        recordBrandAutoComplete.setAdapter(adapter);
        return view;
    }
}