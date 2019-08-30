package com.hmi.dealsnxt.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Kuldeep on 14-03-2016.
 */
public class OTPInboxMessageReceiver extends BroadcastReceiver {
    public static String OTP = null;
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    /*Dear Customer, Your one time password (OTP) is <1234>. Pls enter the OTP to proceed.
                    Thank you,
                    Team StarStar*/


                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();         //SSAOTP
                    String senderNum = phoneNumber;
                    if (senderNum.equals("VM-BANTER") || senderNum.contains("-BANTER")) {
                        OTP = currentMessage.getDisplayMessageBody();
                        OTP = OTP.substring(47, 52);
                        Intent i2 = new Intent("com.starstar.dial.OTP_MESSAGE");
                        i2.putExtra("OTP", OTP);
                        context.sendBroadcast(i2);
                        if (OTP.matches("^[0-9]+$")) {
                            Intent i1 = new Intent("com.starstar.dial.OTP_MESSAGE");
                            i1.putExtra("OTP", OTP);
                            context.sendBroadcast(i1);
                        } else {
                        }
                        Log.i("SmsReceiver", "senderNum: " + senderNum + "; OTP: " + OTP);
                    }

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }
}