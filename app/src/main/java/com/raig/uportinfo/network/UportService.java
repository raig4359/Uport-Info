package com.raig.uportinfo.network;

import com.raig.uportinfo.rest_resource_model.ApiResponse;
import com.raig.uportinfo.rest_resource_model.AutoVariantResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Gaurav on 13-01-2018.
 */

public interface UportService {

    @FormUrlEncoded
    @POST("@@")
    Call<ApiResponse> performLogin(@FieldMap Map<String,String> credentials);

    @GET("%%")
    Call<AutoVariantResponse> getTruckTypes();

    @Multipart
    @POST("&&")
    Call<ApiResponse> uploadData(@Part("userData") String userData, @Part("Image") RequestBody image);
}
