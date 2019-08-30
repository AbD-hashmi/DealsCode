package com.hmi.dealsnxt.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.hmi.dealsnxt.Adaptor.OrderBookedDetailAdaptor;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.FAQModel;
import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FAQActivity extends AppCompatActivity {

    ImageView imBack, imlocation, ivfilter;
    TextView tvTitle, tvaddress, tvusername;
    ImageView ivsearch;
    LinearLayout LLloc;
    RecyclerView recycler_view;
    ProgressBar progressBar;
    public List<FAQModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
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
        tvTitle.setText("FAQ's");
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //  loadfaq();
    }

    public void loadfaq() {

        if (Global.isInternetAvail(FAQActivity.this)) {
            String url = Constaints.OrderedDetailbyDealid;

            progressBar.setVisibility(View.VISIBLE);

            StringRequest request = new StringRequest(Request.Method.POST,
                    url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jSONObject = new JSONObject(new String(response));
                                int Status = jSONObject.optInt("status");
                                if (Status == 1) {
                                    JSONArray infoArray = jSONObject.getJSONArray("otherOrderDetails");
                                    for (int i = 0; i < infoArray.length(); i++) {
                                        FAQModel faqModel = new FAQModel();
                                        JSONObject orderJson = infoArray.getJSONObject(i);
                                        /*    orderModel.setDealname(orderJson.optString("deal_title"));
                                        orderModel.setDealQty(orderJson.optString("qty"));
                                        orderModel.setDealstatus(orderJson.optString("status"));
                                        orderModel.setDealpurchaseamount(orderJson.optString("price"));
                                        arrayList.add(orderModel);*/
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                                }
                              /*  OrderBookedDetailAdaptor adapter = new OrderBookedDetailAdaptor(arrayList, FAQActivity.this);
                                recycler_view.setLayoutManager(new LinearLayoutManager(FAQActivity.this));
                                recycler_view.setAdapter(adapter);*/

                            } catch (Exception e) {
                                Log.e("", e.getMessage());
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FAQActivity.this, R.string.ConnectionErrorResponse, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("XAPIKEY", "XXXXX");
                    params.put("deal_id", OrderModel.getOrderModel().getDealid());
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
            Toast.makeText(FAQActivity.this, R.string.ConnectionErrorResponse, Toast.LENGTH_SHORT).show();
        }
    }
}
