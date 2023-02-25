package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Record;
import com.example.myapplication.core.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;

public class RecordFeedbackFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_feedback, container, false);
        Bundle bundle = getArguments();

        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        MaterialButton backButton = view.findViewById(R.id.feedbackBackButton);
        backButton.setOnClickListener(view0 -> getActivity().onBackPressed());
        MaterialButton nextButton = view.findViewById(R.id.feedbackNextButton);
        TextInputEditText noteEditText = view.findViewById(R.id.noteEditText);
        nextButton.setOnClickListener(view0 -> {
            String productName = bundle.getString("product");
            String brandName = bundle.getString("brand");
            double measure = bundle.getDouble("measure");
            int packageQuantity = bundle.getInt("packageQuantity");
            int quantity = bundle.getInt("quantity");
            double totalPrice = bundle.getDouble("price");
            double perPrice = bundle.getDouble("perPrice");
            String location = bundle.getString("location");
            String link = bundle.getString("link");
            boolean isPurchase = bundle.getBoolean("isPurchase");
            Date purchaseDate = Utils.parseDate(bundle.getString("purchaseDate"));
            float rating = ratingBar.getRating();
            String note = noteEditText.getText().toString();

            Record record = new Record(productName, brandName, measure, packageQuantity, quantity, totalPrice, perPrice, isPurchase, purchaseDate, location, link, rating, note);
            if (Database.withContext(getContext()).addRecord(record)) {
                getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }
}