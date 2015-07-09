package com.jasondelport.notes.data.server;

import com.jasondelport.notes.BuildConfig;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by jasondelport on 23/02/2015.
 */
public class DataManager {

    private RestApi api;

    public DataManager() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(15, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.URL)
                .setClient(new OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        api = restAdapter.create(RestApi.class);
    }

    public RestApi getRestApi() {
        return api;
    }

}
