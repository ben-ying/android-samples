package com.yjh.retrofit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.yjh.retrofit2.custom.CustomCall;
import com.yjh.retrofit2.custom.CustomCallAdapterFactory;
import com.yjh.retrofit2.custom.StringConverterFactory;
import com.yjh.retrofit2.model.CustomResponse;
import com.yjh.retrofit2.model.Event;
import com.yjh.retrofit2.model.ListResponseResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);
        mTextView.setMovementMethod(new ScrollingMovementMethod());
        String token = "1272dc0fe06c52383c7a9bdfef33255b940c195b";
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CustomCallAdapterFactory.create())
                .baseUrl("http://www.bensbabycare.com/webservice/")
                .build();
        Webservice webservice = retrofit.create(Webservice.class);
//        Call<ResponseBody> call = webservice.login("babycare",
//                MD5Utils.getMD5ofStr("md51988123456").toLowerCase());
//        Call<CustomResponse> call = webservice.getEvents(token, "1");
//        Call<ResponseBody> call = webservice.deleteEvent("22", token);
//        call.enqueue(new Callback<CustomResponse>() {
//            @Override
//            public void onResponse(Call<CustomResponse> call, Response<CustomResponse> response) {
//                try {
////                    mTextView.setText(new JSONObject(response.body().getResult()).toString(4));
//                    mTextView.setText(new JSONObject(response.body().getResult().toString()).toString(4));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CustomResponse> call, Throwable t) {
//                mTextView.setText(t.getLocalizedMessage());
//            }
//        });
        webservice.getEvents(token, "1")
//                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomResponse<ListResponseResult<List<Event>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "disposable: " + d.toString());
                    }

                    @Override
                    public void onNext(CustomResponse<ListResponseResult<List<Event>>> value) {
                        if (value.isSuccessful()) {
                            Log.d(TAG, "successful");
                        } else {
                            Log.d(TAG, "failed");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "complete");
                    }
                });

        Call<String> call = webservice.login("babycare",
                MD5Utils.getMD5ofStr("md51988123456").toLowerCase());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    mTextView.setText(new JSONObject(response.body()).toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mTextView.setText(t.getMessage());
            }
        });

//        CustomCall<String> customCall = webservice.getEventList(token, "1");
//        try {
//            String result = customCall.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("", "");
    }
}
