package com.hmi.dealsnxt.Services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

/**
 * Created by Kuldeep
 */
public class LocationUpdate extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    /**
     * indicates how to behave if the service is killed
     */
    int mStartMode;

    /**
     * interface for clients that bind
     */
    IBinder mBinder;

    /**
     * indicates whether onRebind should be used
     */
    boolean mAllowRebind;

    private static RequestQueue mRequestQueue;
    Timer timer = new Timer();
    private final Handler handler = new Handler();
    Intent intent;
    Boolean b = false;

    private HashMap<Integer, Boolean> isNew = new HashMap<>(0);
    private HashMap<Integer, Boolean> isInnerImage = new HashMap<>(0);
    private GoogleApiClient googleApiClient;

    Location locationUp;
    LocationRequest mLocationRequest = new LocationRequest();
    boolean requestingLocationUpdate = true;
    String location_send;
    String late, longti;

    @Override
    public void onCreate() {
        //Log.d("syn service called", "yes");

    }

    /**
     * The service is starting, due to a call to startService()
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //    handler.removeCallbacks(sendUpdatesToUI);
        // handler.postDelayed(sendUpdatesToUI, 100000); // 1000 = 1 Sec

        return mStartMode;
    }

    private Date dateCompareOne;
    private Date dateCompareTwo;
  /*  private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            //Log.d("inside run", "yes");
            if (googleApiClient != null) {
                googleApiClient.connect();
            }
            //api her
            if (ApplicationGlobles.isConnectedToInternetForServive(getApplicationContext())) {
                String url = Constants.LocationUpdate;

                StringRequest request = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.e("respoce", "" + response);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        //Log.d("user name", loginuser);
                        //base 64
                        Map<String, String> params = new HashMap<String, String>();
                        String apiKey = DecodeData.getConfigValuesecret(getApplicationContext(), "secret_key");
                        params.put("secret_key", StaticValue.md5(apiKey));
                        String ver_key = DecodeData.getConfigValueversion(getApplicationContext(), "version_key");
                        params.put("version_key", StaticValue.md5(ver_key));
                        params.put("device_os", Constants.DEVICE_OS);
                        params.put("device_token", SessionManager.getDeviceIMEI(getApplicationContext()));
                        params.put("registration_id", SessionManager.getUserId(getApplicationContext()));
                        params.put("user_email_id", SessionManager.getUserEmail(getApplicationContext()));
                        *//*Date date = new Date(System.currentTimeMillis());
                        params.put("date", String.valueOf(date.getTime()));*//*
                        params.put("latitude", late);
                        params.put("longitude", longti);
                        params.put("location", location_send);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }

                };


                int socketTimeout = 30000;

                Volley.newRequestQueue(getApplicationContext()).add(request);
                request.setRetryPolicy(new DefaultRetryPolicy(
                        socketTimeout,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            } else {
                //  Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }


            handler.postDelayed(this, 10000); // 1000 = 1 Sec
        }
    };*/

    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Called when all clients have unbound with unbindService()
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /**
     * Called when a client is binding to the service with bindService()
     */
    @Override
    public void onRebind(Intent intent) {

    }

    /**
     * Called when The service is no longer used and is being destroyed
     */
    @Override
    public void onDestroy() {

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            try {
                double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
                String units = "imperial";
                Log.e("Lat", lat + "long" + lon);
                late = "" + lat;
                longti = "" + lon;
                location_send = convert(lat, lon);
                //    Log.d(late,"Lati");
                //  Log.d(longti,"Longti");

            } catch (Exception e) {
            }
        }

        if (requestingLocationUpdate) {
            startLocationUpdate();//trying to get the update location
        }


    }

    @Override
    public void onConnectionSuspended(int i) {


    }

    private void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            double lat = location.getLatitude(), lon = location.getLongitude();
            String units = "imperial";
            Log.e("Lat", lat + "long" + lon);
            late = "" + lat;
            longti = "" + lon;
            location_send = convert(lat, lon);
        } catch (Exception e) {

        }
    }


    private String convert(double latitude, double longitude) {
        StringBuilder builder = new StringBuilder();
        String latitudeDegrees = Location.convert(Math.abs(latitude), Location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudeDegrees.split(":");
        builder.append(latitudeSplit[0]);
        builder.append("°");
        builder.append(latitudeSplit[1]);
        builder.append("'");
        builder.append(latitudeSplit[2]);
        builder.append("\"");

        builder.append(" ");
        if (latitude < 0) {
            builder.append("S ");
        } else {
            builder.append("N ");
        }

        String longitudeDegrees = Location.convert(Math.abs(longitude), Location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("°");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(longitudeSplit[2]);
        builder.append("\"");
        if (longitude < 0) {
            builder.append("W ");
        } else {
            builder.append("E ");
        }
        return builder.toString();
    }
}
