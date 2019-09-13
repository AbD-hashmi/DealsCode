package com.hmi.dealsnxt.Fragement;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hmi.dealsnxt.Activity.LandingNewActivity;
import com.hmi.dealsnxt.Activity.OrderActivity;
import com.hmi.dealsnxt.Adaptor.AllinoneAdaptor;
import com.hmi.dealsnxt.Adaptor.OrderBookedAdaptor;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.HotDealsModel;
import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpcommingOrderfragment extends Fragment {

    RecyclerView recycleVIew;
    public static ProgressBar progressBar;
    public List<OrderModel> arrayList = new ArrayList<>();
    public LinearLayout LLloc;
    public ImageView ivfilter;
    public TextView tvusername, tvTitle;
    public LinearLayout newtoolbar;
    public ImageView imBack, ivmoveup;
    public ImageView ivsearch;
    private SwipeRefreshLayout swipeContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upcomming_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        recycleVIew = (RecyclerView) view.findViewById(R.id.recycleVIew);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // loadOfflineDeals("");
        loadDetailist("");

        //loadOfflineDeals(offlineDealsJSON);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              //  loadOfflineDeals("PullToRefresh");
                loadDetailist("PullToRefresh");

            }
        });

    }


    public void loadDetailist(final String From) {
        String url = Constaints.OrderedListing;
        if (!From.equals("PullToRefresh")) {
            progressBar.setVisibility(View.VISIBLE);
        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    if (From.equals("PullToRefresh")) {
                        swipeContainer.setRefreshing(false);
                    }
                    arrayList.clear();
                    int Status = jSONObject.optInt("status");
                    String Path = Constaints.BaseUrl + jSONObject.optString("dealsCdnpath");
                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("info");
                        for (int i = 0; i < infoArray.length(); i++) {
                            JSONObject orderJson = infoArray.getJSONObject(i);
                            JSONArray dealorderdetail = orderJson.getJSONArray("orderDetails");
                            for (int j = 0; j < dealorderdetail.length(); j++) {
                                JSONObject dealobj = dealorderdetail.getJSONObject(j);
                                OrderModel orderModel = new OrderModel();
                                orderModel.setDealtransactionid(orderJson.optString("transactions_no"));
                                orderModel.setOutletorderstatus(orderJson.optString("status"));
                                orderModel.setDealpurchasedate(orderJson.optString("order_date"));
                                orderModel.setDealorderid(orderJson.optString("order_id"));
                                orderModel.setOutletaddress(orderJson.optString("city"));
                                orderModel.setDealid(dealobj.optString("id"));
                                orderModel.setDealdeatilid(dealobj.optString("deal_detail_id"));
                                orderModel.setDealQty(dealobj.optString("qty"));
                                orderModel.setDealtitle(dealobj.optString("title"));
                                orderModel.setDealpurchaseamount(dealobj.optString("price"));
                                orderModel.setDealstatus(dealobj.optString("status"));
                                orderModel.setDealimgurl(Path + "/" + dealobj.optString("dealImge"));
                                orderModel.setDealavailtime_to(dealobj.optString("timeTo"));
                                orderModel.setDealavailtime_from(dealobj.optString("timeFrom"));
                                arrayList.add(orderModel);
                            }

                        }
                    } else {
                        Toast.makeText(getContext(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                        swipeContainer.setRefreshing(false);
                    }
                    OrderBookedAdaptor adapter = new OrderBookedAdaptor(arrayList, getActivity(), getActivity(),UpcommingOrderfragment.this);
                    recycleVIew.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recycleVIew.setAdapter(adapter);

                } catch (Exception e) {
                    Log.e("", e.getMessage());
                    swipeContainer.setRefreshing(false);
                }
                progressBar.setVisibility(View.GONE);
                swipeContainer.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("user_id", SessionManager.getUserID(getContext()));
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
        Volley.newRequestQueue(getContext()).add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    @Override
    public void onResume() {
        super.onResume();
       // loadOfflineDeals("");
        loadDetailist("");

    }
}