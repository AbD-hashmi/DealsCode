package com.hmi.dealsnxt.Services;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.hmi.dealsnxt.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class MyGcmListenerService extends GcmListenerService{

    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    String ScheduleName = "";
    String FacultyName = "";
    String ScheduleId = "";

    private static final String TAG = "MyGcmListenerService";
    JSONObject jSONObject;
    int GCMACTION = 0;
    JSONObject UserInfo;
    Intent notifActivityIntent;
    TaskStackBuilder stackBuilder;
    String NotificationMessage = "";
    NotificationCompat.Builder mBuilder;
    Bundle fromBundle = new Bundle();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        if (message != null) {
            try {
                //{"GCMACTION":"1","info":{"schedule_id":"12", "schedule_name":"Old and New Insulins","faculty_name":"Dr. Fonseka"}}
                //{"GCMACTION":"2","info":{"schedule_name":"Mumbai, faculty slide PDF's now available !","faculty_name":"Dr. Fonseka"}}
                jSONObject = new JSONObject(new String(message));

                GCMACTION = Integer.valueOf(jSONObject.optString("GCMACTION"));

                if (GCMACTION == 1 || GCMACTION == 2) {

                    UserInfo = jSONObject.getJSONObject("info");
                    ScheduleId = UserInfo.optString("schedule_id");
                    ScheduleName = UserInfo.optString("schedule_name");
                    FacultyName = UserInfo.optString("faculty_name");

                     NotificationMessage = ScheduleName;

                    fromBundle.putString("FROM", "NOTIFICATION");


                  //  sendNotification(from, UserInfo);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



   /* private void sendNotification(String title, JSONObject body) {
        Context context = getBaseContext();

        int color = getResources().getColor(R.color.notifBackgroundColor);

        if (GCMACTION == 1) {
            if (Integer.valueOf(body.optString("schedule_id")) == 11) {
                FacultyName = "All Faculty";

                notifActivityIntent = new Intent(getBaseContext(), OverallEvaluation.class);

                notifActivityIntent.putExtras(fromBundle);

                stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(OverallEvaluation.class);
                mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(getNotificationIcon()).setContentTitle("ASN HIGHLIGHTS 2017")
                        .setContentText("Please give overall program feedback")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Please give overall program feedback"));
            } else {
                notifActivityIntent = new Intent(getBaseContext(), FeedbackEvaluationform.class);

                ScheduleId = body.optString("schedule_id");
                ScheduleName = body.optString("schedule_name");
                FacultyName = body.optString("faculty_name");

                fromBundle.putString("ScheduleId", ScheduleId);
                fromBundle.putString("ScheduleName", ScheduleName);
                fromBundle.putString("FacultyName", FacultyName);

                notifActivityIntent.putExtras(fromBundle);

                stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(FeedbackEvaluationform.class);
                mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(getNotificationIcon()).setContentTitle("ASN HIGHLIGHTS 2017")
                        .setContentText(Html.fromHtml(ScheduleName).toString() + " has been completed, Please give your feedback.")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(ScheduleName).toString() + " has been completed, Please give your feedback."));
            }

        } else if (GCMACTION == 2) {
           notifActivityIntent = new Intent(getBaseContext(), FacultyslideActivity.class);

            ScheduleId = body.optString("schedule_id");
            ScheduleName = body.optString("schedule_name");
            FacultyName = body.optString("faculty_name");

            fromBundle.putString("ScheduleId", ScheduleId);
            fromBundle.putString("FacultySlideDescription", ScheduleName);
            fromBundle.putString("FacultySlideURL", FacultyName);

            notifActivityIntent.putExtras(fromBundle);

            stackBuilder = TaskStackBuilder.create(this);
           stackBuilder.addParentStack(FacultyslideActivity.class);
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(getNotificationIcon()).setContentTitle("ASN HIGHLIGHTS 2017")
                    .setContentText(Html.fromHtml(ScheduleName).toString());
        }

        mBuilder.setColor(color);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(notifActivityIntent);

        final int not_nu = generateRandom();

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(not_nu, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(notification);
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);
        mNotificationManager.notify(not_nu, mBuilder.build());
    }*/

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

    public int generateRandom() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}