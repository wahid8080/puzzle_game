package com.bdfleet.puzzlegame.Retrofit;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DataService {

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("log/call")
    Call<ResponseBody> postData(@Body JsonObject callRacord);

}
