package com.lowmans.boilerplate;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import rx.Observable;
import rx.subjects.PublishSubject;

// https://gist.github.com/benjchristensen/04eef9ca0851f3a5d7bf
public class RxBus {
    private static RxBus instance = null;

    // This is better done with a DI Library like Dagger
    public static RxBus getInstance() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }


    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    // private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    private final PublishSubject<Object> testEventBus = PublishSubject.create();

    public void sendTestEvent(Object o) {
        testEventBus.onNext(o);
    }

    @RxLogObservable
    public Observable<Object> getTestObserverable() {
        return testEventBus;
    }
}
