package com.example.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.Product;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        View view = LayoutInflater.from(context).inflate(R.layout.activity_product_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductViewHolder holder, int position) {
        holder.productName.setText(productList.get(holder.getAdapterPosition()).getName());
        SharedPreferences sp = context.getSharedPreferences("grocerylab", Context.MODE_PRIVATE);
        holder.deleteProductButton.setOnClickListener(v -> {
            productList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            try {
                JSONArray jsonArray = new JSONArray();
                Gson gson = new Gson();
                for (Product p : productList)
                    jsonArray.put(new JSONObject(gson.toJson(p)));
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Product.PRODUCT_TAG, jsonArray.toString());
                editor.apply();
                Toast.makeText(context, "Product deleted.", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName;

        MaterialButton deleteProductButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTextView);
            deleteProductButton = itemView.findViewById(R.id.deleteProductButton);
        }
    }
}