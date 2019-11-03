package com.hmi.dealsnxt.Activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hmi.dealsnxt.Adaptor.HotDealsAdaptor;
import com.hmi.dealsnxt.Adaptor.NearbyDealsAdaptor;
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

public class NearByActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;
    public List<HotDealsModel> arrayList = new ArrayList<>();
    NearbyDealsAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ImageView imBack= (ImageView) findViewById(R.id.imBack);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng test = new LatLng(Double.valueOf(SessionManager.getLatitude(NearByActivity.this))
                , Double.valueOf(SessionManager.getLongitude(NearByActivity.this)));

        //drawMarker(test,"I am here");
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        loadOfflineDeals("");
    }


    public void loadOfflineDeals(final String From) {
        // if (From.equals("PullToRefresh")) {

        String url = Constaints.DealList;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jSONObject = new JSONObject(new String(response));

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

                        for (int i = 0; i < infoArray.length(); i++) {
                            HotDealsModel dealsModel = new HotDealsModel();
                            JSONObject outlet = infoArray.getJSONObject(i);
                            JSONArray dealArray = outlet.optJSONArray("deals");
                            //  JSONObject dealArray = outlet.optJSONObject("deals");

                            if (dealArray.length() > 0) {
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
                                drawMarker(new LatLng(Double.parseDouble(outlet.optString("lat")), Double.parseDouble(outlet.optString("lng"))),outlet.optString("outletName"));


                                for (int j = 0; j < dealArray.length(); j++) {
                                    JSONObject data = dealArray.getJSONObject(j);
                                    dealsModel.setDealid(data.optInt("id"));
                                    dealsModel.setMerchantid(data.optString("user_id"));
                                    dealsModel.setDealTitle(data.optString("deal_title"));
                                    dealsModel.setActualPrice(data.optString("deal_price"));
                                    dealsModel.setAvailibiltyTime(data.optString("deal_available_from"));
                                    dealsModel.setReminderTime(data.optString("deal_available_to"));
                                    dealsModel.setAfterDiscountPrice(data.optString("deal_discount_price"));
                                    dealsModel.setDealdescription(data.optString("deal_description"));
                                    // dealsModel.setout(data.optString("deal_discount_price"));
                                    dealsModel.setPercentage(data.optString("percentage"));
                                    dealsModel.setDealimage(Path + "/" + data.optString("deal_display_photo"));
                                    dealsModel.setLikes(data.optString("like"));
                                    dealsModel.setLikesCount(data.optString("totalLike"));

                                /*JSONArray OutletArray = data.optJSONArray("outlets");
                                for (int k = 0; k < OutletArray.length(); k++) {
                                    JSONObject outletobj = OutletArray.getJSONObject(k);
                                    dealsModel.setOutletName(outletobj.optString("name"));
                                    dealsModel.setLocation((outletobj.optString("city")));              }*/
                                }
                                //   HotDealsModel.saveInTx(dealsModel);
                                arrayList.add(dealsModel);
                            }
                        }
                    } else {

                    }
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    adapter = new NearbyDealsAdaptor(arrayList, NearByActivity.this, NearByActivity.this);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(adapter);



                } catch (Exception e) {
                    Log.e("", e.getMessage());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("user_id", SessionManager.getUserID(getApplicationContext()));
              /*  params.put("lat", "" + 18.1608226);
                params.put("lng", "" + 72.99661590000005);*/
                params.put("lat", SessionManager.getLatitude(getApplicationContext()));
                params.put("lng", SessionManager.getLongitude(getApplicationContext()));
                params.put("city_id","");
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

    private void drawMarker(LatLng point,String name){
// Creating an instance of MarkerOptions


        mMap.addMarker(new MarkerOptions().position(point).title(name));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(test));

        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, zoomLevel));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
    }

}
