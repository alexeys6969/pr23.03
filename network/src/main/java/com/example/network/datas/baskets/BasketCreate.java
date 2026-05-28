package com.example.network.datas.baskets;

import com.example.network.domains.apis.MyAsyncTask;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.common.Settings;
import com.example.network.domains.models.BasketParams;
import com.google.gson.GsonBuilder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class BasketCreate extends MyAsyncTask {
    String token;
    BasketParams basketParams;
    public BasketCreate(String token, BasketParams basketParams, MyResponseCallback callback) {
        super(callback);
        this.token = token;
        this.basketParams = basketParams;
    }
    @Override
    protected String doInBackground(Void... voids) {
        String rawData = new GsonBuilder().create().toJson(basketParams);
        try {
            Connection.Response response = Jsoup.connect(Settings.URL + "/api/basket/create")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.POST)
                    .header("Content-type", "application/json")
                    .header("token", token)
                    .requestBody(rawData)
                    .execute();
            return response.statusCode() == 200 ? response.body() : "Error: " + response.body();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
}
