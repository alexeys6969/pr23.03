package com.example.catalog_shashin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalog_shashin.presentations.adapters.CategoryAdapter;
import com.example.catalog_shashin.presentations.utils.BottomSheetHelper;
import com.example.catalog_shashin.presentations.utils.ProgressDialogHelper;
import com.example.catalog_shashin.datas.CategoryContext;
import com.example.network.datas.baskets.BasketCreate;
import com.example.network.datas.baskets.BasketUpdate;
import com.example.network.datas.products.ProductGet;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.models.BasketParams;
import com.example.network.domains.models.Product;
import com.example.uicomponents.button.BthCustom;
import com.example.uicomponents.button.BthSmall;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView llCategory;
    LinearLayout llProducts;
    ProgressDialogHelper progressDialogHelper;
    String Token = "07a922ae-4e9f-4981-b9c0-bf31d1786439";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llCategory = findViewById(R.id.llCategory);
        llProducts = findViewById(R.id.llProducts);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, CategoryContext.allCategory());
        llCategory.setAdapter(categoryAdapter);
        progressDialogHelper = new ProgressDialogHelper(this);
        RequestProductGet();
    }

    public void RequestProductGet() {
        progressDialogHelper.progressDialog.show();
        ProductGet RPG = new ProductGet(
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("PRODUCTS GET", result);
                        ArrayList<Product> Products = new GsonBuilder().create().fromJson(
                                result, new TypeToken<ArrayList<Product>>(){}.getType());
                        CreateProduct(Products);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("PRODUCTS GET", error);
                        progressDialogHelper.progressDialog.hide();
                    }
                }
        );
        RPG.execute();
    }

    public void BasketCreate(Product product, BthCustom bthAdd) {
        Log.d("DEBUG_BTN", "=== BasketCreate called ===");

        ChangeBthState(bthAdd, true);

        progressDialogHelper.progressDialog.show();
        BasketCreate RequestBC = new BasketCreate(
                Token,
                new BasketParams(product.id, 0),
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("BASKET CREATE", result);
                        progressDialogHelper.progressDialog.hide();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("BASKET ERROR", error);
                        ChangeBthState(bthAdd, false);
                        progressDialogHelper.progressDialog.hide();
                    }
                }
        );
        RequestBC.execute();
    }

    public void BasketUPD(Product product, BthCustom bthAdd) {
        Log.d("DEBUG_BTN", "=== BasketUPD called ===");

        ChangeBthState(bthAdd, false);

        progressDialogHelper.progressDialog.show();
        BasketUpdate RBU = new BasketUpdate(
                new BasketParams(0, product.id),
                Token,
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("BASKET UPDATE", result);
                        progressDialogHelper.progressDialog.hide();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("BASKET ERROR", error);
                        ChangeBthState(bthAdd, true);
                        progressDialogHelper.progressDialog.hide();
                    }
                }
        );
        RBU.execute();
    }

    public void ChangeBthState(BthCustom bthAdd, boolean isBasket) {
        if (bthAdd != null) {
            if (isBasket) {
                bthAdd.reinit("Убрать", BthCustom.TypeButton.SECONDARY);
            } else {
                bthAdd.reinit("Добавить", BthCustom.TypeButton.PRIMARY);
            }
        }
    }

    public void CreateProduct(ArrayList<Product> products) {
        String[] NameCategory = new String[] {"Мужское", "Женское", "Unisex"};

        for(Product product : products) {
            View item = LayoutInflater.from(this).inflate(R.layout.item_product, llProducts, false);
            TextView tvName = item.findViewById(R.id.tvName);
            TextView tvCategory = item.findViewById(R.id.tvCategory);
            TextView tvPrice = item.findViewById(R.id.tvPrice);
            BthSmall bthAdd = item.findViewById(R.id.bthAdd);

            Log.d("DEBUG_BTN", "=== NEW PRODUCT: " + product.name + " ===");
            Log.d("DEBUG_BTN", "bthAdd: " + bthAdd);
            Log.d("DEBUG_BTN", "bthAdd.Bth: " + bthAdd.Bth);
            if (bthAdd.Bth != null) {
                Log.d("DEBUG_BTN", "Initial Bth text: " + bthAdd.Bth.getText());
            }

            tvName.setText(product.name);
            bthAdd.init("Добавить", BthCustom.TypeButton.PRIMARY);

            if(product.gender >= 0 && product.gender <= 2)
                tvCategory.setText(NameCategory[product.gender]);
            else
                tvCategory.setText("Неизвестно");

            tvPrice.setText(product.price + "P");

            item.setOnClickListener(v -> {
                BottomSheetHelper.Create(this, this, product, bthAdd, progressDialogHelper);
            });

            bthAdd.Bth.setOnClickListener(v -> {
                Log.d("DEBUG_BTN", "=== CLICK on " + product.name + " ===");
                Log.d("DEBUG_BTN", "Text: " + bthAdd.Bth.getText());

                if(bthAdd.Bth.getText().equals("Добавить")) {
                    Log.d("DEBUG_BTN", "Calling BasketCreate");
                    BasketCreate(product, bthAdd);
                } else {
                    Log.d("DEBUG_BTN", "Calling BasketUPD");
                    BasketUPD(product, bthAdd);
                }
            });

            llProducts.addView(item);
        }
        progressDialogHelper.progressDialog.hide();
    }
}