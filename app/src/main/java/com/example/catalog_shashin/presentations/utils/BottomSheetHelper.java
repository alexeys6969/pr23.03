package com.example.catalog_shashin.presentations.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.catalog_shashin.MainActivity;
import com.example.catalog_shashin.R;
import com.example.network.domains.common.Settings;
import com.example.network.domains.models.Product;
import com.example.uicomponents.button.BthBig;
import com.example.uicomponents.button.BthCustom;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

public class BottomSheetHelper {
    public static void Create (
            Context context,
            Activity activity,
            Product product,
            BthCustom bthCardAdd,
            ProgressDialogHelper progressDialogHelper) {
        progressDialogHelper.progressDialog.show();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_description, null);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        TextView tvExpenditure = view.findViewById(R.id.tvExpenditure);
        View bthClose = view.findViewById(R.id.bthClose);
        BthBig bthAdd = view.findViewById(R.id.bthAdd);
        ImageView image = view.findViewById(R.id.imageView);

        if(product.img != null) {
            Picasso
                    .with(context)
                    .load(Settings.URL + "/img/" + product.img)
                    .into(image);
        }
        tvName.setText(product.name);
        tvDescription.setText(product.description);
        tvExpenditure.setText(product.expenditure);
        bthAdd.init("Добавить за " + product.price + "P", BthCustom.TypeButton.PRIMARY);
        bthClose.setOnClickListener(v -> {
            dialog.hide();
        });
        bthAdd.Bth.setOnClickListener(v -> {
            ((MainActivity)activity).BasketCreate(product, bthCardAdd;
        });
        dialog.setContentView(view);
        progressDialogHelper.progressDialog.hide();
        dialog.show();
    }
}
