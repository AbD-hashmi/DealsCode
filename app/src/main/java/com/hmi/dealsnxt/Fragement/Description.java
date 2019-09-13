package com.hmi.dealsnxt.Fragement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hmi.dealsnxt.Activity.DetailNewActivity;
import com.hmi.dealsnxt.Activity.LandingNewActivity;
import com.hmi.dealsnxt.Adaptor.AllinoneAdaptor;
import com.hmi.dealsnxt.Adaptor.OutletsDeallistAdaptorNew;
import com.hmi.dealsnxt.Adaptor.RedeemorCancelOrder;
import com.hmi.dealsnxt.Adaptor.SimilairDealsAdaptor;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.DealImagesModel;
import com.hmi.dealsnxt.Model.HotDealsModel;
import com.hmi.dealsnxt.Model.ListModel;
import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Common;
import com.hmi.dealsnxt.Utils.Customutils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Description extends Fragment {
    TextView no_result;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.textview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        String s=getArguments().getString("terms", "none");
  //      no_result= (TextView) getView().findViewById(R.id.text);
    //    no_result.setText(s);


    }



/*    public void loadOfflineDeals() {
        String url = Constaints.DealList;
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    int Status = jSONObject.optInt("status");
                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("info");
                        offlineDealsJSON = infoArray.toString();

                        JSONArray scheduleArray = new JSONArray(offlineDealsJSON);
                        arrayLength = scheduleArray.length();
                        arrayList = new ArrayList<>(scheduleArray.length());
                */    @Override
    public void onResume() {
        super.onResume();

    }
}