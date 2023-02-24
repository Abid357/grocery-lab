package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RecordProductFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_product, container, false);

        AutoCompleteTextView recordProductAutoComplete = view.findViewById(R.id.recordProductAutoCompleteTextView);
        List<String> productStringList = Database.withContext(getContext()).getProductStringList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.product_auto_complete_text_view, productStringList);
        recordProductAutoComplete.setAdapter(adapter);
        recordProductAutoComplete.setOnItemClickListener((adapterView, view0, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putString("product", recordProductAutoComplete.getEditableText().toString());
            RecordBrandFragment fragment = new RecordBrandFragment();
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.mainFrameLayout, fragment)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
        recordProductAutoComplete.postDelayed(() -> {
            recordProductAutoComplete.showDropDown();
            recordProductAutoComplete.setText("");
            recordProductAutoComplete.setSelection(recordProductAutoComplete.getText().length());
        },200);

        MaterialButton backButton = view.findViewById(R.id.productBackButton);
        backButton.setOnClickListener(view0 -> getActivity().onBackPressed());
        return view;
    }
}