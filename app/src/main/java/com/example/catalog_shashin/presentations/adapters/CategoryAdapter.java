package com.example.catalog_shashin.presentations.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalog_shashin.R;
import com.example.catalog_shashin.domains.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<Category> categories;
    int selectPosition = 0;
    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        int BackgroundResource = selectPosition == position ?
                R.color.category_active : R.color.category_disabled;
        int BackgroundColor = context.getResources().getColor(BackgroundResource, context.getTheme());
        int TextResource = selectPosition == position ?
                R.color.white : R.color.black;
        int TextColor = context.getResources().getColor(TextResource, context.getTheme());

        holder.tvName.setText(category.name);
        holder.clParent.setBackgroundTintList(ColorStateList.valueOf(BackgroundColor));
        holder.tvName.setTextColor(TextColor);

        holder.clParent.setOnClickListener(v -> {
            notifyItemChanged(selectPosition);
            selectPosition = position;
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ConstraintLayout clParent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            clParent = itemView.findViewById(R.id.clParent);
        }
    }
}
