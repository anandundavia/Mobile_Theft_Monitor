package com.example.anandundavia.mobiletheftmonitor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Database
{
    private static final String DATABASE_NAME = "MasterDB";

    private static final String USER_TABLE = "User";

    private static final String USER_NAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String ICC_ID = "ICCID";
    private static final String EMERGENCY_CONTACT_NAME = "EmergencyContactName";
    private static final String EMERGENCY_CONTACT_NUMBER = "EmergencyContactNumber";
    private static final String EMERGENCY_EMAIL_ID = "EmergencyEmailId";

    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + USER_NAME + " TEXT, " + PASSWORD + " TEXT," + ICC_ID + " TEXT, " + EMERGENCY_CONTACT_NAME + " TEXT, " + EMERGENCY_CONTACT_NUMBER + " TEXT, " + EMERGENCY_EMAIL_ID + " TEXT );";

    private static final String GET_USER_COUNT = "SELECT * FROM " + USER_TABLE;

    Context localContext;

    SQLiteDatabase db = null;

    public Database(Context ct)
    {
        localContext = ct;
        db = localContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        db.execSQL(CREATE_USER_TABLE);
    }

    int getUserCount()
    {
        Cursor c = db.rawQuery(GET_USER_COUNT, null);
        return c.getCount();
    }

    public boolean createUser(String userName, String password, String eContactName, String eContactNum, String eEmail)
    {
        try
        {
            TelephonyManager tMgr = (TelephonyManager) localContext.getSystemService(Context.TELEPHONY_SERVICE);
            String iccid = tMgr.getSimSerialNumber();
            String query = "INSERT INTO " + USER_TABLE + " VALUES ('" + userName + "','" + password + "','" + iccid + "','" + eContactName + "','" + eContactNum + "','" + eEmail + "');";
            db.execSQL(query);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    public String getUserName()
    {
        String query = "SELECT " + USER_NAME + " FROM " + USER_TABLE;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c.getString(0);
    }

    public String getPassword()
    {
        String query = "SELECT " + PASSWORD + " FROM " + USER_TABLE;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c.getString(0);
    }

    public String getEmergencyContactNumber()
    {
        String query = "SELECT " + EMERGENCY_CONTACT_NUMBER + " FROM " + USER_TABLE + " ;";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c.getString(0);
    }

    public String getIccId()
    {
        String query = "SELECT " + ICC_ID + " FROM " + USER_TABLE + " ;";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c.getString(0);
    }

    public String getEmailId()
    {
        String query = "SELECT " + EMERGENCY_EMAIL_ID + " FROM " + USER_TABLE + " ;";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Log.e("SEND",c.getString(0));
        return c.getString(0);
    }
}
