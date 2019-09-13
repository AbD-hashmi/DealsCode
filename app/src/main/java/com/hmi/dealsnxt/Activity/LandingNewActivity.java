package com.hmi.dealsnxt.Activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.hmi.dealsnxt.CustomControl.DownloadUrl;
import com.hmi.dealsnxt.Fragement.FoodDrinkFragment;
import com.hmi.dealsnxt.Fragement.HotDealsFragment;
import com.hmi.dealsnxt.Fragement.HotelFragment;
import com.hmi.dealsnxt.Fragement.SpaFragment;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.BannerImagesModel;
import com.hmi.dealsnxt.Model.TimeModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Services.RegistrationIntentService;
import com.hmi.dealsnxt.Utils.MultiSelectAdapter;
import com.hmi.dealsnxt.Utils.TimeselsectionAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LandingNewActivity extends AppCompatActivity {
    private static final String TAG = LandingNewActivity.class.getSimpleName();
    public TabLayout tabLayout;

    int tabnum;
    NavigationView filter_view;
    LinearLayout LLrate, LLshare, LLNotification, LLRefer, LLPrivacy, LLUse, LLAbout, LLcontact, LLlogout,LLclose;
    LinearLayout LLpopulardeals, LLproximity, LLtrading, LLorder;
   // TextView tvmore;
    LinearLayout LLfooter, LLfooterHome, LLfooterOrder, LLfooterProfile, LLfootermore, LLfooterFavourite;
    LinearLayout LLloc;
    ImageView imBack, imlocation, ivfilter;
    TextView tvTitle, tvaddress, tvusername;
    ImageView ivsearch;
    public static ViewPager view_pager;
    public ArrayList<BannerImagesModel> bannerimglist = new ArrayList<BannerImagesModel>();
   // TextView tvHome, tvOrder, tvProfile, tvFavourite;
     LinearLayout  LLHome, LLOrder, LLProfile, LLFavourite, LLmore;
   // ImageView ivHome, ivOrder, ivProfile, ivFavourite, ivmore;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    int currentPage = 0;
    Timer timer;
    public ProgressBar progress_bar;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions defaultOptions;
    public DrawerLayout drawer;
    public String banner_outletid = "";
    public static CollapsingToolbarLayout collapsingToolbarLayout;
    public static AppBarLayout htab_appbar;
    public static CoordinatorLayout htab_maincontent;
    public static CoordinatorLayout.LayoutParams params;
    public String location1 = "", location2 = "";
    View promptView;
    android.app.AlertDialog alert;
    //  public ProgressBar progressBar;
    public static BottomNavigationView navigationView;

    ArrayList<TimeModel> timearray_list = new ArrayList<>();
    ArrayList<TimeModel> user_list = new ArrayList<>();
    ArrayList<TimeModel> multiselect_list = new ArrayList<>();
    public static TimeselsectionAdapter timeSelectAdapter;
    public MultiSelectAdapter multiSelectAdapterlocal;
    String response = "{\"status\": 1,\"message\": \"Your Date list \",\"info\": [{\"id\": 1,\"start_time\": \"12:00\",\"end_time\": \"02:00\"},{\"id\": 2,\"start_time\": \"02:00\",\"end_time\": \"04:00\"},{\"id\": 3,\"start_time\":\"04:00\",\n" +
            "\"end_time\": \"06:00\"},{\"id\": 4,\"start_time\": \"06:00\",\"end_time\": \"08:00\"},{\"id\": 5,\"start_time\": \"08:00\",\"end_time\": \"10:00\"},{\"id\": 6,\"start_time\": \"10:00\",\"end_time\": \"12:00\"}]}";
    public String offval = "";
    TextView tvdetectlocation, tvpricerange;
    SeekBar seekbar;
    Switch switch2, switch3, switch4, switch5;
    TextView ivfiltertype1value, ivfiltertype2value, ivfiltertype3value, ivfiltertype4value;
    RecyclerView time_recycler_view;
    LinearLayout LLfilter1, LLfilter2, LLfilter3, LLfilter4;
    TextView tvpricerangend, tvpricerangstart;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landing_landing);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final LinearLayout toolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        toolbar.setClickable(false);
        SessionManager.setrefercode(getApplicationContext(), true);
        LLloc = (LinearLayout) toolbar.findViewById(R.id.LLloc);
        imBack = (ImageView) toolbar.findViewById(R.id.imBack);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        LLloc = (LinearLayout) toolbar.findViewById(R.id.LLloc);
        LLloc.setVisibility(View.GONE);
        imlocation = (ImageView) toolbar.findViewById(R.id.imlocation);
        tvaddress = (TextView) toolbar.findViewById(R.id.tvaddress);
        ivfilter = (ImageView) toolbar.findViewById(R.id.ivfilter);
        ivsearch = (ImageView) toolbar.findViewById(R.id.ivsearch);
      //  imBack = (ImageView) toolbar.findViewById(R.id.imBack);
        tvusername = (TextView) toolbar.findViewById(R.id.tvusername);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        SessionManager.setrefercode(LandingNewActivity.this,true);

        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Dining");
        tvTitle.setGravity(View.FOCUS_LEFT);


        tabnum=getIntent().getExtras().getInt("tab");

        imBack.setVisibility(View.VISIBLE);
        ivfilter.setVisibility(View.VISIBLE);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ivsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LandingNewActivity.this,SearchActivity.class);
                startActivity(i);
            }
        });


        ivfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_view.setVisibility(View.VISIBLE);
                //     LoadfilterUI();
                DrawerLayout.LayoutParams params1 = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params1.setLayoutDirection(Gravity.RIGHT);
                    params1.gravity = GravityCompat.END;
                }
                filter_view.setLayoutParams(params1);
                DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.MATCH_PARENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setLayoutDirection(Gravity.LEFT);
                    params.gravity = GravityCompat.START;

                }
                navigationView.setLayoutParams(params);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
                seekbar = (SeekBar) findViewById(R.id.seekbar);
                ivsearch = (ImageView) findViewById(R.id.ivsearch);
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

                loadtimeschedule(response);
            }
        });

        String test = SessionManager.getUserName(getApplicationContext()).toString();
        //   String test = "Priyanshu";
        // char first = test.charAt(0);
        tvusername.setText(test);
        tvusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LandingNewActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });

        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(SessionManager.getLatitude(getApplicationContext())), Double.parseDouble(SessionManager.getLongitude(getApplicationContext())), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            //  SessionManager.setlocation_name(getApplicationContext(),city);
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            if (SessionManager.getCityid(getApplicationContext()).equals("")) {
                //    tvaddress.setText(city + "," + state);
            } else {
                //    tvaddress.setText(SessionManager.getLocation_name(getApplicationContext()));
            }
        } catch (Exception e) {

        }

        loadbannerImages();



        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        ///   PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        setupViewPager(viewPager);
        ///////     tabs.setViewPager(viewPager);
        setupTabIcons();


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        htab_appbar = (AppBarLayout) findViewById(R.id.htab_appbar);
        htab_maincontent = (CoordinatorLayout) findViewById(R.id.htab_maincontent);

        navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        filter_view = (NavigationView) findViewById(R.id.filter_view);
       // filter_view.setVisibility(View.GONE);
        DrawerLayout.LayoutParams params1 = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params1.setLayoutDirection(Gravity.LEFT);
            params1.gravity = GravityCompat.START;
        }
        filter_view.setLayoutParams(params1);
        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.MATCH_PARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.setLayoutDirection(Gravity.RIGHT);
            params.gravity = GravityCompat.END;

        }
        navigationView.setLayoutParams(params);
        LLfooter = (LinearLayout) findViewById(R.id.LLfooter);
        // LinearLayout LLmore = (LinearLayout) LLfooter.findViewById(R.id.LLmore);

        LLfooter = (LinearLayout) findViewById(R.id.LLfooter);
        LLfooterHome = (LinearLayout) LLfooter.findViewById(R.id.LLfooterHome);
        LLfooterOrder = (LinearLayout) LLfooter.findViewById(R.id.LLfooterOrder);
        LLfooterProfile = (LinearLayout) LLfooter.findViewById(R.id.LLfooterProfile);
        LLfooterFavourite = (LinearLayout) LLfooter.findViewById(R.id.LLfooterFavourite);
        LLclose= (LinearLayout) findViewById(R.id.LLclose);
        LLclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  tvmore.performClick();
            }
        });
        LLfootermore = (LinearLayout) LLfooter.findViewById(R.id.LLfootermore);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

        {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabnum = tab.getPosition();

                //   View firstTab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
                //    View secondTab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1);
                if (tabnum == 0) { // that means first tab
                    //    Toast.makeText(getApplicationContext(), "This is my 1 message!",
                    //   Toast.LENGTH_LONG).show();
                    LLloc.setVisibility(View.INVISIBLE);
                    imBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setGravity(View.FOCUS_LEFT);
                    ivfilter.setVisibility(View.VISIBLE);
                    tvTitle.setText("Dining");

                } else if (tabnum == 1) { // that means it's a last tab
                    // Toast.makeText(getApplicationContext(), "This is my 2 message!",
                    //         Toast.LENGTH_LONG).show();
                    LLloc.setVisibility(View.INVISIBLE);
                    imBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setGravity(View.FOCUS_LEFT);
                    ivfilter.setVisibility(View.VISIBLE);
                    tvTitle.setText("Wellness");

                    imBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           onBackPressed();
                        }
                    });
                } else if (tabnum == 2) { // that means it's a last tab
                    // Toast.makeText(getApplicationContext(), "This is my 3 message!",
                    //         Toast.LENGTH_LONG).show();
                    LLloc.setVisibility(View.INVISIBLE);
                    imBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setGravity(View.FOCUS_LEFT);
                    ivfilter.setVisibility(View.VISIBLE);
                    tvTitle.setText("Fashion");
                    imBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();

                        }
                    });
                } else if (tabnum == 3) { // that means it's a last tab
                    //  Toast.makeText(getApplicationContext(), "This is my 4 message!",
                    //        Toast.LENGTH_LONG).show();
                    LLloc.setVisibility(View.INVISIBLE);
                    imBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setGravity(View.FOCUS_LEFT);
                    ivfilter.setVisibility(View.VISIBLE);
                    tvTitle.setText("Accessories");
                    imBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();

                        }
                    });
                }else if (tabnum == 4) { // that means it's a last tab
                    //  Toast.makeText(getApplicationContext(), "This is my 4 message!",
                    //        Toast.LENGTH_LONG).show();
                    LLloc.setVisibility(View.INVISIBLE);
                    imBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setGravity(View.FOCUS_LEFT);
                    ivfilter.setVisibility(View.VISIBLE);
                    tvTitle.setText("Events");
                    imBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();

                        }
                    });
                }else if (tabnum == 5) { // that means it's a last tab
                    //  Toast.makeText(getApplicationContext(), "This is my 4 message!",
                    //        Toast.LENGTH_LONG).show();
                    LLloc.setVisibility(View.INVISIBLE);
                    imBack.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setGravity(View.FOCUS_LEFT);
                    ivfilter.setVisibility(View.VISIBLE);
                    tvTitle.setText("Membership");
                    imBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();

                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (
                checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    public void loadbannerImages() {
        String url = Constaints.BannerUrl;
        progress_bar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    int Status = jSONObject.optInt("status");
                    String Path = Constaints.BaseUrl + jSONObject.optString("bannerCdnpath");
                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("info");
                        for (int i = 0; i < infoArray.length(); i++) {
                            //  ListModel listModel = new ListModel();
                            //  JSONObject outlet = infoArray.getJSONObject(i);
                            //     JSONArray dealJsonArray = outlet.optJSONArray("image");
                            //     ArrayList<AssetsModel> assetsModels = new ArrayList<>(assetsJsonArray.length());
                            ArrayList<BannerImagesModel> ImagesModels = new ArrayList<BannerImagesModel>();
                            // DealImagesModel.deleteRecord("account" + accountJson.optInt("id"));
                            //  for (int j = 0; j < dealJsonArray.length(); j++) {
                            JSONObject review = infoArray.optJSONObject(i);
                            BannerImagesModel um = new BannerImagesModel();
                            um.setBannerimage_ID(review.optString("id"));
                            um.setBanner_outletid(review.optString("link"));
                            //       um.setOutlet_id(outlet.optString("id"));
                            // um.setBannerimages_url(review.optString("image_name"));
                            //  um.setImage_name(review.optString("image_name"));
                            um.setBannerimages_url(Path + "/" + review.optString("img"));
                            ImagesModels.add(um);
                            bannerimglist.add(um);
                        }
                        //  }
                    } else {
                        Toast.makeText(getApplicationContext(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                    }
                    CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getApplicationContext(), bannerimglist);
                    view_pager.setAdapter(mCustomPagerAdapter);
                     /*After setting the adapter use the timer */
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == bannerimglist.size() - 1) {
                                currentPage = 0;
                            }
                            view_pager.setCurrentItem(currentPage++, true);
                        }
                    };
                    timer = new Timer(); // This will create a new Thread
                    timer.schedule(new TimerTask() { // task to be scheduled
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, DELAY_MS, PERIOD_MS);
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                }
                progress_bar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_bar.setVisibility(View.GONE);
                Log.e("error", "" + error);
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

    public void loadtimeschedule(String response) {
        progress_bar.setVisibility(View.VISIBLE);
        try {
            Log.e("profile update resp", response);
            JSONObject jSONObject = new JSONObject(response);
            // JSONObject jSONObject = new JSONObject("{\"status\": 1,\"cdnpath\": \"uploads/usersdp/\",\"message\": \"Your account has been updated successfully !\",\"info\": {\"id\": \"4\",\"user_type\": \"Doctor\",\"user_name\": \"Dr. Richard\",\"account_key\": \"\", \"doctor_reference_key\": \"2\",\"user_mobile\": \"9999999999\",\"user_dp\": \"https://s3.amazonaws.com/uploads.hipchat.com/57414/3406939/ZOVhui1QUme3G5l/c1f3.png\",\"user_wallet_points\": \"0\",\"about_us\": \"Dr.Richard, senior consultant Pediatrics and Neonatology. Dr. Mangala Pawar personally sees all pediatric cases and has a good team of super specialties for patients with complex diseases. Dr. Mangala Pawar is a senior consultant Paediatrics and Neonatology at Fortis Memorial Research Institute, Gurgaon (FMRI). Dr. Mangala Pawar is a senior consultant at leading Hospitals in Washington DC and Maryland, USA. She has also practiced as a senior consultant at Apollo Hospital, Chennai.\",\"qualification\": \"MBBS , MD\",\"work_area\": \"Paediatrics\",\"pin\": \"110001\",\"status\": \"0\",\"created_at\": \"2017-04-21 12:11:11\",\"updated_at\": \"2017-05-03 10:49:41\",\"in_notifications\": \"0\",\"is_confirmed\": \"1\",\"is_deleted\": \"1\",\"user_email\":\"dr_rechard@gmail.com\",\"address1\": \"Gurgaon Sector 50, Gurgaon\",\"address2\": \"The Close South ,Tower 12, Flat 801, Landmark : Near Unitech Business Zone, Gurgaon\",\"kids\":\"2\"}}");
            String Status = jSONObject.optString("status");
            //    user_list.clear();
            String msg = jSONObject.optString("message");
            if (Integer.parseInt(Status) == 1) {
                JSONArray jsonArray = jSONObject.optJSONArray("info");
                timearray_list.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    TimeModel timeModel = new TimeModel(jsonObject.optString("start_time"), jsonObject.optString("end_time"), jsonObject.optString("id"));
                    timearray_list.add(timeModel);
                }
                timeSelectAdapter = new TimeselsectionAdapter(LandingNewActivity.this, timearray_list, multiselect_list, LandingNewActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                time_recycler_view.setNestedScrollingEnabled(false);
                time_recycler_view.setLayoutManager(gridLayoutManager);
                time_recycler_view.setItemAnimator(new DefaultItemAnimator());
                int spanCount = 2; // 3 columns
                int spacing = 0; // 50px
                boolean includeEdge = false;
                time_recycler_view.addItemDecoration(new LandingNewActivity.GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                time_recycler_view.setAdapter(timeSelectAdapter);
            } else {
                Toast.makeText(getApplicationContext(), jSONObject.optString("message").toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
        progress_bar.setVisibility(View.GONE);
    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context, ArrayList<BannerImagesModel> list) {
            defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .showImageOnLoading(R.drawable.banner)
                    .showStubImage(R.drawable.banner)
                    .showImageForEmptyUri(R.drawable.banner)
                    .showImageOnFail(R.drawable.banner)
                    .build();
            mContext = context;
            bannerimglist = list;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getCount() {
            return bannerimglist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));
            View itemView = mLayoutInflater.inflate(R.layout.pager, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.bannerimg);
            imageLoader.displayImage(bannerimglist.get(position).getBannerimages_url(), imageView, defaultOptions);
            banner_outletid = bannerimglist.get(position).getBanner_outletid();
            container.addView(itemView);
            bannerimglist.get(position).getBanner_outletid();

            //       if (bannerimglist.get(position).getBannerimages_url() != "") {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (banner_outletid.equals("")) {
                    } else {
                        Intent i = new Intent(LandingNewActivity.this, DetailNewActivity.class);
                        i.putExtra("outletid", banner_outletid);
                        startActivity(i);
                    }
                }
            });
            //      }
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }




    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        setTitle("DINING");
        tabOne.setText("DINING");
      //  tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.dining, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("WELLNESS");
        setTitle("WELLNESS");

        //  tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.wellness, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("FASHION");
        setTitle("FASHION");

        //tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fashion, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("ACCESSORIES");
        setTitle("ACCESSORIES");

        // tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.accessories, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText("EVENTS");
        setTitle("EVENTS");

        //  tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.event, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);

        TextView tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSix.setText("MEMBERSHIP");
        setTitle("MEMBERSHIP");

        //  tabSix.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.membership, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tabSix);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HotDealsFragment(), "DINING");
        adapter.addFrag(new FoodDrinkFragment(), "WELLNESS");
        adapter.addFrag(new HotelFragment(), "FASHION");
        adapter.addFrag(new SpaFragment(), "ACCESSORIES");
        adapter.addFrag(new SpaFragment(), "EVENTS");
        adapter.addFrag(new SpaFragment(), "MEMBERSHIP");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.d("OnCreate ", " Called");
        //   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //   this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  setContentView(R.layout.activity_landing);
        //    findViewsById();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            Geocoder geocoder;
            List<Address> addresses = new ArrayList<>();
            geocoder = new Geocoder(LandingNewActivity.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(SessionManager.getLatitude(getApplicationContext())), Double.parseDouble(SessionManager.getLongitude(getApplicationContext())), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                Log.e("result is", "" + result);

                JSONObject jSONObject = new JSONObject(result);
                JSONArray objArray = jSONObject.optJSONArray("results");
                String status = jSONObject.optString("status");
                if (status.equals("OK")) {
                    for (int i = 0; i < 1; i++) {
                        JSONObject outlet = objArray.getJSONObject(0);
                        JSONArray outletJSONArray = outlet.getJSONArray("address_components");

                        for (int j = 0; j < outletJSONArray.length(); j++) {
                            JSONObject outletobj = outletJSONArray.getJSONObject(j);

                            if (j == 2) {
                                location1 = outletobj.optString("short_name");
                            }
                            if (j == 5) {
                                location2 = outletobj.optString("short_name");
                            }
                        }
                        tvaddress.setText(location1 + " , " + location2);
                    }
                }

                String subtoroughfare = addresses.get(0).getSubThoroughfare();
                String thoroughfare = addresses.get(0).getThoroughfare();
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                // SessionManager.setlocation_name(getApplicationContext(),city);
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                if (SessionManager.getCityid(getApplicationContext()).equals("")) {
                    if (LocationActivity.location_nameis.equals("")) {
                        //     tvaddress.setText(thoroughfare + "," + state);
                    } else {
                        //    tvaddress.setText(SessionManager.getLocation_name(getApplicationContext()));
                    }
                } else {
                    tvaddress.setText(SessionManager.getLocation_name(getApplicationContext()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
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
}