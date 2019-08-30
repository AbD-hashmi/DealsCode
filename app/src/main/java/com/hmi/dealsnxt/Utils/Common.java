package com.hmi.dealsnxt.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hmi.dealsnxt.R;

/**
 * Created by admin on 11-12-2017.
 */

public class Common {


   /* public static String LoginURl = newBaseUrl + "userLogin";
    public static String getWODetailForSE = newBaseUrl + "getWODetailForSE";
    public static String getWOListForSE = newBaseUrl + "getWOListforSE";
    public static String getmedialistforSE = newBaseUrl + "getMediaListforSE";*/

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void showMyToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean isConnectingToInternet(Context CurrentActivity) {
        Boolean Connected = false;
        ConnectivityManager connectivity = (ConnectivityManager) CurrentActivity.getApplicationContext().getSystemService(
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
            Connected = false;
        }
        return Connected;

    }

    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setCancelable(true);
        //progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
      //  progressDialog.setIndeterminateDrawable(context.getResources().getDrawable(R.anim.progress));
        progressDialog.setMessage("Please wait");
       // ViewGroup.LayoutParams hj=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       // progressDialog.addContentView(new ProgressBar(context), hj);
        return progressDialog;
    }

    public static void hideKeyboard(View view, Context context) {
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
