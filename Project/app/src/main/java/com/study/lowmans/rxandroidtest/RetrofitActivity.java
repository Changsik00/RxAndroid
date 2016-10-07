package com.study.lowmans.rxandroidtest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lowmans.boilerplate.ApiClient;
import com.lowmans.boilerplate.HttpBinService;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitActivity extends RxAppCompatActivity {

    private HttpBinService httpBinService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);

        httpBinService = ApiClient.getInstance().getHttpBinService();
    }

    @OnClick({R.id.button1, R.id.button2})
    void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                httpBinService.get()
                        .compose(bindToLifecycle())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                httpBinResponse -> {
                                    Log.i("#@#", "onResponse : onSuccess -> " + httpBinResponse.toString());
                                },
                                error -> {
                                    Log.i("#@#", "onFailure : " + error);
                                });
                break;

            case R.id.button2:
                HttpBinService.LoginData data = new HttpBinService.LoginData("Testuesr", "password111");
                httpBinService.postWithJson(data)
                        .compose(bindUntilEvent(ActivityEvent.DESTROY))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                httpBinResponse -> {
                                    Log.i("#@#", "onResponse : onSuccess -> " + httpBinResponse.toString());
                                },
                                error -> {
                                    Log.i("#@#", "onFailure : " + error);
                                });
                break;
        }
    }
}
