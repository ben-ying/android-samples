package com.yjh.rxbinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxSeekBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.RxToolbar;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onStop() {
        super.onStop();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
