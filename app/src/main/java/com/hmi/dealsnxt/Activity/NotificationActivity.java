package com.hmi.dealsnxt.Activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
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
import com.hmi.dealsnxt.Model.CityModel;
import com.hmi.dealsnxt.Model.TimeModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.MultiSelectAdapter;
import com.hmi.dealsnxt.Utils.TimeselsectionAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NotificationActivity extends AppCompatActivity {
    TextView tvdetectlocation, tvpricerange;
    SeekBar seekbar;
    Switch switch2, switch3, switch4, switch5;
    TextView ivfiltertype1value, ivfiltertype2value, ivfiltertype3value, ivfiltertype4value;
    RecyclerView rvlist;
    String progressChangedValue = "";
    String offval = "";
    LinearLayout LLfilter1, LLfilter2, LLfilter3, LLfilter4;
    //   MultiSlider multiSlider1;
    TextView tvpricerangend, tvpricerangstart;
    ProgressBar progressBar;
    ArrayList<TimeModel> timearray_list = new ArrayList<>();
    ArrayList<TimeModel> user_list = new ArrayList<>();
    ArrayList<TimeModel> multiselect_list = new ArrayList<>();
    public static TimeselsectionAdapter timeSelectAdapter;
    public MultiSelectAdapter multiSelectAdapterlocal;
    String response = "{\"status\": 1,\"message\": \"Your Date list \",\"info\": [{\"id\": 1,\"start_time\": \"12:00\",\"end_time\": \"02:00\"},{\"id\": 2,\"start_time\": \"02:00\",\"end_time\": \"04:00\"},{\"id\": 3,\"start_time\":\"04:00\",\n" +
            "\"end_time\": \"06:00\"},{\"id\": 4,\"start_time\": \"06:00\",\"end_time\": \"08:00\"},{\"id\": 5,\"start_time\": \"08:00\",\"end_time\": \"10:00\"},{\"id\": 6,\"start_time\": \"10:00\",\"end_time\": \"12:00\"}]}";

    RelativeLayout Rldata;
    TextView tvselectodel, tvnotificationno;
    public LinearLayout newtoolbar, LLloc;
    public ImageView imBack, ivmoveup;
    public ImageView ivsearch;
    private SwipeRefreshLayout swipeContainer;
    public TextView tvusername, tvTitle, tvmore;
    public ImageView ivfilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notification);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rvlist = (RecyclerView) findViewById(R.id.recycler_view);
        tvselectodel = (TextView) findViewById(R.id.tvselectodel);
        tvnotificationno = (TextView) findViewById(R.id.tvnotificationno);
        Rldata = (RelativeLayout) findViewById(R.id.Rldata);
        newtoolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        imBack = (ImageView) newtoolbar.findViewById(R.id.imBack);
        tvTitle = (TextView) newtoolbar.findViewById(R.id.tvTitle);
        LLloc = (LinearLayout) newtoolbar.findViewById(R.id.LLloc);
        ivfilter = (ImageView) newtoolbar.findViewById(R.id.ivfilter);
        ivsearch = (ImageView) newtoolbar.findViewById(R.id.ivsearch);
        tvusername = (TextView) newtoolbar.findViewById(R.id.tvusername);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        LLloc.setVisibility(View.GONE);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivsearch.setVisibility(View.GONE);
        tvusername.setVisibility(View.GONE);
        tvTitle.setText("Notifications");
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

      /*  seekbar = (SeekBar) findViewById(R.id.seekbar);
        ivsearch = (SearchView) findViewById(R.id.ivsearch);
        tvdetectlocation = (TextView) findViewById(R.id.tvdetectlocation);
        time_recycler_view = (RecyclerView) findViewById(R.id.time_recycler_view);
        tvpricerangstart = (TextView) findViewById(R.id.tvpricerangstart);
        tvpricerangend = (TextView) findViewById(R.id.tvpricerangend);
        switch2 = (Switch) findViewById(R.id.switch2);
        switch3 = (Switch) findViewById(R.id.switch3);
        switch4 = (Switch) findViewById(R.id.switch4);
        switch5 = (Switch) findViewById(R.id.switch5);
        ivfiltertype1value = (TextView) findViewById(R.id.tvfiltertype1value);
        ivfiltertype2value = (TextView) findViewById(R.id.tvfiltertype2value);
        ivfiltertype3value = (TextView) findViewById(R.id.tvfiltertype3value);
        ivfiltertype4value = (TextView) findViewById(R.id.tvfiltertype4value);
        LLfilter1 = (LinearLayout) findViewById(R.id.LLfilter1);
        LLfilter2 = (LinearLayout) findViewById(R.id.LLfilter2);
        LLfilter3 = (LinearLayout) findViewById(R.id.LLfilter3);
        LLfilter4 = (LinearLayout) findViewById(R.id.LLfilter4);
        //    multiSlider1 = (MultiSlider) findViewById(R.id.range_slider1);
        tvpricerangstart = (TextView) findViewById(R.id.tvpricerangstart);
        tvpricerangend = (TextView) findViewById(R.id.tvpricerangend);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //   SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
      *//*  seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = String.valueOf(progress);
                //   tvpricerange.setText(seekbar.getMax() + "" + "-" + "" + progressChangedValue);
                tvpricerangstart.setText(0 + "" + "-" + "" + progressChangedValue);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                tvpricerangstart.setText(0 + "" + "-" + "" + progressChangedValue);
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //  Toast.makeText(getActivity(), "Seek bar progress is :" + progressChangedValue, Toast.LENGTH_SHORT).show();
                //   tvProbability.setText(progressChangedValue + "");
            }
        });*//*

        if (switch2.isChecked()) {
            offval = "20%";
        }
        if (switch3.isChecked()) {
            offval = "30%";
        }
        if (switch4.isChecked()) {
            offval = "40%";
        }
        if (switch5.isChecked()) {
            offval = "50%";
        }
       // loadtimeschedule(response);*/


    }


   /* public void loadtimeschedule(String response) {

     *//*   String url = Constaints.getCity;
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*//*
        try {
            Log.e("profile update resp", response);
            JSONObject jSONObject = new JSONObject(response);
            // JSONObject jSONObject = new JSONObject("{\"status\": 1,\"cdnpath\": \"uploads/usersdp/\",\"message\": \"Your account has been updated successfully !\",\"info\": {\"id\": \"4\",\"user_type\": \"Doctor\",\"user_name\": \"Dr. Richard\",\"account_key\": \"\", \"doctor_reference_key\": \"2\",\"user_mobile\": \"9999999999\",\"user_dp\": \"https://s3.amazonaws.com/uploads.hipchat.com/57414/3406939/ZOVhui1QUme3G5l/c1f3.png\",\"user_wallet_points\": \"0\",\"about_us\": \"Dr.Richard, senior consultant Pediatrics and Neonatology. Dr. Mangala Pawar personally sees all pediatric cases and has a good team of super specialties for patients with complex diseases. Dr. Mangala Pawar is a senior consultant Paediatrics and Neonatology at Fortis Memorial Research Institute, Gurgaon (FMRI). Dr. Mangala Pawar is a senior consultant at leading Hospitals in Washington DC and Maryland, USA. She has also practiced as a senior consultant at Apollo Hospital, Chennai.\",\"qualification\": \"MBBS , MD\",\"work_area\": \"Paediatrics\",\"pin\": \"110001\",\"status\": \"0\",\"created_at\": \"2017-04-21 12:11:11\",\"updated_at\": \"2017-05-03 10:49:41\",\"in_notifications\": \"0\",\"is_confirmed\": \"1\",\"is_deleted\": \"1\",\"user_email\":\"dr_rechard@gmail.com\",\"address1\": \"Gurgaon Sector 50, Gurgaon\",\"address2\": \"The Close South ,Tower 12, Flat 801, Landmark : Near Unitech Business Zone, Gurgaon\",\"kids\":\"2\"}}");
            String Status = jSONObject.optString("status");
            //    user_list.clear();
            String msg = jSONObject.optString("message");
            if (Integer.parseInt(Status) == 1) {
                JSONArray jsonArray = jSONObject.optJSONArray("info");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    TimeModel timeModel = new TimeModel(jsonObject.optString("start_time"), jsonObject.optString("end_time"), jsonObject.optString("id"));
                    timearray_list.add(timeModel);
                }
                timeSelectAdapter = new TimeselsectionAdapter(NotificationActivity.this, timearray_list, multiselect_list, NotificationActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                time_recycler_view.setNestedScrollingEnabled(false);
                time_recycler_view.setLayoutManager(gridLayoutManager);
                time_recycler_view.setItemAnimator(new DefaultItemAnimator());
                int spanCount = 2; // 3 columns
                int spacing = 0; // 50px
                boolean includeEdge = false;
                time_recycler_view.addItemDecoration(new NotificationActivity.GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                time_recycler_view.setAdapter(timeSelectAdapter);
            } else {
                Toast.makeText(getApplicationContext(), jSONObject.optString("message").toString(), Toast.LENGTH_SHORT).show();
            }

          *//*  timeSelectAdapter = new TimeselsectionAdapter(NotificationActivity.this, user_list, multiselect_list, NotificationActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            time_recycler_view.setNestedScrollingEnabled(false);
            time_recycler_view.setLayoutManager(gridLayoutManager);
            time_recycler_view.setItemAnimator(new DefaultItemAnimator());
            int spanCount = 2; // 3 columns
            int spacing = 0; // 50px
            boolean includeEdge = false;
            time_recycler_view.addItemDecoration(new NotificationActivity.GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            time_recycler_view.setAdapter(timeSelectAdapter);*//*
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
        progressBar.setVisibility(View.GONE);
               *//* progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");

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
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*//*
    }*/



    /*public void loadtimeschedule() {
      *//*  String name[] = {"Gokul", "Rajesh", "Ranjith", "Madhu", "Ameer", "Sonaal", "Gokul", "Rajesh", "Ranjith", "Madhu", "Ameer", "Sonaal"};
        String posting[] = {"Manager", "HR", "Android Developer", "iOS Developer", "Team Leader", "Designer", "Manager", "HR", "Android Developer", "iOS Developer", "Team Leader", "Designer"};
        for (int i = 0; i < name.length; i++) {
            CityModel mSample = new CityModel(name[i], posting[i], "", "");
            // user_list.add(mSample);
        }*//*

        String url = Constaints.getCity;
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("profile update resp", response);
                    JSONObject jSONObject = new JSONObject(response);
                    // JSONObject jSONObject = new JSONObject("{\"status\": 1,\"cdnpath\": \"uploads/usersdp/\",\"message\": \"Your account has been updated successfully !\",\"info\": {\"id\": \"4\",\"user_type\": \"Doctor\",\"user_name\": \"Dr. Richard\",\"account_key\": \"\", \"doctor_reference_key\": \"2\",\"user_mobile\": \"9999999999\",\"user_dp\": \"https://s3.amazonaws.com/uploads.hipchat.com/57414/3406939/ZOVhui1QUme3G5l/c1f3.png\",\"user_wallet_points\": \"0\",\"about_us\": \"Dr.Richard, senior consultant Pediatrics and Neonatology. Dr. Mangala Pawar personally sees all pediatric cases and has a good team of super specialties for patients with complex diseases. Dr. Mangala Pawar is a senior consultant Paediatrics and Neonatology at Fortis Memorial Research Institute, Gurgaon (FMRI). Dr. Mangala Pawar is a senior consultant at leading Hospitals in Washington DC and Maryland, USA. She has also practiced as a senior consultant at Apollo Hospital, Chennai.\",\"qualification\": \"MBBS , MD\",\"work_area\": \"Paediatrics\",\"pin\": \"110001\",\"status\": \"0\",\"created_at\": \"2017-04-21 12:11:11\",\"updated_at\": \"2017-05-03 10:49:41\",\"in_notifications\": \"0\",\"is_confirmed\": \"1\",\"is_deleted\": \"1\",\"user_email\":\"dr_rechard@gmail.com\",\"address1\": \"Gurgaon Sector 50, Gurgaon\",\"address2\": \"The Close South ,Tower 12, Flat 801, Landmark : Near Unitech Business Zone, Gurgaon\",\"kids\":\"2\"}}");
                    String Status = jSONObject.optString("status");
                    //    user_list.clear();
                    String msg = jSONObject.optString("message");
                    if (Integer.parseInt(Status) == 1) {
                        JSONArray jsonArray = jSONObject.optJSONArray("info");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            TimeModel timeModel = new TimeModel(jsonObject.optString("start_time"), jsonObject.optString("end_time"), jsonObject.optString("id"));
                            timearray_list.add(timeModel);
                        }
                        timeSelectAdapter = new TimeselsectionAdapter(NotificationActivity.this, user_list, multiselect_list, NotificationActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        time_recycler_view.setNestedScrollingEnabled(false);
                        time_recycler_view.setLayoutManager(gridLayoutManager);
                        time_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        int spanCount = 2; // 3 columns
                        int spacing = 0; // 50px
                        boolean includeEdge = false;
                        time_recycler_view.addItemDecoration(new NotificationActivity.GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        time_recycler_view.setAdapter(timeSelectAdapter);
                    } else {

                        Toast.makeText(getApplicationContext(), jSONObject.optString("message").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");

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
    }*/

    class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
