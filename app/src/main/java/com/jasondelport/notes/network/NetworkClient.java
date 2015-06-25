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

    /*

        private GithubApi _createGithubApi() {

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(
              "https://api.github.com/");
        //.setLogLevel(RestAdapter.LogLevel.FULL);

        final String githubToken = getResources().getString(R.string.github_oauth_token);
        if (!isNullOrEmpty(githubToken)) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Authorization", format("token %s", githubToken));
                }
            });
        }

        return builder.build().create(GithubApi.class);
    }

     */

}
