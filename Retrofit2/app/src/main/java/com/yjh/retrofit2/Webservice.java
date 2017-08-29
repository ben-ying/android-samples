package com.yjh.retrofit2;


import com.yjh.retrofit2.custom.CustomCall;
import com.yjh.retrofit2.model.CustomResponse;
import com.yjh.retrofit2.model.Event;
import com.yjh.retrofit2.model.ListResponseResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Webservice {
    @FormUrlEncoded
    @POST("user/login")
    Call<String> login(@Field("username") String username,
                     @Field("password") String password);

    @GET("events")
    Observable<CustomResponse<ListResponseResult<List<Event>>>> getEvents(@Query("token") String token,
                                                            @Query("user_id") String userId);

    @GET("events")
    CustomCall<String> getEventList(@Query("token") String token,
                         @Query("user_id") String userId);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "events/{eventId}", hasBody = true)
    Call<ResponseBody> deleteEvent(@Path("eventId") String eventId,
                                   @Field("token") String token);
}
