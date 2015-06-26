package com.jasondelport.notes.data;

import com.jasondelport.notes.BuildConfig;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by jasondelport on 23/02/2015.
 */
public class DataManager {

    private static RestApi api;

    static {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.URL)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        api = restAdapter.create(RestApi.class);
    }

    private DataManager() {}

    public static RestApi getInstance() {
        return api;
    }

}