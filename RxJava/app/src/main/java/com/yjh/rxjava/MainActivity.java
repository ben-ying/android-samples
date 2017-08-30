package com.yjh.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Integer> mList;
    private CompositeDisposable mCompositeDisposable;
    private Disposable mDisposable;
    private CheeseSearchEngine mCheeseSearchEngine;
    private EditText mQueryEditText;
    private Button mSearchButton;
    private CheeseAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(mAdapter = new CheeseAdapter());

        mQueryEditText = findViewById(R.id.query_edit_text);
        mSearchButton = findViewById(R.id.search_button);
        mProgressBar = findViewById(R.id.progress_bar);

        List<String> cheeses = Arrays.asList(getResources().getStringArray(R.array.cheeses));
        mCheeseSearchEngine = new CheeseSearchEngine(cheeses);

        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(i);
        }

        simpleObservableExample();

        observableListExample();

        maybeListExample();

        observableDisposableExample();

        singleDisposableExample();

        compositeDisposableExample();

        operatorsExample();

        distinctExample();

        distinctUntilChangedExample();

        takeExample();
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
        mDisposable = listObservable.subscribe(new Consumer<Integer>() {
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
        mDisposable = createButtonClickObservable()
                // filter items emitted by an Observable
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.length() > 1;
                    }
                })
                // only emit an item from the source Observable after a particular
                // time span has passed without the Observable emitting any other items
                .debounce(300, TimeUnit.MILLISECONDS)
                // subscribe on io
                .subscribeOn(Schedulers.io())
                // observeOn mainThread
                .observeOn(AndroidSchedulers.mainThread())
                // register an action to take whenever an Observable emits an item
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "doOnNext s: " + s);
                        showProgressBar();
                    }
                })
                // observeOn io
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String s) throws Exception {
                        return mCheeseSearchEngine.search(s);
                    }
                })
                // transform the items emitted by an Observable by applying a function to each of them
                .observeOn(AndroidSchedulers.mainThread()) // observeOn mainThread
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        hideProgressBar();
                        showResult(strings);
                    }
                });
    }

    // suppress duplicate items emitted by the source Observable
    private void distinctExample() {
        mDisposable = Observable.just(1, 2, 2, 3, 2, 3, 3)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "distinct: " + integer);
                    }
                });
    }

    // suppress duplicate consecutive items emitted by the source Observable
    private void distinctUntilChangedExample() {
        mDisposable = Observable.just(1, 2, 2, 3, 2, 3, 3)
                .distinctUntilChanged()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "distinctUntilChanged: " + integer);
                    }
                });
    }

    // emit only the first n items emitted by an Observable
    private void takeExample() {
        mDisposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
            }
        }).take(3).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "take: " + s);
            }
        });
    }

    private Observable<String> createButtonClickObservable() {
        Observable<String> buttonObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                mSearchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emitter.onNext(mQueryEditText.getText().toString());
                    }
                });
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        mSearchButton.setOnClickListener(null);
                    }
                });
            }
        });

        Observable<String> editTextObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                final TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s.toString());
                    }
                };

                mQueryEditText.addTextChangedListener(watcher);

                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        mQueryEditText.removeTextChangedListener(watcher);
                    }
                });
            }
        });

        return Observable.merge(buttonObservable, editTextObservable);
    }

    protected void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    protected void showResult(List<String> result) {
        if (result.isEmpty()) {
            Toast.makeText(this, R.string.nothing_found, Toast.LENGTH_SHORT).show();
            mAdapter.setCheeses(Collections.<String>emptyList());
        } else {
            mAdapter.setCheeses(result);
        }
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
