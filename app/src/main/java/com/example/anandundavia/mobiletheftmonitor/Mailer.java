package com.example.anandundavia.mobiletheftmonitor;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Anand Undavia on 3/28/2016.
 */
public class Mailer implements Runnable
{
    String emailId;
    Context context;
    MyLocation location;
    TelephonyManager tMgrr;

    Mailer(Context c, String id)
    {
        context = c;
        emailId = id;
        location = new MyLocation(c);
        tMgrr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Log.e("MAIL", "Waking up");
                Log.e("SEND", "Run");
                String to = emailId;
                String from = "abundavia@gmail.com";

                Properties properties = System.getProperties();
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.user", "abundavia");
                properties.put("mail.smtp.password", "1055/c@mb@v@di");
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");
                Log.e("SEND", "Trying to auth");
                Session session = Session.getDefaultInstance(properties,
                        new javax.mail.Authenticator()
                        {
                            protected PasswordAuthentication getPasswordAuthentication()
                            {
                                return new PasswordAuthentication("abundavia@gmail.com", "1055/c@mb@v@di");
                            }
                        });
                Log.e("SEND", "Auth done");

                Log.e("SEND", "Preparing Message");
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("PHONE STATUS UPDATE");
                String messageText = "The current Phone number is : " + getPhoneNumber() + "\nThe current location in latitude,longitude is : " + getPoints();
                message.setText(messageText);
                Log.e("SEND", "Trying to send");

                Transport.send(message);
                Log.e("SEND", "SENT");
                Log.e("SEND", "Sleeping");


                Thread.sleep(15000);

            } catch (Exception mex)
            {
                mex.printStackTrace();
                Log.e("SEND", mex.toString());
            }
        }

    }

    public String getPhoneNumber()
    {
        return tMgrr.getLine1Number();
    }

    public String getPoints()
    {
        return location.getLat() + "," + location.getLong();
    }
}

