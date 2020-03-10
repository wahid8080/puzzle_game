package com.bdfleet.puzzlegame;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhonecallReceiver extends BroadcastReceiver {
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;
    DateFormat dateFormat;
    private static String saveDateTime;

    // because the passed incoming is only valid in ringing


    @Override
    public void onReceive(final Context context, Intent intent) {

        TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (ContextCompat.checkSelfPermission(context,Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        String myNumber = phoneMgr.getLine1Number();

        int state = 0;

        //We listen o two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            String outGoing = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");

            onCallStateChanged(context, state,outGoing, myNumber);

        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String incoming = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            onCallStateChanged(context, state,incoming, myNumber);
        }
    }

    //Derived classes should override these to respond to specific events of interest
    protected void onIncomingCallStarted(Context ctx, String number, String start) {
    }

    protected void onOutgoingCallStarted(Context ctx, String number, String start) {
    }

    protected void onIncomingCallEnded(Context ctx, String ownerNum, String number, String start, String end) {
    }

    protected void onOutgoingCallEnded(Context ctx, String ownerNum, String number, String start, String end) {
    }

    protected void onMissedCall(Context ctx, String number, String start) {

    }

    //Deals with actual events
    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    public void onCallStateChanged(Context context, int state,String number, String ownerNum) {
        if (lastState == state) {
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                callStartTime = new Date();
                onIncomingCallStarted(context, number, dateFormat.format(callStartTime));
                saveDateTime = dateFormat.format(callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false;
                    dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, number , dateFormat.format(callStartTime));
                    saveDateTime = dateFormat.format(callStartTime);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    callStartTime = new Date();
                    //Ring but no pickup-  a miss
                    onMissedCall(context, number, dateFormat.format(callStartTime));
                } else if (isIncoming) {
                    dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    callStartTime = new Date();
                    onIncomingCallEnded(context, ownerNum, number, saveDateTime, dateFormat.format(callStartTime));
                } else {
                    dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    callStartTime = new Date();
                    onOutgoingCallEnded(context, ownerNum, number, saveDateTime, dateFormat.format(callStartTime));
                }
                break;
        }
        lastState = state;
    }
}
