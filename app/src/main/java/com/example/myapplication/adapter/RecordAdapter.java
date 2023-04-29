package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private OnRecordClickListener listener;

    public RecordAdapter(Context context, List<Record> records, OnRecordClickListener listener) {
        this.context = context;
        this.records = records;
        this.listener = listener;
    }

    @Override
    public RecordAdapter.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_record_list_item, parent, false);
        return new RecordViewHolder(view, listener);
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

    public void setUpItemTouchHelper(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Database.withContext(context).deleteRecord(position);
                        notifyItemRemoved(position);
                    }

                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                            int actionState, boolean isCurrentlyActive) {
                        View itemView = viewHolder.itemView;
                        Drawable deleteIcon = ResourcesCompat.getDrawable(context.getResources(), R.drawable.baseline_delete_24, null);
                        int backgroundCornerOffset = 20;
                        int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                        int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                        int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                        ColorDrawable background = new ColorDrawable(context.getResources().getColor(R.color.deleteButton, null));
                        if (dX > 0) { // swiping to the right
                            int iconLeft = itemView.getLeft() + iconMargin;
                            int iconRight = itemView.getLeft() + iconMargin + deleteIcon.getIntrinsicWidth();
                            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                            background.setBounds(itemView.getLeft(), itemView.getTop(),
                                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                                    itemView.getBottom());
                        } else { // not swiping
                            background.setBounds(0, 0, 0, 0);
                        }

                        background.draw(c);
                        deleteIcon.draw(c);

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public interface OnRecordClickListener {
        void onRecordClick(int position);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView purchaseIcon, locationIcon, linkIcon;
        TextView brandName, productName, perPrice;
        RatingBar ratingBar;
        OnRecordClickListener listener;

        public RecordViewHolder(View itemView, OnRecordClickListener listener) {
            super(itemView);
            brandName = itemView.findViewById(R.id.recordBrandNameTextView);
            productName = itemView.findViewById(R.id.recordProductNameTextView);
            purchaseIcon = itemView.findViewById(R.id.purchaseIconImageView);
            locationIcon = itemView.findViewById(R.id.locationIconImageView);
            linkIcon = itemView.findViewById(R.id.linkIconImageView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            perPrice = itemView.findViewById(R.id.perPriceTextView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onRecordClick(getAdapterPosition());
        }
    }
}