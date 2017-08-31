
# RxJava2 Tutorial

RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.

It extends the observer pattern to support sequences of data/events and adds operators that allow you to compose sequences together declaratively while abstracting away concerns about things like low-level threading, synchronization, thread-safety and concurrent data structures.

## Set up your environment
1. Open your app’s build.gradle file. This is usually not the top-level build.gradle file but app/build.gradle.
2. Add the following lines inside dependencies:
```java
compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
```
## Build blocks for RxJava
The build blocks for RxJava code are the following:
1. observables representing sources of data
2. subscribers (or observers) listening to the observables
3. a set of methods for modifying and composing the data

An observable emits items; a subscriber consumes those items.

### Observables
Observables are the sources for the data. Usually they start providing data once a subscriber starts listening. An observable may emit any number of items (including zero items). It can terminate either successfully or with an error. Sources may never terminate, for example, an observable for a button click can potentially produce an infinite stream of events.

### Subscribers
A observable can have any number of subscribers. If a new item is emitted from the observable, the onNext() method is called on each subscriber. If the observable finishes its data flow successful, the onComplete() method is called on each subscriber. Similar, if the observable finishes its data flow with an error, the onError() method is called on each subscriber.

## Creating Observables, subscribing to them and disposing them
### You can create different types of observables.
#### Flowable\<T\> :
  Emits 0 or n items and terminates with an success or an error event. Supports backpressure, which allows to control how fast a source emits items.
#### Observable\<T\>:
  Emits 0 or n items and terminates with an success or an error event.
#### Single\<T\>:
  Emits either a single item or an error event. The reactive version of a method call.
#### Maybe\<T\>:
  Succeeds with an item, or no item, or errors. The reactive version of an Optional.
#### Completable:
  Either completes with an success or with an error event. It never emits items. The reactive version of a Runnable.

### RxJava provides several convenience methods to create observables
#### Observable.just("Hello"):
  Allows to create an observable as wrapper around other data types
#### Observable.fromIterable():
  Takes an java.lang.Iterable<T> and emits their values in their order in the data structure
#### Observable.fromArray():
  Takes an array and emits their values in their order in the data structure
#### Observable.fromCallable():
  Allows to create an observable for a java.util.concurrent.Callable<V>
#### Observable.fromFuture():
  Allows to create an observable for a java.util.concurrent.Future
#### Observable.interval():
  An observable that emits Long objects in a given interval
  
## Unsubscribe to avoid memory leaks
Observable.subscribe() returns a Subscription (if you are using a Flowable) or a Disposable object. To prevent a possible (temporary) memory leak, unsubscribe from your observables in the 'onStop()' method of the activity or fragment. 
```java
@Override
protected void onStop() {
    super.onStop();
    if (mDisposable != null && !mDisposable.isDisposed()) {
        mDisposable.dispose();
    }
}

@Override
protected void onDestroy() {
    super.onDestroy();
    if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
        mCompositeDisposable.clear();
    }
}
```

## Examples for different types of observables.
#### An example for the creation of an Observable
```java
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
```
#### An example for the creation of a Maybe
```java
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
```
#### An example for the Observable disposable subscribe method
```java
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
```
#### An example for the Single disposable subscribeWith method
```java
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
```
#### An example for the compositeDisposable
```java
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
```
## Examples for rxjava operations
#### an example for the RxJava mutipley operators
```java
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
```
#### suppress duplicate items emitted by the source Observable
```java
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
```
#### suppress duplicate consecutive items emitted by the source Observable
```java
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
```
#### emit only the first n items emitted by an Observable
```java
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
```
