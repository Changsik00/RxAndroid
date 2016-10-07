package com.study.lowmans.rxandroidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lowmans.boilerplate.RxBus;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

public class RxEventbusActivity extends RxAppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_eventbus);
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

    @OnClick(R.id.button)
    void buttonOnClick(View view) {
        RxBus.getInstance().sendTestEvent("Hello EventBus");
    }
}
