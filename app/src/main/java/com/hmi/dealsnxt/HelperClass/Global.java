package com.hmi.dealsnxt.HelperClass;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Global {
    public final static int CAMERA_REQUEST_CODE = 2222;
    public final static int MemCard_REQUEST_CODE = 222;
    public final static int Zingbarcode_REQUEST_CODE = 786;
    public final static int CropImage_REQUEST_CODE = 209;
    public static String NoReadingError = "No Reading Available";
    public static NotificationManager mNotificationManager;

    public static boolean isInternetAvail(Activity CurrentActivity) {
        Boolean Connected = false;
        ConnectivityManager connectivity = (ConnectivityManager) CurrentActivity
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.e("Network is: ", "Connected");
                        Connected = true;
                    } else {
                    }
        } else {
            Log.e("Network is: ", "Not Connected");

            Toast.makeText(CurrentActivity.getApplicationContext(),
                    "Please Check Your  internet connection", Toast.LENGTH_LONG)
                    .show();
            Connected = false;
        }
        return Connected;
    }

    public static boolean isConnectedToInternetForServive(Context context) {
        Boolean Connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }
    public static String BitmapTobase64(Bitmap photo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }


}
