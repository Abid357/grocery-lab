package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.Brand;
import com.example.myapplication.core.Database;

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
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_brand_list_item, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandAdapter.BrandViewHolder holder, int position) {
        holder.brandName.setText(brands.get(holder.getAdapterPosition()).getName());
        holder.brandProduct.setText(brands.get(holder.getAdapterPosition()).getProductName());
    }

    @Override
    public int getItemCount() {
        return brands.size();
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
                        if (Database.withContext(context).deleteBrand(position))
                            notifyItemRemoved(position);
                        else
                            notifyItemChanged(position);
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

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView brandImage;
        TextView brandName, brandProduct;

        public BrandViewHolder(View itemView) {
            super(itemView);
            brandImage = itemView.findViewById(R.id.brandImageView);
            brandName = itemView.findViewById(R.id.brandNameTextView);
            brandProduct = itemView.findViewById(R.id.brandProductTextView);
        }
    }
}