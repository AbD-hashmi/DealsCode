package com.hmi.dealsnxt.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.WAKE_LOCK;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashSplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    ProgressBar progressBar;
    private String DeviceIMEI;
    public static final int RequestPermissionCode = 888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        RelativeLayout Rl = (RelativeLayout) findViewById(R.id.Rl);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (CheckingPermissionIsEnabledOrNot()) {


            proceedAfterPermission();

        }


        else {


            RequestMultiplePermission();

        }

        printHashKey();
    }

    public void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.hmi.dealsnxt",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }






    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        //    Toast.makeText(getBaseContext(), "We got the Storage Permission", Toast.LENGTH_LONG).show();


        PackageManager pm = SplashSplashActivity.this.getPackageManager();
        TelephonyManager mngr = (TelephonyManager) SplashSplashActivity.this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DeviceIMEI = mngr.getDeviceId();
        } else {
            DeviceIMEI = mngr.getDeviceId();
        }

        String DeviceOSVersion = Build.VERSION.RELEASE; // e.g. myVersion := "1.6"

        SessionManager.setDeviceIMEI(getApplicationContext(), DeviceIMEI);
        //.      SessionManager.setDeviceOSVersion(getApplicationContext(), DeviceOSVersion);


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            PackageInfo packageInfo = pm.getPackageInfo("com.hmi.dealsnxt", PackageManager.GET_PERMISSIONS);

            String installTime = sdf.format(new Date(packageInfo.firstInstallTime));

            //  SessionManager.setAppInstallTime(getApplicationContext(), installTime);
            Log.d("InstallTime", "Installed: " + installTime.toString());

            String updateTime = sdf.format(new Date(packageInfo.lastUpdateTime));
            Log.d("UpdateTime", "Updated: " + updateTime.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        startActivity();


    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashSplashActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }







    private void startActivity() {

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {

                                          if (SessionManager.getIsTut(getApplicationContext()) == true) {

                                              if (SessionManager.getIsotp(getApplicationContext()) == true) {

                                                  if (SessionManager.getIssignup(getApplicationContext()) == true) {

                                                      if (SessionManager.getrefercode(getApplicationContext()) == true) {

                                                          if (SessionManager.getIsloc(getApplicationContext()) == true) {

                                                              Intent i = new Intent(SplashSplashActivity.this, LandingNewActivity.class);
                                                              startActivity(i);
                                                              finish();
                                                          } else {
                                                              Intent i = new Intent(SplashSplashActivity.this, LocationActivity.class);
                                                              startActivity(i);
                                                              finish();
                                                          }
                                                      } else {
                                                          Intent i = new Intent(SplashSplashActivity.this, ReferCodeActivity.class);
                                                          startActivity(i);
                                                          finish();
                                                      }

                                                  } else {
                                                      Intent i = new Intent(SplashSplashActivity.this, SignInActivity.class);
                                                      startActivity(i);
                                                      finish();
                                                  }
                                              } else {
                                                  Intent i = new Intent(SplashSplashActivity.this, OTPActivity.class);
                                                  startActivity(i);
                                                  finish();
                                              }


                                          } else {


                                              // DeviceInfoToServer();
                                              Intent i = new Intent(SplashSplashActivity.this, AftersplashActivity.class);
                                              startActivity(i);
                                              finish();

                                          }


                                      }
                                  }
                , SPLASH_TIME_OUT);


    }



    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WAKE_LOCK);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int SixthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int SeventhPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int EightPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        int CameraPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FifthPermissionResult == PackageManager.PERMISSION_GRANTED&&
                SixthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SeventhPermissionResult == PackageManager.PERMISSION_GRANTED &&
                EightPermissionResult == PackageManager.PERMISSION_GRANTED&&
                CameraPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(SplashSplashActivity.this, new String[]
                {
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        WAKE_LOCK,
                        READ_PHONE_STATE,
                        ACCESS_NETWORK_STATE,
                        ACCESS_FINE_LOCATION,
                        RECEIVE_SMS,
                        CAMERA

                }, RequestPermissionCode);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordAudioPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean SendSMSPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean GetAccountsPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && RecordAudioPermission && SendSMSPermission && GetAccountsPermission) {

                        proceedAfterPermission();

                    } else {
                        Toast.makeText(SplashSplashActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

}





