package com.bdfleet.puzzlegame.Retrofit;

import android.util.Log;

import com.bdfleet.puzzlegame.Model.CallRacord;
import com.google.gson.JsonObject;

import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitClientInstance {

    public static void DataPass(CallRacord data1, final ResponseCallBack<ResponseBody> callBack){

        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("from",data1.getFrom());
        jsonObject.addProperty("to",data1.getTo());
        jsonObject.addProperty("call_type",data1.getCall_type());
        jsonObject.addProperty("start_at",data1.getStart_at());
        jsonObject.addProperty("end_at",data1.getEnd_at());


        final DataService dataService = ApiClient.getClient(ApiClient.BASE_URL).create(DataService.class);
        Call<ResponseBody> call = dataService.postData(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    callBack.onSuccess(response.body());
                    Log.d("JsonObject", String.valueOf(jsonObject.get("from")));
                } else {
                    callBack.onResponse(new Throwable(response.code() + ""));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof SocketTimeoutException){
                    callBack.onError(new Throwable("Time out. Please try again."));
                } else {
                    callBack.onError(new Throwable(t.getLocalizedMessage()));
                }
            }
        });

    }
}
