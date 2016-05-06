package com.example.anandundavia.mobiletheftmonitor;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    public static Database localDB;

    public static final int ADMIN_INTENT = 15;
    public static DevicePolicyManager mDevicePolicyManager;
    public static ComponentName mComponentName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localDB = new Database(this);
        int userCount = localDB.getUserCount();

        mDevicePolicyManager = (DevicePolicyManager)getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, MyAdminReceiver.class);


        Fragment frag = null;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ftm = fm.beginTransaction();

        if(userCount == 0) {
            frag = new Register();
        } else {
            frag = new LogIn();
        }
        ftm.replace(R.id.container,frag);
        ftm.commit();
    }
}
