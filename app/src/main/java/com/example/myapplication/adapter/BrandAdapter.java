package com.example.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.Brand;
import com.example.myapplication.core.Database;
import com.google.android.material.button.MaterialButton;

import java.util.List;


public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private List<Brand> brands;
    private Context context;

    public BrandAdapter(Context context, List<Brand> brands) {
        this.context = context;
        this.brands = brands;
    }

    @Override
    public BrandAdapter.BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_brand_list_item, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandAdapter.BrandViewHolder holder, int position) {
        holder.brandName.setText(brands.get(holder.getAdapterPosition()).getBrandName());
        holder.brandProduct.setText(brands.get(holder.getAdapterPosition()).getProductName());
        holder.deleteBrandButton.setOnClickListener(v -> {
            Database.withContext(context).deleteBrand(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView brandImage;
        TextView brandName, brandProduct;
        MaterialButton viewBrandButton, deleteBrandButton;

        public BrandViewHolder(View itemView) {
            super(itemView);
            brandImage = itemView.findViewById(R.id.brandImageView);
            brandName = itemView.findViewById(R.id.brandNameTextView);
            brandProduct = itemView.findViewById(R.id.brandProductTextView);
            viewBrandButton = itemView.findViewById(R.id.viewBrandButton);
            deleteBrandButton = itemView.findViewById(R.id.deleteBrandButton);
        }
    }
}