package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.Record;
import com.google.android.material.button.MaterialButton;

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
//        holder.brandName.setText(brands.get(holder.getAdapterPosition()).getRecordName());
//        holder.brandProduct.setText(brands.get(holder.getAdapterPosition()).getProductName());
//        holder.deleteRecordButton.setOnClickListener(v -> {
//            Database.withContext(context).deleteRecord(holder.getAdapterPosition());
//            notifyItemRemoved(holder.getAdapterPosition());
//        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        ImageView brandImage;
        TextView brandName, brandProduct;
        MaterialButton viewRecordButton, deleteRecordButton;

        public RecordViewHolder(View itemView) {
            super(itemView);
//            brandImage = itemView.findViewById(R.id.brandImageView);
//            brandName = itemView.findViewById(R.id.brandNameTextView);
//            brandProduct = itemView.findViewById(R.id.brandProductTextView);
//            viewRecordButton = itemView.findViewById(R.id.viewRecordButton);
//            deleteRecordButton = itemView.findViewById(R.id.deleteRecordButton);
        }
    }
}