package com.study.lowmans.rxandroidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lowmans.boilerplate.ApiClient;
import com.lowmans.boilerplate.HttpBinService;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private HttpBinService httpBinService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        httpBinService = ApiClient.getInstance().getHttpBinService();
    }

    @OnClick({R.id.button1, R.id.button2})
    void buttonOnClick(View view) {

        switch (view.getId()) {
            case R.id.button1:
                httpBinService.get().enqueue(new Callback<HttpBinService.HttpBinResponse>() {
                    @Override
                    public void onResponse(Call<HttpBinService.HttpBinResponse> call, Response<HttpBinService.HttpBinResponse> response) {
                        Log.i("#@#", "onResponse");
                        if (response.isSuccessful()) {
                            HttpBinService.HttpBinResponse httpBinResponse = response.body();
                            Log.i("#@#", "onResponse : onSuccess -> " + httpBinResponse.toString());
                        } else {
                            Log.i("#@#", "onResponse : onFailure");
                        }
                    }

                    @Override
                    public void onFailure(Call<HttpBinService.HttpBinResponse> call, Throwable t) {
                        Log.i("#@#", "onFailure : " + t);
                    }
                });
                break;

            case R.id.button2:
                httpBinService.postWithJson(new HttpBinService.LoginData("Testuesr", "password111"))
                        .enqueue(new Callback<HttpBinService.HttpBinResponse>() {
                            @Override
                            public void onResponse(Call<HttpBinService.HttpBinResponse> call, Response<HttpBinService.HttpBinResponse> response) {
                                Log.i("#@#", "onResponse");
                                if (response.isSuccessful()) {
                                    HttpBinService.HttpBinResponse httpBinResponse = response.body();
                                    Log.i("#@#", "onResponse : onSuccess -> " + httpBinResponse.toString());
                                } else {
                                    Log.i("#@#", "onResponse : onFailure");
                                }
                            }

                            @Override
                            public void onFailure(Call<HttpBinService.HttpBinResponse> call, Throwable t) {
                                Log.i("#@#", "onFailure : " + t);
                            }
                        });
                break;
        }
    }
}
