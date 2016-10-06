package com.lowmans.boilerplate;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.ToString;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface HttpBinService {
    @GET("/get")
    Observable<HttpBinResponse> get();

    @GET("/get")
    Observable<HttpBinResponse> getWithArg(@Query("testArg") String arg);

    @FormUrlEncoded
    @POST("/post")
    Observable<HttpBinResponse> postWithFormParams(@Field("field1") String field1);

    @POST("/post")
    Observable<HttpBinResponse> postWithJson(@Body LoginData loginData);


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
