package com.lowmans.boilerplate;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final Boolean debug = true;

    private static final String API_URL = "http://httpbin.org";

    private static ApiClient instance = null;
    private Map<String, String> headers = new HashMap<>();

    private HttpBinService httpBinService;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    private ApiClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.networkInterceptors().add(chain -> {
            Request.Builder builder = chain.request().newBuilder();

            Log.i("#@#", "Adding headers:" + headers);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }

            return chain.proceed(builder.build());
        });

        if (debug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            httpClient.addInterceptor(logging);
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        httpBinService = retrofit.create(HttpBinService.class);
    }

    public HttpBinService getHttpBinService() {
        return httpBinService;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public void removeHeader(String key) {
        headers.remove(key);
    }

    public void removeAllHeaders() {
        headers.clear();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}