package com.example.network.datas.baskets;

import android.util.Log;

import com.example.network.domains.apis.MyAsyncTask;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.common.Settings;
import com.example.network.domains.models.BasketParams;
import com.google.gson.GsonBuilder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class BasketUpdate extends MyAsyncTask {

    String token;
    BasketParams basketRequest;

    public BasketUpdate(BasketParams basketRequest,
                        String token,
                        MyResponseCallback callback) {

        super(callback);

        this.token = token;
        this.basketRequest = basketRequest;
    }

    @Override
    protected String doInBackground(Void... voids) {

        String rawData =
                new GsonBuilder().create().toJson(basketRequest);

        Log.d("BASKET_JSON", rawData);

        try {

            Connection.Response response =
                    Jsoup.connect(Settings.URL + "/api/basket/update")
                            .ignoreContentType(true)
                            .ignoreHttpErrors(true)
                            .method(Connection.Method.PUT)
                            .header("Content-type", "application/json")
                            .header("token", token)
                            .requestBody(rawData)
                            .execute();

            String responseBody = response.body();

            Log.d("BASKET_CODE",
                    String.valueOf(response.statusCode()));

            Log.d("BASKET_RESPONSE", responseBody);

            if(response.statusCode() == 200) {

                return responseBody;

            } else {

                return "Error: " + responseBody;
            }

        } catch (IOException e) {

            return "Error: " + e.getMessage();
        }
    }
}