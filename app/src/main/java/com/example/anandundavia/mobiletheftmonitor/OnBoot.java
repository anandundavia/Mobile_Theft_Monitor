package com.example.anandundavia.mobiletheftmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


public class OnBoot extends BroadcastReceiver
{
    String id;
    Context localcontext;
    @Override
    public void onReceive(final Context context, Intent intent)
    {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String currIccid = "#"+tMgr.getSimSerialNumber();
        Database localDB = new Database(context);
        String savedIccid = "#"+localDB.getIccId();
        Log.e("ICCID",currIccid);
        Log.e("ICCID", savedIccid);
        localcontext = context;
        if(!savedIccid.equals(currIccid))
        {
            Log.e("SEND","not same,mailing");
            id = localDB.getEmailId();
            AsyncTask.execute(new Mailer(localcontext,id));
        }
    }
}
