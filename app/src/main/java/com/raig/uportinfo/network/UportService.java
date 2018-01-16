package com.raig.uportinfo.network;

import com.raig.uportinfo.rest_resource_model.ApiResponse;
import com.raig.uportinfo.rest_resource_model.AutoVariantResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Gaurav on 13-01-2018.
 */

public interface UportService {

    @FormUrlEncoded
    @POST("$$$$")
    Call<ApiResponse> performLogin(@FieldMap Map<String,String> credentials);

    @POST("$$$$$")
    Call<AutoVariantResponse> getTruckTypes();

}
