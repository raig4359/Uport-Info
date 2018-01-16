package com.raig.uportinfo.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gaurav on 15-01-2018.
 */

public class RestClient {

    public static RestClient client;
    private final String BASE_URL = "%%%%%%%%%%";
    private Retrofit retrofit;
    private UportService service;

    public static RestClient getRestClient() {
        if (client == null) {
            client = new RestClient();
        }
        return client;
    }

    private  Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    public UportService getUportService(){
        return getRetrofitInstance().create(UportService.class);
    }

}
