package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;

import java.util.List;

public class RecordProductFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_product, container, false);


        List<String> productStringList = Database.withContext(getContext()).getProductStringList();
        AutoCompleteTextView recordProductAutoComplete = view.findViewById(R.id.recordProductAutoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.product_auto_complete_text_view, productStringList);
        recordProductAutoComplete.setAdapter(adapter);

        recordProductAutoComplete.setOnItemClickListener((adapterView, view1, i, l) -> getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.mainFrameLayout, new RecordBrandFragment())
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit());
        return view;
    }
}