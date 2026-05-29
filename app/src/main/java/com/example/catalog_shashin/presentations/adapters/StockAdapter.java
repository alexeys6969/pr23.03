package com.example.catalog_shashin.presentations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.catalog_shashin.R;
import com.example.network.domains.common.Settings;
import com.example.network.domains.models.Stock;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    Context context;
    ArrayList<Stock> stocks;

    public StockAdapter(Context context, ArrayList<Stock> stocks) {
        this.context = context;
        this.stocks = stocks;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_stock, parent, false);

        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {

        Stock stock = stocks.get(position);

        holder.tvName.setText(stock.product.name);

        holder.tvPrice.setText(stock.price + " ₽");

        Picasso
                .with(context)
                .load(Settings.URL + "/img/" + stock.product.img)
                .into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvPrice;
        ImageView imgProduct;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}