package com.hmi.dealsnxt.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.hmi.dealsnxt.CustomControl.DataParser;
import com.hmi.dealsnxt.CustomControl.DownloadUrl;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.CityModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Common;
import com.hmi.dealsnxt.Utils.MultiSelectAdapter;
import com.hmi.dealsnxt.Utils.MultiSelectLocalityAdapter;
import com.hmi.dealsnxt.Utils.MultiSelectSearchCity;
import com.hmi.dealsnxt.Utils.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static co.kaush.core.util.CoreNullnessUtils.isNotNullOrEmpty;
import static java.lang.String.format;

public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    int REQUEST_CHECK_SETTINGS = 15000;
    final String TAG = "Location Activty";
    private LocationManager locationManager;
    boolean isGPS = false;
    boolean isNetwork = false;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest = new LocationRequest();
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private Location mLocation;
    public static String location_nameis = "";
    ScrollView scrollView;
    ResultCallback<LocationSettingsResult> mResultCallbackFromSettings = new ResultCallback<LocationSettingsResult>() {
        @Override
        public void onResult(LocationSettingsResult result) {
            final Status status = result.getStatus();
            //final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(
                                LocationActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Log.e(TAG, "Settings change unavailable. We have no way to fix the settings so we won't show the dialog.");
                    break;
            }
        }
    };

    RecyclerView recyclerView, recycler_view_loc, recycler_view_search;
    public MultiSelectAdapter multiSelectAdapter;
    public MultiSelectLocalityAdapter multiSelectAdapterlocal;
    public static MultiSelectSearchCity seacrhcityAdpater;
    boolean isMultiSelect = false;
    ArrayList<CityModel> user_list = new ArrayList<>();
    ArrayList<CityModel> multiselect_list = new ArrayList<>();
    ArrayList<CityModel> locality_list = new ArrayList<>();
    ArrayList<CityModel> search_list = new ArrayList<>();

    private ProgressBar progressBar;
    SearchView searchView;
    TextView tvTitle;
    ImageView my_loc;
    String myloc = "";
    public static ProgressDialog progressDialog;
    TextView pick_loc;
    private Disposable _disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view_loc = (RecyclerView) findViewById(R.id.recycler_view_loc);
        recycler_view_search = (RecyclerView) findViewById(R.id.recycler_view_search);
        pick_loc = (TextView) findViewById(R.id.pick_loc);
        SessionManager.setrefercode(LocationActivity.this, true);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        searchView = (SearchView) findViewById(R.id.iv_search);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        progressDialog = Common.getProgressDialog(LocationActivity.this);
        search_list.clear();
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        ImageView imBack = (ImageView) findViewById(R.id.imBack);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        PlaceAutocompleteFragment places = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        places.setFilter(typeFilter);
        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        if (locality_list.isEmpty()) {
            pick_loc.setVisibility(View.GONE);
        } else {
            pick_loc.setVisibility(View.VISIBLE);
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        getMylocation();
        my_loc = (ImageView) findViewById(R.id.my_loc);
        my_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFinishing())
                progressDialog.show();
                myloc = "yes";
                getMylocation();

            }
        });
        tvTitle.setText("Pick your location");
        ImageView im = (ImageView) searchView.findViewById(R.id.search_button);
        im.setImageResource(R.drawable.ic_search_black_24dp);
        SearchView.SearchAutoComplete search_src_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);

        ImageView search_close_btn = (ImageView) findViewById(R.id.search_close_btn);
        ImageView search_mag_icon = (ImageView) findViewById(R.id.search_mag_icon);

        search_close_btn.setImageResource(R.drawable.close_white);
        search_mag_icon.setImageResource(R.drawable.ic_search_black_24dp);
        search_src_text.setTextColor(Color.WHITE);
        search_src_text.setHintTextColor(getResources().getColor(R.color.white));
        search_src_text.setHint("Search Here");
        // EditText ed=new EditText(LocationActivity.this);

        _disposable = RxTextView.textChangeEvents(search_src_text)
                .debounce(600, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(changes -> isNotNullOrEmpty(changes.text().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(_getSearchObserver());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //  MultiSelectSearchCity.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("")) {
                    scrollView.setVisibility(View.VISIBLE);
                    recycler_view_search.setVisibility(View.GONE);
                } else {
                    scrollView.setVisibility(View.GONE);
                    recycler_view_search.setVisibility(View.VISIBLE);
                }

                //   MultiSelectSearchCity.filter(newText);

                return true;
            }

        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scrollView.setVisibility(View.GONE);
                recycler_view_search.setVisibility(View.VISIBLE);


            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                scrollView.setVisibility(View.VISIBLE);
                recycler_view_search.setVisibility(View.GONE);
                return false;
            }
        });

        data_load();
        if (SessionManager.getIsloc(LocationActivity.this)) {
            locality_load();
        }


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.e("Error is", "" + user_list.get(position));
                SessionManager.setCityid(getApplicationContext(), user_list.get(position).getcityid());
                SessionManager.setlocation_name(getApplicationContext(), user_list.get(position).getName());
                SessionManager.setIsloc(getApplicationContext(), true);
                location_nameis = "";
                if (SessionManager.getIsloc(getApplicationContext()) == false) {
                    Intent i = new Intent(LocationActivity.this, Dashboard.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(LocationActivity.this, Dashboard.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {


            }
        }));


        recycler_view_loc.addOnItemTouchListener(new RecyclerItemClickListener(this, recycler_view_loc, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.e("Error is", "" + locality_list.get(position));
                SessionManager.setCityid(getApplicationContext(), "");
                SessionManager.setLatitude(getApplicationContext(), locality_list.get(position).getLat());
                SessionManager.setLongitude(getApplicationContext(), locality_list.get(position).getLang());
                SessionManager.setIsloc(getApplicationContext(), true);
                SessionManager.setlocation_name(getApplicationContext(), locality_list.get(position).getName());
                location_nameis = locality_list.get(position).getName();
                if (SessionManager.getIsloc(getApplicationContext()) == false) {
                    Intent i = new Intent(LocationActivity.this, Dashboard.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(LocationActivity.this, Dashboard.class);
                    startActivity(i);
                    finish();
                }


            }

            @Override
            public void onItemLongClick(View view, int position) {


            }
        }));

        if (SessionManager.getIsloc(getApplicationContext()) == false) {
            //  getMylocation();
        }


    }

    private DisposableObserver<TextViewTextChangeEvent> _getSearchObserver() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {

                Log.e("", "--------- onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("", "--------- onerror");
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {

                Log.e("Searching for %s", onTextChangeEvent.text().toString());


                search_loc(onTextChangeEvent.text().toString());
            }
        };
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //  locationManager.removeUpdates(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
       /* if (mLocation != null) {
            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        }*/
        else {
            //    Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        } catch (Exception e) {

        }

    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);

        if (SessionManager.getIsloc(getApplicationContext()) == false) {
            progressDialog.show();
        }
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {
        //   String msg = "Updated Location: " + Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());

        SessionManager.setLatitude(getApplicationContext(), Double.toString(location.getLatitude()));
        SessionManager.setLongitude(getApplicationContext(), Double.toString(location.getLongitude()));

        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");
            // showSettingsAlert();
            LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, locationSettingsRequestBuilder.build());
            result.setResultCallback(mResultCallbackFromSettings);
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // check permissions
            if (myloc.equals("yes")) {

                SessionManager.setCityid(getApplicationContext(), "");
                if (SessionManager.getIsloc(getApplicationContext()) == false) {
                    Intent i = new Intent(LocationActivity.this, Dashboard.class);

                    startActivity(i);
                    location_nameis = "";
                    finish();
                    progressDialog.dismiss();
                } else {
                    Intent i = new Intent(LocationActivity.this, Dashboard.class);
                    location_nameis = "";
                    startActivity(i);
                    finish();
                }
                SessionManager.setIsloc(getApplicationContext(), true);

            } else {
                if (SessionManager.getIsloc(getApplicationContext()) == false) {
                    Intent i = new Intent(LocationActivity.this, Dashboard.class);
                    location_nameis = "";
                    SessionManager.setIsloc(getApplicationContext(), true);
                    startActivity(i);
                    finish();
                } else {
                    if (locality_list.isEmpty()) {
                       /* GoogleMap mMap = null;
                        String url = getUrl(location.getLatitude(), location.getLongitude(), "restaurant");
                        Object[] DataTransfer = new Object[2];
                        DataTransfer[0] = mMap;
                        DataTransfer[1] = url;
                        Log.d("onClick", url);
                        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                        getNearbyPlacesData.execute(DataTransfer);*/
                        //  locality_load();


                    } else {

                    }
                }

            }


        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(intent);
        switch (requestCode) {
            case 15000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        if (mGoogleApiClient.isConnected()) {
                            startLocationUpdates();
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to


                        //data_load();
                        //
                        progressDialog.dismiss();

                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void data_load() {


        String url = Constaints.getCity;
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("profile update resp", response);
                    JSONObject jSONObject = new JSONObject(new String(response));
                    // JSONObject jSONObject = new JSONObject("{\"status\": 1,\"cdnpath\": \"uploads/usersdp/\",\"message\": \"Your account has been updated successfully !\",\"info\": {\"id\": \"4\",\"user_type\": \"Doctor\",\"user_name\": \"Dr. Richard\",\"account_key\": \"\", \"doctor_reference_key\": \"2\",\"user_mobile\": \"9999999999\",\"user_dp\": \"https://s3.amazonaws.com/uploads.hipchat.com/57414/3406939/ZOVhui1QUme3G5l/c1f3.png\",\"user_wallet_points\": \"0\",\"about_us\": \"Dr.Richard, senior consultant Pediatrics and Neonatology. Dr. Mangala Pawar personally sees all pediatric cases and has a good team of super specialties for patients with complex diseases. Dr. Mangala Pawar is a senior consultant Paediatrics and Neonatology at Fortis Memorial Research Institute, Gurgaon (FMRI). Dr. Mangala Pawar is a senior consultant at leading Hospitals in Washington DC and Maryland, USA. She has also practiced as a senior consultant at Apollo Hospital, Chennai.\",\"qualification\": \"MBBS , MD\",\"work_area\": \"Paediatrics\",\"pin\": \"110001\",\"status\": \"0\",\"created_at\": \"2017-04-21 12:11:11\",\"updated_at\": \"2017-05-03 10:49:41\",\"in_notifications\": \"0\",\"is_confirmed\": \"1\",\"is_deleted\": \"1\",\"user_email\":\"dr_rechard@gmail.com\",\"address1\": \"Gurgaon Sector 50, Gurgaon\",\"address2\": \"The Close South ,Tower 12, Flat 801, Landmark : Near Unitech Business Zone, Gurgaon\",\"kids\":\"2\"}}");
                    String Status = jSONObject.optString("status");
                    user_list.clear();
                    String msg = jSONObject.optString("message");
                    if (Integer.parseInt(Status) == 1) {
                        JSONArray jsonArray = jSONObject.optJSONArray("info");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            CityModel cityModel = new CityModel(jsonObject.optString("city_name"), jsonObject.optString("id"), "", "", "city");
                            user_list.add(cityModel);
                        }


                        multiSelectAdapter = new MultiSelectAdapter(LocationActivity.this, user_list, multiselect_list, LocationActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        int spanCount = 3; // 3 columns
                        int spacing = 0; // 50px
                        boolean includeEdge = false;
                        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        recyclerView.setAdapter(multiSelectAdapter);


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


    }

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


    private void getMylocation() {
        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");
            //  showSettingsAlert();
            LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, locationSettingsRequestBuilder.build());
            result.setResultCallback(mResultCallbackFromSettings);
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // check permissions

        }

    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + 5000);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyCzTFJDn77l679PHO3do4aQFooCfq3vmiQ");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

        String googlePlacesData;
        GoogleMap mMap;
        String url;

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d("GetNearbyPlacesData", "doInBackground entered");
                mMap = (GoogleMap) params[0];
                url = (String) params[1];
                DownloadUrl downloadUrl = new DownloadUrl();
                googlePlacesData = downloadUrl.readUrl(url);
                Log.d("GooglePlacesReadTask", "doInBackground Exit");
            } catch (Exception e) {
                Log.d("GooglePlacesReadTask", e.toString());
            }
            return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("GooglePlacesReadTask", "onPostExecute Entered");
            List<HashMap<String, String>> nearbyPlacesList = null;
            DataParser dataParser = new DataParser();
            nearbyPlacesList = dataParser.parse(result);
            ShowNearbyPlaces(nearbyPlacesList);
            Log.d("GooglePlacesReadTask", "onPostExecute Exit");
        }

        private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
            locality_list.clear();
            for (int i = 0; i < nearbyPlacesList.size(); i++) {
                Log.d("onPostExecute", "Entered into showing locations");

                HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
                double lat = Double.parseDouble(googlePlace.get("lat"));
                double lng = Double.parseDouble(googlePlace.get("lng"));

                String placeName = googlePlace.get("place_name");
                String vicinity = googlePlace.get("vicinity");
                CityModel cm = new CityModel(placeName, "" + i, "" + lat, "" + lng, "locality");
                locality_list.add(cm);


            }
            if (locality_list.isEmpty()) {
                pick_loc.setVisibility(View.GONE);
            } else {
                pick_loc.setVisibility(View.VISIBLE);
            }
            multiSelectAdapterlocal = new MultiSelectLocalityAdapter(LocationActivity.this, locality_list, multiselect_list, LocationActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

            recycler_view_loc.setLayoutManager(gridLayoutManager);
            recycler_view_loc.setNestedScrollingEnabled(false);
            recycler_view_loc.setItemAnimator(new DefaultItemAnimator());
            int spanCount = 2; // 3 columns
            int spacing = 0; // 50px
            boolean includeEdge = false;
            recycler_view_loc.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            recycler_view_loc.setAdapter(multiSelectAdapterlocal);


        }
    }

    public void locality_load() {


        String url = Constaints.getlocality;
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("profile update resp", response);
                    JSONObject jSONObject = new JSONObject(new String(response));
                    // JSONObject jSONObject = new JSONObject("{\"status\": 1,\"cdnpath\": \"uploads/usersdp/\",\"message\": \"Your account has been updated successfully !\",\"info\": {\"id\": \"4\",\"user_type\": \"Doctor\",\"user_name\": \"Dr. Richard\",\"account_key\": \"\", \"doctor_reference_key\": \"2\",\"user_mobile\": \"9999999999\",\"user_dp\": \"https://s3.amazonaws.com/uploads.hipchat.com/57414/3406939/ZOVhui1QUme3G5l/c1f3.png\",\"user_wallet_points\": \"0\",\"about_us\": \"Dr.Richard, senior consultant Pediatrics and Neonatology. Dr. Mangala Pawar personally sees all pediatric cases and has a good team of super specialties for patients with complex diseases. Dr. Mangala Pawar is a senior consultant Paediatrics and Neonatology at Fortis Memorial Research Institute, Gurgaon (FMRI). Dr. Mangala Pawar is a senior consultant at leading Hospitals in Washington DC and Maryland, USA. She has also practiced as a senior consultant at Apollo Hospital, Chennai.\",\"qualification\": \"MBBS , MD\",\"work_area\": \"Paediatrics\",\"pin\": \"110001\",\"status\": \"0\",\"created_at\": \"2017-04-21 12:11:11\",\"updated_at\": \"2017-05-03 10:49:41\",\"in_notifications\": \"0\",\"is_confirmed\": \"1\",\"is_deleted\": \"1\",\"user_email\":\"dr_rechard@gmail.com\",\"address1\": \"Gurgaon Sector 50, Gurgaon\",\"address2\": \"The Close South ,Tower 12, Flat 801, Landmark : Near Unitech Business Zone, Gurgaon\",\"kids\":\"2\"}}");
                    String Status = jSONObject.optString("status");
                    locality_list.clear();
                    String msg = jSONObject.optString("message");
                    if (Integer.parseInt(Status) == 1) {
                        JSONArray jsonArray = jSONObject.optJSONArray("locality");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);

                            CityModel cityModel = new CityModel(jsonObject.optString("name"), "" + i, jsonObject.optString("lat"), jsonObject.optString("log"), "locality");
                            locality_list.add(cityModel);


                        }
                        if (locality_list.isEmpty()) {
                            pick_loc.setVisibility(View.GONE);
                        } else {
                            pick_loc.setVisibility(View.VISIBLE);
                        }
                        search_list.addAll(locality_list);

                        multiSelectAdapterlocal = new MultiSelectLocalityAdapter(LocationActivity.this, locality_list, multiselect_list, LocationActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

                        recycler_view_loc.setLayoutManager(gridLayoutManager);
                        recycler_view_loc.setNestedScrollingEnabled(false);
                        recycler_view_loc.setItemAnimator(new DefaultItemAnimator());
                        int spanCount = 2; // 3 columns
                        int spacing = 0; // 50px
                        boolean includeEdge = false;
                        recycler_view_loc.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        recycler_view_loc.setAdapter(multiSelectAdapterlocal);
                        seacrhcityAdpater = new MultiSelectSearchCity(LocationActivity.this, search_list, multiselect_list, LocationActivity.this);
                        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

                        recycler_view_search.setLayoutManager(mLayoutManager1);

                        recycler_view_search.setAdapter(seacrhcityAdpater);


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
                params.put("lat", SessionManager.getLatitude(LocationActivity.this));
                params.put("lng", SessionManager.getLongitude(LocationActivity.this));
                params.put("city_id", SessionManager.getCityid(LocationActivity.this));

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

    public void search_loc(String data) {
        String url = Constaints.searchLoc;
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("profile update resp", response);
                    JSONObject jSONObject = new JSONObject(new String(response));
                    String Status = jSONObject.optString("status");
                    search_list.clear();
                    if (Integer.parseInt(Status) == 1) {
                        JSONArray jsonArray = jSONObject.optJSONArray("info");


                        if (jsonArray.length() != 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.optJSONObject(i);

                                if (jsonObject.has("cityId")) {
                                    CityModel cityModel = new CityModel(jsonObject.optString("name"), jsonObject.optString("cityId"), "", "", "city");
                                    search_list.add(cityModel);
                                } else {
                                    CityModel cityModel = new CityModel(jsonObject.optString("name"), "" + i, jsonObject.optString("lat"), jsonObject.optString("lng"), "locality");
                                    search_list.add(cityModel);
                                }


                            }


                            seacrhcityAdpater = new MultiSelectSearchCity(LocationActivity.this, search_list, multiselect_list, LocationActivity.this);
                            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

                            recycler_view_search.setLayoutManager(mLayoutManager1);

                            recycler_view_search.setAdapter(seacrhcityAdpater);
                        } else {
                            Toast.makeText(getApplicationContext(), "No search found", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        Toast.makeText(getApplicationContext(), "No search found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                    progressBar.setVisibility(View.GONE);
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
                params.put("search_key", data);


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
