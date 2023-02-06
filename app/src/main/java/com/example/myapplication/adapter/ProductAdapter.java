package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.Product;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_product_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductViewHolder holder, int position) {
        holder.priceValue.setText(Double.toString(products.get(position).getLastPurchasePrice()));
        holder.locationValue.setText(products.get(position).getLastPurchaseLocation());
        holder.brandValue.setText(products.get(position).getBrandName());
        holder.typeValue.setText(products.get(position).getProductType());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView priceValue, locationValue, brandValue, typeValue;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImageView);
            priceValue = itemView.findViewById(R.id.priceValueTextView);
            locationValue = itemView.findViewById(R.id.locationValueTextView);
            brandValue = itemView.findViewById(R.id.brandValueTextView);
            typeValue = itemView.findViewById(R.id.typeValueTextView);
        }
    }
}