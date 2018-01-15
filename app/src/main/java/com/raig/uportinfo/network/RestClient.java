package com.raig.uportinfo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gaurav on 15-01-2018.
 */

public class RestClient {

    public static RestClient client;
    private final String BASE_URL = "%%%%%%%%";
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
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public UportService getUportService(){
        return getRetrofitInstance().create(UportService.class);
    }

}
