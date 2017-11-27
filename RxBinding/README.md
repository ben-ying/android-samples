# RxBinding Tutorial


## Set up your environment
1. Open your app’s build.gradle file. This is usually not the top-level build.gradle file but app/build.gradle.
2. Add the following lines inside dependencies:

#### Platform bindings:
```java
compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
```
#### 'support-v4' library bindings:
```java
compile 'com.jakewharton.rxbinding2:rxbinding-support-v4:2.0.0'
```
#### 'appcompat-v7' library bindings:
```java
compile 'com.jakewharton.rxbinding2:rxbinding-appcompat-v7:2.0.0'
```
#### 'design' library bindings:
```java
compile 'com.jakewharton.rxbinding2:rxbinding-design:2.0.0'
```
#### 'recyclerview-v7' library bindings:
```java
compile 'com.jakewharton.rxbinding2:rxbinding-recyclerview-v7:2.0.0'
```
#### 'leanback-v17' library bindings:
```java
compile 'com.jakewharton.rxbinding2:rxbinding-leanback-v17:2.0.0'
```

## Using RxBinding
#### This is how Android developers typically react to a button click event:
```java
Button button = (Button)findViewById(R.id.button);
button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // do some work here     
            }
        });
```
#### Using RxBinding, you can accomplish the same thing but with RxJava subscription:
```java
Button button = (Button)findViewById(R.id.button);
Observable<Object> observable = RxView.clicks(button).share();
Disposable buttonDisposable = observable.subscribe(new Consumer<Object>() {
    @Override
    public void accept(Object aVoid) throws Exception {
         // do some work here     
    }
});
```
#### A text change listener for an EditText:
```java
final EditText nameEditText = (EditText) v.findViewById(R.id.et_name);
nameEditText.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do some work here with the updated text
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});
```
#### Written with RxBinding support:
```java
final EditText nameEditText = (EditText) v.findViewById(R.id.et_name);
Disposable editDisposable =
        RxTextView.textChanges(nameEditText).subscribe(new Consumer<CharSequence>() {
    @Override
    public void accept(CharSequence charSequence) throws Exception {
        // do some work here with the updated text
    }
});
```
#### Here’s the implementation of the TextViewTextOnSubscribe class that RxBinding uses behind the scenes for you in the RxTextView.textChanges() observable:
```java
final class TextViewTextOnSubscribe implements Observable.OnSubscribe<CharSequence> {
  final TextView view;

  TextViewTextOnSubscribe(TextView view) {
    this.view = view;
  }

  @Override public void call(final Subscriber<? super CharSequence> subscriber) {
    checkUiThread();

    final TextWatcher watcher = new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!subscriber.isUnsubscribed()) {
          subscriber.onNext(s);
        }
      }

      @Override public void afterTextChanged(Editable s) {
      }
    };
    view.addTextChangedListener(watcher);

    subscriber.add(new MainThreadSubscription() {
      @Override protected void onUnsubscribe() {
        view.removeTextChangedListener(watcher);
      }
    });

    // Emit initial value.
    subscriber.onNext(view.getText());
  }
}
```

## Using RxBinding with CompositeDisposable
```java
private CompositeDisposable mCompositeDisposable;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mCompositeDisposable = new CompositeDisposable();
    final TextView textView = findViewById(R.id.text_view);
    final EditText editText = findViewById(R.id.edit_text);
    final Button button = findViewById(R.id.button);
    final SeekBar seekBar = findViewById(R.id.seek_bar);

    Disposable editDisposable =
            RxTextView.textChanges(editText).subscribe(new Consumer<CharSequence>() {
        @Override
        public void accept(CharSequence charSequence) throws Exception {
            textView.setText(charSequence);
        }
    });
    mCompositeDisposable.add(editDisposable);

    Observable<Object> observable = RxView.clicks(button).share();
    Disposable buttonDisposable = observable.subscribe(new Consumer<Object>() {
        @Override
        public void accept(Object aVoid) throws Exception {
            textView.setText(button.getText());
        }
    });
    mCompositeDisposable.add(buttonDisposable);

    Disposable seekBarDisposable = RxSeekBar.changes(seekBar).subscribe(new Consumer<Integer>() {
        @Override
        public void accept(Integer integer) throws Exception {
            textView.setText(String.valueOf(integer));
        }
    });
    mCompositeDisposable.add(seekBarDisposable);
}

// or dispose in onDestory()
@Override
protected void onStop() {
    super.onStop();
    if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
        mCompositeDisposable.dispose();
    }
}
```
