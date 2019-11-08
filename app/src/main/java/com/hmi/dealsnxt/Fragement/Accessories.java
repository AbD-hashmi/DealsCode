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
import com.hmi.dealsnxt.Adaptor.AllinoneAdaptor;
import com.hmi.dealsnxt.Adaptor.HotDealsAdaptor;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.HotDealsModel;
import com.hmi.dealsnxt.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accessories extends Fragment {
    private RecyclerView mRecyclerView;
    //  public static RecyclerView.Adapter mAdapter;
    public static ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;
    LinearLayoutManager linearLayoutManager;
    AllinoneAdaptor adapter;
    ImageView ivmoveup;
    public List<HotDealsModel> arrayList = new ArrayList<>();
    //    private RelativeLayout rvMain;
//    private HashMap<Integer, Boolean> isNew = new HashMap<>(0);
//    private HashMap<Integer, Boolean> isInnerImage = new HashMap<>(0);
//    List<HotDealsModel> listSharedUser = new ArrayList<HotDealsModel>();
//    public static boolean isNews = false;
    //   public static List<HotDealsModel> mContentItems = new ArrayList<HotDealsModel>();
    String offlineDealsJSON = "{\"status\":1,\"info\":[{\"deal_id\":\"1\",\"like\":\"15\",\"discount\":\"20\",\"deal_name\":\"CAFE COFFEDAY\",\"reminder_time\":\"1\",\"location\":\"Sector55,Gurgaon\",\"avail_time\":\"5pm\",\"actualprice\":\"500\",\"afterdiscountprice\":\"200\"},{\"deal_id\":\"1\",\"like\":\"15\",\"discount\":\"20\",\"deal_name\":\"CAFE COFFEDAY\",\"reminder_time\":\"1\",\"location\":\"Sector55,Gurgaon\",\"avail_time\":\"5pm\",\"actualprice\":\"500\",\"afterdiscountprice\":\"200\"},{\"deal_id\":\"1\",\"like\":\"15\",\"discount\":\"20\",\"deal_name\":\"CAFE COFFEDAY\",\"reminder_time\":\"1\",\"location\":\"Sector55,Gurgaon\",\"avail_time\":\"5pm\",\"actualprice\":\"500\",\"afterdiscountprice\":\"200\"},{\"deal_id\":\"1\",\"like\":\"15\",\"discount\":\"20\",\"deal_name\":\"CAFE COFFEDAY\",\"reminder_time\":\"1\",\"location\":\"Sector55,Gurgaon\",\"avail_time\":\"5pm\",\"actualprice\":\"500\",\"afterdiscountprice\":\"200\"},{\"deal_id\":\"1\",\"like\":\"15\",\"discount\":\"20\",\"deal_name\":\"CAFE COFFEDAY\",\"reminder_time\":\"1\",\"location\":\"Sector55,Gurgaon\",\"avail_time\":\"5pm\",\"actualprice\":\"500\",\"afterdiscountprice\":\"200\"}]}";
    public static int arrayLength = 0;
    TextView no_result;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotdeals, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
      /*  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);*/
        linearLayoutManager = new LinearLayoutManager(getActivity());
        swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swipeContainer);
        ivmoveup = (ImageView) view.findViewById(R.id.ivmoveup);
        no_result= (TextView) getView().findViewById(R.id.no_result);


        ivmoveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    layoutManager.scrollToPositionWithOffset(0, 0);
                    LandingNewActivity.collapsingToolbarLayout.setFitsSystemWindows(true);

                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) LandingNewActivity.htab_appbar.getLayoutParams();
                    AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                    if (behavior != null) {
                        behavior.setTopAndBottomOffset(0);
                        behavior.onNestedPreScroll(LandingNewActivity.htab_maincontent, LandingNewActivity.htab_appbar, null, 0, 1, new int[2]);
                    }
                } catch (Exception e) {
                }

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadOfflineDeals("");
        //loadOfflineDeals(offlineDealsJSON);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadOfflineDeals("PullToRefresh");
            }
        });

    }

    public void loadOfflineDeals(final String From) {
        // if (From.equals("PullToRefresh")) {

        String url = Constaints.CategoryDetailbyID;
        progressBar.setVisibility(View.VISIBLE);
        if (!From.equals("PullToRefresh")) {
            progressBar.setVisibility(View.VISIBLE);
        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jSONObject = new JSONObject(new String(response));
                    //   JSONObject jSONObject = new JSONObject(new String(""));

                    if (From.equals("PullToRefresh")) {
                        swipeContainer.setRefreshing(false);
                    }
                    arrayList.clear();
                    int Status = jSONObject.optInt("status");
                    String Path = Constaints.BaseUrl + jSONObject.optString("dealsCdnpath");
                    //       dealsModel.setDealsName(Constaints.BaseUrl + merchant.optString("dealsCdnpath"));
                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("info");
                        //  scheduleJSONArray = infoArray.toString();
                        ////  JSONArray scheduleArray = new JSONArray(scheduleJSONArray);
                        //  arrayLength = scheduleArray.length();
                        //   arrayList = new ArrayList<>(scheduleArray.length());
                        if (infoArray.length()==0)
                        {
                            no_result.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            no_result.setVisibility(View.GONE);
                        }

                        for (int i = 0; i < infoArray.length(); i++) {

                            JSONObject outlet = infoArray.getJSONObject(i);
                            JSONArray dealArray = outlet.optJSONArray("deals");
                            //  JSONObject dealArray = outlet.optJSONObject("deals");

                            JSONArray compaing_array = outlet.optJSONArray("compain");
                            if (infoArray.length() > 0) {
                                for (int j = 0; j < dealArray.length(); j++) {
                                    JSONObject data = dealArray.getJSONObject(j);
                                    HotDealsModel dealsModel = new HotDealsModel();
                                    dealsModel.setOutletid(outlet.optString("id"));
                                    dealsModel.setOutletName(outlet.optString("outletName"));
                                    dealsModel.setOutletAddress(outlet.optString("address"));
                                    dealsModel.setOutletstate(outlet.optString("state"));
                                    dealsModel.setOutletCity(outlet.optString("city"));
                                    dealsModel.setOutletzipcode(outlet.optString("zipcode"));
                                    dealsModel.setTndc(outlet.optString("termAndCondition"));
                                    dealsModel.setNumofOffers(outlet.optString("dealCount"));
                                    dealsModel.setOutletcontactperson(outlet.optString("contactPersonName"));
                                    dealsModel.setOutletcontactnumber(outlet.optString("contactNumber"));
                                    dealsModel.setOutletLatitude(outlet.optString("lat"));
                                    dealsModel.setOutletLongtitude(outlet.optString("lng"));
                                    dealsModel.setOutletdescription(outlet.optString("description"));

                                    dealsModel.setDealid(data.optInt("id"));
                                    dealsModel.setMerchantid(data.optString("user_id"));
                                    dealsModel.setDealTitle(data.optString("deal_title"));
                                    dealsModel.setActualPrice(data.optString("deal_price"));
                                    dealsModel.setAvailibiltyTime(data.optString("deal_available_from"));
                                    dealsModel.setReminderTime(data.optString("deal_available_to"));
                                    dealsModel.setAfterDiscountPrice(data.optString("deal_discount_price"));
                                    dealsModel.setDealdescription(data.optString("deal_description"));
                                    dealsModel.setType(0);
                                    // dealsModel.setout(data.optString("deal_discount_price"));
                                    dealsModel.setPercentage(data.optString("percentage"));
                                    dealsModel.setDealimage(Path + "/" + data.optString("deal_display_photo"));
                                    dealsModel.setLikes(data.optString("like"));
                                    dealsModel.setLikesCount(data.optString("totalLike"));


                                    arrayList.add(dealsModel);
                                }

                                /*for (int k=0;k<compaing_array.length();k++)
                                {

                                    try {
                                        JSONObject jsonObject=compaing_array.optJSONObject(k);
                                        HotDealsModel dealsModel = new HotDealsModel();
                                        dealsModel.setOutletid(outlet.optString("id"));
                                        dealsModel.setOutletName(outlet.optString("outletName"));
                                        dealsModel.setOutletAddress(outlet.optString("address"));
                                        dealsModel.setOutletstate(outlet.optString("state"));
                                        dealsModel.setOutletCity(outlet.optString("city"));
                                        dealsModel.setOutletzipcode(outlet.optString("zipcode"));
                                        dealsModel.setTndc(outlet.optString("termAndCondition"));
                                        dealsModel.setNumofOffers(outlet.optInt("dealCount"));
                                        dealsModel.setOutletcontactperson(outlet.optString("contactPersonName"));
                                        dealsModel.setOutletcontactnumber(outlet.optString("contactNumber"));
                                        dealsModel.setOutletLatitude(outlet.optString("lat"));
                                        dealsModel.setOutletLongtitude(outlet.optString("lng"));
                                        dealsModel.setOutletdescription(outlet.optString("description"));

                                        dealsModel.setDealid(jsonObject.optInt("id"));
                                        dealsModel.setMerchantid(jsonObject.optString("user_id"));
                                        dealsModel.setDealTitle(jsonObject.optString("deal_title"));
                                        dealsModel.setActualPrice(jsonObject.optString("deal_price"));
                                        dealsModel.setAvailibiltyTime(jsonObject.optString("deal_available_from"));
                                        dealsModel.setReminderTime(jsonObject.optString("deal_available_to"));
                                        dealsModel.setAfterDiscountPrice(jsonObject.optString("deal_discount_price"));
                                        dealsModel.setDealdescription(jsonObject.optString("deal_description"));
                                        dealsModel.setType(1);
                                        // dealsModel.setout(data.optString("deal_discount_price"));
                                        dealsModel.setPercentage(jsonObject.optString("percentage"));
                                        dealsModel.setDealimage("https://s3.amazonaws.com/uploads.hipchat.com/57414/4271595/vtinmlpUbyc9hKi/Banner_2.png");
                                        dealsModel.setLikes(jsonObject.optString("like"));
                                        dealsModel.setLikesCount(jsonObject.optString("totalLike"));

                                        if (i%2==1) {
                                            arrayList.add(dealsModel);
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
*/                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                        swipeContainer.setRefreshing(false);
                        no_result.setVisibility(View.VISIBLE);
                    }
                    String category_id="4";

                    adapter = new AllinoneAdaptor(arrayList, getActivity(), getActivity(),category_id);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                    swipeContainer.setRefreshing(false);
                    no_result.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                swipeContainer.setRefreshing(false);
                Log.e("error", "" + error);
                no_result.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("category_id", 4 + "");
                params.put("user_id", SessionManager.getUserID(getContext()));
                params.put("lat", SessionManager.getLatitude(getContext()));
                params.put("lng", SessionManager.getLongitude(getContext()));
                params.put("city_id", SessionManager.getCityid(getContext()));
                params.put("page", "0");
                System.out.println("data "+params);
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
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

      /*  } else {

            //   List<HotDealsModel> deallist = HotDealsModel.getAlldeals();
            //    arrayList = HotDealsModel.getAlldeals();
            arrayList.clear();
            adapter = new HotDealsAdaptor(arrayList, getActivity());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(adapter);

        }*/
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
                *//*        for (int i = 0; i < scheduleArray.length(); i++) {

                            HotDealsModel hotDealsModel = new HotDealsModel();
                            JSONObject dealJson = scheduleArray.getJSONObject(i);

                            hotDealsModel.setDealid(dealJson.optInt("deal_id"));
                            hotDealsModel.setLikes(dealJson.optInt("like"));
                            hotDealsModel.setDiscount(dealJson.optInt("discount"));
                            hotDealsModel.setDealsName(dealJson.optString("deal_name"));
                            hotDealsModel.setReminderTime(dealJson.optString("reminder_time"));
                            hotDealsModel.setLocation(dealJson.optString("location"));
                            hotDealsModel.setAvailibiltyTime(dealJson.optString("avail_time"));

                            hotDealsModel.setActualPrice(dealJson.optString("actualprice"));
                            hotDealsModel.setAfterDiscountPrice(dealJson.optString("afterdiscountprice"));

                            arrayList.add(i, hotDealsModel);
                        }*//*
                    } else {
                        Toast.makeText(getActivity(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                    }

                    HotDealsAdaptor adapter = new HotDealsAdaptor(arrayList, getActivity());
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(adapter);

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
              //  String apiKey = "XXXXX";
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
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }*/



 /* public void loadOfflineDeals(String offlineDealsJSON) {
        try {
            JSONObject jSONObject = new JSONObject(offlineDealsJSON);

            int Status = jSONObject.optInt("status");
            if (Status == 1) {
                JSONArray infoArray = jSONObject.getJSONArray("info");
                offlineDealsJSON = infoArray.toString();

                JSONArray scheduleArray = new JSONArray(offlineDealsJSON);
                arrayLength = scheduleArray.length();
                arrayList = new ArrayList<>(scheduleArray.length());
                for (int i = 0; i < scheduleArray.length(); i++) {

                    HotDealsModel hotDealsModel = new HotDealsModel();
                    JSONObject dealJson = scheduleArray.getJSONObject(i);

                    hotDealsModel.setDealid(dealJson.optInt("deal_id"));
                    hotDealsModel.setLikes(dealJson.optInt("like"));
                    hotDealsModel.setDiscount(dealJson.optInt("discount"));
                    hotDealsModel.setDealsName(dealJson.optString("deal_name"));
                    hotDealsModel.setReminderTime(dealJson.optString("reminder_time"));
                    hotDealsModel.setLocation(dealJson.optString("location"));
                    hotDealsModel.setAvailibiltyTime(dealJson.optString("avail_time"));
                    hotDealsModel.setActualPrice(dealJson.optString("actualprice"));
                    hotDealsModel.setAfterDiscountPrice(dealJson.optString("afterdiscountprice"));

                    arrayList.add(i, hotDealsModel);
                }

            } else {
                Toast.makeText(getActivity(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
            }

            HotDealsAdaptor adapter = new HotDealsAdaptor(arrayList, getActivity());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(adapter);

        } catch (Exception e) {
            Log.e("", e.getMessage());

        }

        progressBar.setVisibility(View.GONE);

    }
*/


}