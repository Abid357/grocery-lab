package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;
import com.google.android.material.button.MaterialButton;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_product_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductViewHolder holder, int position) {
        holder.productName.setText(productList.get(holder.getAdapterPosition()).getName());
        holder.uom.setText(productList.get(holder.getAdapterPosition()).getUom());
        holder.brandCount.setText(Integer.toString(productList.get(holder.getAdapterPosition()).getBrandCount()));
        holder.deleteProductButton.setOnClickListener(v -> {
            Database.withContext(context).deleteProduct(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, uom, brandCount;
        MaterialButton deleteProductButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTextView);
            deleteProductButton = itemView.findViewById(R.id.deleteProductButton);
            uom = itemView.findViewById(R.id.uomValueTextView);
            brandCount = itemView.findViewById(R.id.brandCountValueTextView);
        }
    }
}