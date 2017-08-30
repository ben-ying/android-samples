package com.yjh.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Integer> mList;
    private CompositeDisposable mCompositeDisposable;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(i);
        }

//        simpleObservableExample();
//
//        observableListExample();
//
//        maybeListExample();
//
//        observableDisposableExample();
//
//        singleDisposableExample();
//
//        compositeDisposableExample();
    }

    // a simple observable example
    private void simpleObservableExample() {
        Observable<String> observable = Observable.just("test");
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "simpleObservableExample onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "simpleObservableExample onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "simpleObservableExample onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "simpleObservableExample onComplete");
            }
        });
    }

    // an example for the creation of an Observable
    private void observableListExample() {
        Observable<Integer> listObservable =
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i : mList) {
                            e.onNext(i);
                        }
                    }
                });
        listObservable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "observableListExample onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "observableListExample onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "observableListExample onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "observableListExample onComplete");
            }
        });
    }

    // an example for the creation of a Maybe
    private void maybeListExample() {
        Maybe<List<Integer>> maybe = Maybe.create(new MaybeOnSubscribe<List<Integer>>() {
            @Override
            public void subscribe(MaybeEmitter<List<Integer>> e) throws Exception {
                e.onSuccess(mList);
                e.onComplete();
            }
        });
        maybe.subscribe(new MaybeObserver<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "maybeListExample onSubscribe");
            }

            @Override
            public void onSuccess(List<Integer> integers) {
                Log.d(TAG, "maybeListExample onSuccess: " + integers);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "maybeListExample onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "maybeListExample onComplete");
            }
        });
    }

    // an example for the Observable disposable subscribe method
    private void observableDisposableExample() {
        Observable<Integer> listObservable =
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i : mList) {
                            e.onNext(i);
                        }
                    }
                });
        mDisposable = listObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "observableDisposableExample accept1: " + integer);
            }
        });
        // handle error
        Disposable subscribe = listObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "observableDisposableExample accept2: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "observableDisposableExample Throwable: " + throwable.getMessage());
            }
        });
    }

    // an example for the Single disposable subscribeWith method
    private void singleDisposableExample() {
        Single<List<Integer>> listSingle = Single.just(mList);
        mDisposable = listSingle.subscribeWith(
                new DisposableSingleObserver<List<Integer>>() {
                    @Override
                    public void onSuccess(List<Integer> integers) {
                        Log.d(TAG, "singleDisposableExample onSuccess: " + integers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "singleDisposableExample onError: " + e.getMessage());
                    }
                });
    }

    // an example for the compositeDisposable
    private void compositeDisposableExample() {
        mCompositeDisposable = new CompositeDisposable();

        Single<List<Integer>> listSingle = Single.just(mList);

        Single<String> stringSingle = Single.just("test");

        mCompositeDisposable.add(listSingle.subscribeWith(
                new DisposableSingleObserver<List<Integer>>() {

                    @Override
                    public void onSuccess(List<Integer> integers) {
                        Log.d(TAG, "compositeDisposableExample onSuccess1: " + integers);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "compositeDisposableExample onError1: " + e.getMessage());
                    }
                }));

        mCompositeDisposable.add(stringSingle.subscribeWith(
                new DisposableSingleObserver<String>() {

                    @Override
                    public void onSuccess(String string) {
                        Log.d(TAG, "compositeDisposableExample onSuccess2: " + string);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "compositeDisposableExample onError2: " + e.getMessage());
                    }
                }));
    }

    // an example for the RxJava operators
    private void operatorsExample() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // disposing subscription
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        // disposing subscriptions
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
