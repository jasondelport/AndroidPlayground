package com.jasondelport.notes.network;

import com.jasondelport.notes.BuildConfig;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by jasondelport on 23/02/2015.
 */
public class NetworkClient {

    private static Api api;

    static {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.URL)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        api = restAdapter.create(Api.class);
    }

    private NetworkClient() {}

    public static Api getService() {
        return api;
    }

}
