package com.hmi.dealsnxt.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GiftThankyou extends AppCompatActivity {

    String message,mobile,userid,tras_id,orderId;
    TextView sendMessage,tvtransaction,tvcancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_thankyou);

        Bundle bundle=getIntent().getExtras();
        orderId= bundle.getString("order_id");
        mobile = bundle.getString("gifteeMobile");
        message = bundle.getString("message");
        tras_id = bundle.getString("trans_id");
        userid= SessionManager.getUserID(getApplicationContext());

        sendMessage=(TextView) findViewById(R.id.tvSendsms);
        tvcancel=(TextView) findViewById(R.id.tvCancel);
        tvtransaction=(TextView) findViewById(R.id.tvtransaction);
        tvtransaction.setText(tras_id);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCall();
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.setType("vnd.android-dir/mms-sms");
                sendIntent.putExtra("address", mobile);
                sendIntent.putExtra("sms_body", message);
                startActivity(sendIntent);
            }
        });
        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(GiftThankyou.this, OrderActivity.class));
            }
        });
    }
    public void apiCall(){
        String url = Constaints.GiftSms;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("transactions_no", tras_id);
                params.put("order_id", orderId);
                params.put("user_id",SessionManager.getUserID(getApplicationContext()));
               // params.put("status", "1");

                System.out.println("server "+params);

                return params;
            }

           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }*/
        };
        int socketTimeout = 30000;
        Volley.newRequestQueue(
                getApplicationContext()).
                add(request);
        request.setRetryPolicy(new
                DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
