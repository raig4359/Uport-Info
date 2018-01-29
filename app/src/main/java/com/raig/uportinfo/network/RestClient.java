package com.raig.uportinfo.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gaurav on 15-01-2018.
 */

public class RestClient {

    private static RestClient client;
    private final String BASE_URL = " ";
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public static RestClient getRestClient() {
        if (client == null) {
            client = new RestClient();
        }
        return client;
    }

    private Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor).build();

        }
        return okHttpClient;
    }

    public UportService getUportService() {
        return getRetrofitInstance().create(UportService.class);
    }

}
