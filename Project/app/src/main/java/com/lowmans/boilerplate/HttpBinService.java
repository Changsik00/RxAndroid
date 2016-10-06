package com.lowmans.boilerplate;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpBinService {
    @GET("/get")
    Call<HttpBinResponse> get();

    @GET("/get")
    Call<HttpBinResponse> getWithArg(@Query("testArg") String arg);

    @FormUrlEncoded
    @POST("/post")
    Call<HttpBinResponse> postWithFormParams(@Field("field1") String field1);

    @POST("/post")
    Call<HttpBinResponse> postWithJson(@Body LoginData loginData);


    @ToString
    @AllArgsConstructor
    class HttpBinResponse {
        String url;
        String origin;
        Map headers;
        Map args;
        Map form;
        Map json;
    }

    @ToString
    @AllArgsConstructor
    class LoginData {
        String username;
        String password;
    }
}
