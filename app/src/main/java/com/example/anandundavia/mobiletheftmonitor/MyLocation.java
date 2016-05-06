package com.example.anandundavia.mobiletheftmonitor;


import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import java.security.Security;

/**
 * Created by Anand  Undavia on 3/29/2016.
 */
public class MyLocation
{

    LocationManager lm;
    LocationListener ll = new MyListener();
    Location location;

    MyLocation(Context context)
    {
        try
        {

            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria locationCritera = new Criteria();
            String providerName = lm.getBestProvider(locationCritera, true);
            if (providerName != null)
                location = lm.getLastKnownLocation(providerName);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20, 1, ll);
        } catch (SecurityException e)
        {
            Log.e("LOCATION", e.toString());
        }
    }

    class MyListener implements LocationListener
    {


        @Override
        public void onLocationChanged(android.location.Location loc)
        {
            location = loc;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

        @Override
        public void onProviderEnabled(String provider)
        {

        }

        @Override
        public void onProviderDisabled(String provider)
        {

        }
    }

    public String getLong()
    {
        return location == null ? "":Double.toString(location.getLongitude());
    }

    public String getLat()
    {
        return location == null ? "":Double.toString(location.getLongitude());
    }
}