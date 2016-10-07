package com.study.lowmans.rxandroidtest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lowmans.boilerplate.RxBus;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends RxAppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        eventSubscribe();
    }

    private void eventSubscribe() {
        RxBus.getInstance().getTestObserverable()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    textView.setText(String.valueOf(event));
                });
    }


    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    void buttonOnClick(View view) {

        switch (view.getId()) {
            case R.id.button1:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;

            case R.id.button2:
                startActivity(new Intent(this, RxEventbusActivity.class));
                break;

            case R.id.button3:
                startActivity(new Intent(this, RxBindingActivity.class));
                break;

            case R.id.button4:
                Observable.from(new Integer[]{1, 2, 3, 4, 5,}).reduce((i, j) -> i + j).subscribe(result -> {
                    Log.i("#@#", "total : " + result);
                });
                break;
        }
    }
}
