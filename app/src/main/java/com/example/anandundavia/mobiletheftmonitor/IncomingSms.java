package com.example.anandundavia.mobiletheftmonitor;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver
{
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent)
    {
        final Bundle bundle = intent.getExtras();
        try
        {
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    if (phoneNumber.length() == 13)
                        phoneNumber = phoneNumber.substring(3);

                    String ecn = MainActivity.localDB.getEmergencyContactNumber();
                    Log.e("ANAND", ecn + " " + phoneNumber + " " + message);
                    if (ecn.equals(phoneNumber) && message.equals("lock"))
                    {
                        MainActivity.mDevicePolicyManager.lockNow();
                    }
                }
            }

        } catch (Exception e)
        {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}