package com.example.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;
import com.example.myapplication.core.Record;
import com.example.myapplication.core.Utils;

import java.util.List;


public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {
    private List<Record> records;
    private final Context context;

    public RecordAdapter(Context context, List<Record> records) {
        this.context = context;
        this.records = records;
    }

    @Override
    public RecordAdapter.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_record_list_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordAdapter.RecordViewHolder holder, int position) {
        holder.brandName.setText(records.get(holder.getAdapterPosition()).getBrandName());
        holder.productName.setText(records.get(holder.getAdapterPosition()).getProductName());
        if (records.get(holder.getAdapterPosition()).isPurchase())
            holder.purchaseIcon.setVisibility(View.VISIBLE);
        else holder.purchaseIcon.setVisibility(View.GONE);
        if (TextUtils.isEmpty(records.get(holder.getAdapterPosition()).getLocation()))
            holder.locationIcon.setVisibility(View.GONE);
        else holder.locationIcon.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(records.get(holder.getAdapterPosition()).getLink()))
            holder.linkIcon.setVisibility(View.GONE);
        else holder.linkIcon.setVisibility(View.VISIBLE);
        holder.ratingBar.setRating((float) records.get(holder.getAdapterPosition()).getRating());
        double perPrice = records.get(holder.getAdapterPosition()).getPricePerUom();
        Product product = Database.withContext(context).getProductByName(records.get(holder.getAdapterPosition()).getProductName());
        holder.perPrice.setText(Utils.formatDecimal(perPrice) + " per " + product.getUom());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        ImageView purchaseIcon, locationIcon, linkIcon;
        TextView brandName, productName, perPrice;
        RatingBar ratingBar;

        public RecordViewHolder(View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.recordBrandNameTextView);
            productName = itemView.findViewById(R.id.recordProductNameTextView);
            purchaseIcon = itemView.findViewById(R.id.purchaseIconImageView);
            locationIcon = itemView.findViewById(R.id.locationIconImageView);
            linkIcon = itemView.findViewById(R.id.linkIconImageView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            perPrice = itemView.findViewById(R.id.perPriceTextView);
        }
    }
}