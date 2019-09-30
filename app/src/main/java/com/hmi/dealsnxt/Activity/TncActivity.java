package com.hmi.dealsnxt.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TncActivity extends AppCompatActivity {
    ImageView imBack, imlocation, ivfilter;
    TextView tvTitle, tvaddress, tvusername;
    ImageView ivsearch;
    LinearLayout LLloc;
    WebView webView;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnc);
        final LinearLayout toolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        LLloc = (LinearLayout) toolbar.findViewById(R.id.LLloc);
        imBack = (ImageView) toolbar.findViewById(R.id.imBack);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);

        LLloc = (LinearLayout) toolbar.findViewById(R.id.LLloc);
        imlocation = (ImageView) toolbar.findViewById(R.id.imlocation);
        tvaddress = (TextView) toolbar.findViewById(R.id.tvaddress);
        ivfilter = (ImageView) toolbar.findViewById(R.id.ivfilter);
        ivsearch = (ImageView) toolbar.findViewById(R.id.ivsearch);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        imlocation.setVisibility(View.GONE);
        tvaddress.setVisibility(View.GONE);
        ivfilter.setVisibility(View.GONE);
        ivsearch.setVisibility(View.GONE);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("Terms and Condition");
        tvusername= (TextView) toolbar.findViewById(R.id.tvusername);
        String test = SessionManager.getUserName(getApplicationContext()).toString();

        tvusername.setText(test);
        tvusername.setVisibility(View.INVISIBLE);

        webView = (WebView) findViewById(R.id.webView);
        progress_bar=(ProgressBar)findViewById(R.id.progress_bar);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        webView.getSettings().setJavaScriptEnabled(true);

        loaddata();
    }

    public void loaddata() {
        if (Global.isInternetAvail(this)) {
            String url = Constaints.AboutUs;
            progress_bar.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //   Log.e("profile update resp", response);
                                JSONObject jSONObject = new JSONObject(new String(response));
                                String Status = jSONObject.optString("status");
                                if (Integer.parseInt(Status) == 1) {

                                    JSONObject jSONinfo = jSONObject.optJSONObject("info");
                                   webView.loadData(jSONinfo.getString("body").toString(), "text/html", "UTF-8");

                                    //webView.loadData(jSONinfo.getString("body").toString(), "text/html", "UTF-8");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Unable to load data from Server", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("", e.getMessage());
                            }
                            progress_bar.setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progress_bar.setVisibility(View.GONE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("XAPIKEY", "XXXXX");
                    params.put("page_id", 2 + "");
                    params.put("user_id", SessionManager.getUserID(getApplicationContext()));
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
            Toast.makeText(getApplicationContext(), R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
        }
    }

}
