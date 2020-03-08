package com.bdfleet.puzzlegame.Retrofit;

public interface ResponseCallBack<T> {
    void onSuccess(T data);
    void onResponse(Throwable th);
    void onError(Throwable th);
}
