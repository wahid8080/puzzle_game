package com.bdfleet.puzzlegame.Retrofit;

import com.bdfleet.puzzlegame.Model.CallRacord;

import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitClientInstance {

    public static void DataPass(CallRacord data1, final ResponseCallBack<ResponseBody> callBack){

        final DataService dataService = ApiClient.getClient(ApiClient.BASE_URL).create(DataService.class);
        Call<ResponseBody> call = dataService.postData(data1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    callBack.onSuccess(response.body());
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
