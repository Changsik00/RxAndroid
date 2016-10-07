package com.study.lowmans.rxandroidtest;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class RxBindingActivity extends AppCompatActivity {

    @BindView(R.id.pwTextInputLayout1)
    TextInputLayout pwTextInputLayout1;

    @BindView(R.id.pwTextInputLayout2)
    TextInputLayout pwTextInputLayout2;

    @BindView(R.id.pwTextInputEditText1)
    TextInputEditText pwTextInputEditText1;

    @BindView(R.id.pwTextInputEditText2)
    TextInputEditText pwTextInputEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        ButterKnife.bind(this);

        Observable<String> passwordObservable1 = RxTextView.textChanges(pwTextInputEditText1).map(CharSequence::toString);
        Observable<String> passwordObservable2 = RxTextView.textChanges(pwTextInputEditText2).map(CharSequence::toString);

        Observable.combineLatest(passwordObservable1, passwordObservable2, Password::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.password1.equals(result.password2)) {
                        pwTextInputLayout2.setError(null);
                    } else {
                        pwTextInputLayout2.setError("비밀번호가 맞지 않습니다.");
                    }
                });
    }

    @AllArgsConstructor
    class Password {
        public String password1;
        public String password2;
    }
}
