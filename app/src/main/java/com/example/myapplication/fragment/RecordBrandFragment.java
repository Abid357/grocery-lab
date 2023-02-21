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
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RecordBrandFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_brand, container, false);

        Bundle bundle = getArguments();
        assert bundle != null;
        String productName = bundle.getString("product");
        List<String> brandStringList = Database.withContext(getContext()).getBrandStringList(productName);
        AutoCompleteTextView recordBrandAutoComplete = view.findViewById(R.id.recordBrandAutoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.product_auto_complete_text_view, brandStringList);
        recordBrandAutoComplete.setAdapter(adapter);
        recordBrandAutoComplete.setOnItemClickListener((adapterView, view1, i, l) -> {
            bundle.putString("product", productName);
            bundle.putString("brand", recordBrandAutoComplete.getEditableText().toString());
            RecordPurchaseFragment fragment = new RecordPurchaseFragment();
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.mainFrameLayout, fragment)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        MaterialButton backButton = view.findViewById(R.id.brandBackButton);
        backButton.setOnClickListener(view0 -> getActivity().onBackPressed());
        return view;
    }
}