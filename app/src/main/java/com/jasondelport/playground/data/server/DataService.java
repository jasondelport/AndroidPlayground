package com.jasondelport.playground.data.server;

import com.jasondelport.playground.BuildConfig;
import com.jasondelport.playground.PlaygroundApp;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jasondelport on 23/02/2015.
 */
public class DataService {

    private RestApi api;

    public DataService() {
        if (api == null) {

            // https://github.com/square/okhttp/wiki/Recipes

            //HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));

            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            File cacheDirectory = new File(PlaygroundApp.getContext().getCacheDir().getAbsolutePath(), "HttpCache");
            Cache cache = new Cache(cacheDirectory, cacheSize);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .cache(cache)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.URL)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(RestApi.class);
        }
    }

    public RestApi getRestApi() {
        return api;
    }

}
