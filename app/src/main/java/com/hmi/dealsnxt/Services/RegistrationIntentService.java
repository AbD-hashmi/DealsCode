/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hmi.dealsnxt.Services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String GCMNotificationID = instanceID.getToken(getString(R.string.GCMSenderID), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            SessionManager.setDeviceGCMNotificationID(getApplicationContext(), GCMNotificationID);
            Log.i(TAG, "GCM Registration Token: " + GCMNotificationID);

            // TODO: Implement this method to send any registration to your app's servers.
            UpdateDeviceToken(GCMNotificationID);
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete GCMNotificationID refresh", e);
        }

    }


    public void UpdateDeviceToken(final String GCMNotificationID) {

        if (!GCMNotificationID.equalsIgnoreCase("") && GCMNotificationID != null) {
            String url = Constaints.UpdateDeviceGCMID;

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        //{"status":1,"msg":"Successfully updated"}
                        JSONObject jSONObject = new JSONObject(new String(response));
                        Log.e("Response", response);
                    } catch (Exception e) {
                        Log.e("GCMUpdateStatus", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("device_imei", SessionManager.getDeviceIMEI(getApplicationContext()));
                    params.put("notification_id", GCMNotificationID);

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
        }
    }

}
