package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.core.Database;
import com.example.myapplication.core.Product;

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
        holder.brandCount.setText(Integer.toString(Database.withContext(context).getBrandCount(productList.get(holder.getAdapterPosition()).getName())));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, uom, brandCount;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTextView);
            uom = itemView.findViewById(R.id.uomValueTextView);
            brandCount = itemView.findViewById(R.id.brandCountValueTextView);
        }
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
                        if (Database.withContext(context).deleteProduct(position))
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
}