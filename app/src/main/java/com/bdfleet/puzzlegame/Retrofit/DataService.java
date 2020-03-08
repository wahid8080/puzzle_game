package com.bdfleet.puzzlegame.Retrofit;


import com.bdfleet.puzzlegame.Model.CallRacord;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DataService {

    @Headers({"Content-Type: application/json","Accept: application/json,text/plain,*/*"})
    @POST("api/log/call")
    Call<ResponseBody> postData(@Body CallRacord callRacord);

}
