package com.study.lowmans.rxandroidtest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lowmans.boilerplate.ApiClient;
import com.lowmans.boilerplate.HttpBinService;
import com.lowmans.boilerplate.RxBus;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {

    private HttpBinService httpBinService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        httpBinService = ApiClient.getInstance().getHttpBinService();

        RxBus.getInstance().getTestObserverable()
                .compose(bindToLifecycle())
                .subscribe(event -> {
                    if (event instanceof String) {
                        Log.i("#@#", "Event : " + event);
                    }
                });
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
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
                httpBinService.postWithJson(new HttpBinService.LoginData("Testuesr", "password111"))
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

            case R.id.button3:
                RxBus.getInstance().sendTestEvent("Hello EventBus");
                break;

            case R.id.button4:
                Observable.from(new Integer[]{1, 2, 3, 4, 5,}).reduce((i, j) -> i + j).subscribe(result -> {
                    Log.i("#@#", "total : " + result);
                });
                break;
        }
    }
}
