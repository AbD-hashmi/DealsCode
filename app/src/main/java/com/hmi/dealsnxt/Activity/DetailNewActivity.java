package com.hmi.dealsnxt.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hmi.dealsnxt.Adaptor.HotDealsAdaptor;
import com.hmi.dealsnxt.Adaptor.OutletsDeallistAdaptor;
import com.hmi.dealsnxt.Adaptor.OutletsDeallistAdaptorNew;
import com.hmi.dealsnxt.Adaptor.SimilairDealsAdaptor;
import com.hmi.dealsnxt.Fragement.Description;
import com.hmi.dealsnxt.Fragement.RedeemCancel;
import com.hmi.dealsnxt.Fragement.TermsAndConditiona;
import com.hmi.dealsnxt.Fragement.UpcommingOrderfragment;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.DealDetailsModel;
import com.hmi.dealsnxt.Model.DealImagesModel;
import com.hmi.dealsnxt.Model.HotDealsModel;
import com.hmi.dealsnxt.Model.ListModel;
import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.ScrollableTextView;
import com.hmi.dealsnxt.Utils.Common;
import com.hmi.dealsnxt.Utils.Customutils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WAKE_LOCK;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DetailNewActivity extends FragmentActivity implements OnMapReadyCallback {
    TextView tvlike, tvoutletname, tvoutletdistncekm, tvoutletaddress, tvstartime, tvendtime, tvtncdetail, tvdealdetail, tvshowextra;
    TextView tvdealaddress, tvbuy, tvTitle, tvdetail, tvphone,tvCountBanner,tvGift;
    LinearLayout LLlist, LLtnc, LLdeal, RLlocation, RLdealdata;
    RelativeLayout LLrember, LLpayment, RLAmount, RLtoolbar_new, RRl;
    ImageView ivdeal, ivshare, ivlikecount, ivgift, imBack;
    RecyclerView viewlist;
    ProgressBar progressBar;
    RelativeLayout rll;
    public Spanned description;
    public Spanned termsandconditions;
    private GoogleMap mMap;
    ViewPager view_pager;
    public static final int RequestPermissionCode = 888;
    public List<ListModel> arrayList = new ArrayList<>();
    public List<HotDealsModel> similararrayList = new ArrayList<>();
    //  List<DealImagesModel> dealimg;
    public ArrayList<DealImagesModel> dealimglist = new ArrayList<DealImagesModel>();
    OutletsDeallistAdaptorNew adapter;
    ImageView ivmoveup;
    Boolean isReceiverRegistered = false;
    public static TextView tvfinalamount;
    public static TextView tvactualprice;
    public int pos = -1;
    public int Fromdiscount = 0;
    public String Dealimgname = "";
    public String Dealstarttime = "";
    public String Dealendtime = "";
    public int Qtycount = 0;
    public int Dealid = 0;
    public int Dealprice = 0;
    public String Latitude = "";
    public String Longtitude = "";
    int currentPage = 0;
    Timer timer;
    TextView tvoutletdetail;
    final long DELAY_MS = 5000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;
    // public static String Outletname = "", Outletadress = "", timein = "", timeout = "";
    //   UploadLeadsBroad uploadLeadsBroad;
    ArrayList<DealDetailsModel> orderarrayList = new ArrayList<>();
    RecyclerView recyclerView;
    SimilairDealsAdaptor similaradapter;
    NestedScrollView scroll_view;
    RelativeLayout.LayoutParams layoutParams;
    public static final int REQUEST_CODE = 1;
    public LinearLayout LLloc;
    public ImageView ivfilter;
    public TextView tvusername ,getDirections;
    public LinearLayout newtoolbar;
    String stroutletname;
    public TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        RLdealdata = (LinearLayout) findViewById(R.id.RLdealdata);
        RRl = (RelativeLayout) findViewById(R.id.RRl);
        scroll_view = (NestedScrollView) findViewById(R.id.scroll_view);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        // scroll_view.setLayoutParams(layoutParams);
        if (CheckingPermissionIsEnabledOrNot()) {

        } else {
            RequestMultiplePermission();
        }

        tvlike = (TextView) findViewById(R.id.tvlike);
        ivdeal = (ImageView) findViewById(R.id.ivdeal);
        ivshare = (ImageView) findViewById(R.id.ivshare);
        ivlikecount = (ImageView) findViewById(R.id.ivlikecount);
        tvoutletname = (TextView) findViewById(R.id.tvoutletname);
        tvCountBanner = (TextView) findViewById(R.id.tvCount);
        tvoutletdistncekm = (TextView) findViewById(R.id.tvoutletdistncekm);
        // ivgift = (ImageView) findViewById(R.id.ivgift);
        tvoutletaddress = (TextView) findViewById(R.id.tvoutletaddress);
        tvstartime = (TextView) findViewById(R.id.tvstartime);
        tvendtime = (TextView) findViewById(R.id.tvendtime);
        LLlist = (LinearLayout) findViewById(R.id.LLlist);
        viewlist = (RecyclerView) findViewById(R.id.viewlist);
        viewlist.setNestedScrollingEnabled(false);
        LLtnc = (LinearLayout) findViewById(R.id.LLtnc);
        rll=(RelativeLayout) findViewById(R.id.rll);
        // tvtncdetail = (TextView) findViewById(R.id.tvdetail);
        LLdeal = (LinearLayout) findViewById(R.id.LLdeal);
        tvdealdetail = (TextView) findViewById(R.id.tvdealdetail);
        LLrember = (RelativeLayout) findViewById(R.id.LLrember);
        tvshowextra = (TextView) findViewById(R.id.tvshowextra);
        ivmoveup = (ImageView) findViewById(R.id.ivmoveup);
        RLlocation = (LinearLayout) findViewById(R.id.RLlocation);
        //  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        RLlocation.setVisibility(View.INVISIBLE);
        tvdealaddress = (TextView) findViewById(R.id.tvdealaddress);
        LLpayment = (RelativeLayout) findViewById(R.id.LLpayment);
        RLAmount = (RelativeLayout) findViewById(R.id.RLAmount);
        tvactualprice = (TextView) findViewById(R.id.tvactualprice);
        tvfinalamount = (TextView) findViewById(R.id.tvfinalamount);
        getDirections=(TextView)findViewById(R.id.getDirections);
        tvfinalamount.setVisibility(View.INVISIBLE);
        tvbuy = (TextView) findViewById(R.id.tvbuy);
        tvGift = (TextView) findViewById(R.id.ivgift);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        tvdetail = (TextView) findViewById(R.id.tvdetail);
        tvphone = (TextView) findViewById(R.id.tvphone);
        // LLpayment.setVisibility(View.GONE);
        tvoutletdetail = (TextView) findViewById(R.id.tvoutletdetail);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        //  collapsingToolbar.setTitle("Title");

        imBack = (ImageView) findViewById(R.id.imBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        try {

            //     tvdealaddress.setText(HotDealsModel.getHotDealsModel().getOutletAddress() + "," + HotDealsModel.getHotDealsModel().getOutletCity() + "," + HotDealsModel.getHotDealsModel().getOutletzipcode());

        } catch (Exception e) {
            e.printStackTrace();
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);

        tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.blackfontcolor));
        tabLayout.setupWithViewPager(viewPager);
        //   tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        setupViewPager(viewPager);

        recyclerView = (RecyclerView) findViewById(R.id.similar_scorll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        ivmoveup.setVisibility(View.GONE);
        registerReceiver(uiUpdated, new IntentFilter("ALERT_CHANGE"));

        // tvactualprice.setPaintFlags(tvactualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(DetailNewActivity.this, "Preparing..", Toast.LENGTH_SHORT).show();
                   /* RLdealdata.setDrawingCacheEnabled(true);
                    RLdealdata.buildDrawingCache();
                    Bitmap bitmap = RLdealdata.getDrawingCache().copy(Bitmap.Config.RGB_565, false);
                    RLdealdata.setDrawingCacheEnabled(false);*/
                    scroll_view.setDrawingCacheEnabled(true);
                    scroll_view.buildDrawingCache();
                    Bitmap bitmap = scroll_view.getDrawingCache().copy(Bitmap.Config.RGB_565, false);
                    scroll_view.setDrawingCacheEnabled(false);

                    String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/ProjectName";
                    File path = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES);
                    File file = new File(path, "Deal.png");
                    File dir = new File(file_path);
                    if (!dir.exists())
                        dir.mkdirs();
                    //File file = new File(dir, "test.png");
                    if (file.exists()) file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream fOut;

                    try {
                        fOut = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Uri uri = Uri.fromFile(file);
                    // String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"deal", null);
                    //  Uri bitmapUri = Uri.parse(bitmapPath);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("image/*");

                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Get 40 % of on this deal");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, " Check out this amazing deal I found on Xclusify.Register with referral code NNALNF to get 40% off on your first order on " + HotDealsModel.getHotDealsModel().getDealTitle() + " of " + " " + HotDealsModel.getHotDealsModel().getOutletName() + "" + "\n  market://details?id=com.hmi.dealsnxt");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, "Share deal with friends"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        rll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.rll) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        loadOfflineDeals("PullToRefresh");
        loadDetailist();

/*
        scroll_view.setSmoothScrollingEnabled(true);
        scroll_view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ScrollableTextView.enableScroll(scroll_view);
                //viewlist.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/
        initMap();

        // view_pager.setAdapter(new CustomPagerAdapter(DetailNewActivity.this, mlist));

        // uploadLeadsBroad = new UploadLeadsBroad();
        //      getApplicationContext().registerReceiver(uploadLeadsBroad, new IntentFilter("uploadLeadsBroad"));

        //LLpayment.setO;




        //     tvfinalamount.setTextColor(DealDetailsModel.getDealDetailsModel().getDealcalculatedprice().toString());


        ivmoveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll_view.smoothScrollTo(0, 0);
            }
        });

        scroll_view.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scroll_view != null) {
                    if (scroll_view.getChildAt(0).getBottom() <= (scroll_view.getHeight() + scroll_view.getScrollY())) {
//scroll view is at bottom
                        //ivmoveup.setVisibility(View.VISIBLE);
                    } else {
//scroll view is not at bottom
                        ivmoveup.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }


    class CustomPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;
        DisplayImageOptions defaultOptions;

        public CustomPagerAdapter(Context context, ArrayList<DealImagesModel> list) {
            defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .showImageOnLoading(R.drawable.banner)
                    .showStubImage(R.drawable.banner)
                    .showImageForEmptyUri(R.drawable.banner)
                    .showImageOnFail(R.drawable.banner)
                    .build();
            mContext = context;
            dealimglist = list;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return dealimglist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager, container, false);
            try {
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 20 * 1000))
                        .build();
                ImageLoader imageLoader = ImageLoader.getInstance();
                //      imageLoader.getInstance().init(config);
                ImageView imageView = (ImageView) itemView.findViewById(R.id.bannerimg);
                // Picasso.with(getApplicationContext()).load(dealimglist.get(position).getImages_url()).into(imageView);
                if(!dealimglist.get(position).getImages_url().isEmpty())
                    imageLoader.displayImage(dealimglist.get(position).getImages_url(), imageView, defaultOptions);
            }catch (Exception e){
                e.printStackTrace();
            }
            //   imageView.setImageResource(dealimglist.get(position).getImages_url());
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
    public String path="";
    public void loadDetailist() {
        String url = Constaints.DealDetailbyOutlet;
        progressBar.setVisibility(View.VISIBLE);
        final ProgressDialog progressDialog = Common.getProgressDialog(DetailNewActivity.this);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(response));
                    int Status = jSONObject.optInt("status");
                    String Path = Constaints.BaseUrl + jSONObject.optString("outletCdnpath");
                    path.equals(Path);
                    String Path_deal = Constaints.BaseUrl + jSONObject.optString("dealsCdnpath");
                    //       dealsModel.setDealsName(Constaints.BaseUrl + merchant.optString("dealsCdnpath"));
                    progressDialog.dismiss();
                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("info");
                        JSONArray similararray = jSONObject.getJSONArray("similarDeals");
                        for (int i = 0; i < infoArray.length(); i++) {
                            //  ListModel listModel = new ListModel();
                            final JSONObject outlet = infoArray.getJSONObject(i);


                            JSONArray dealJsonArray = outlet.optJSONArray("image");
                            Latitude = outlet.optString("lat");
                            Longtitude = outlet.optString("lng");
                            //     ArrayList<AssetsModel> assetsModels = new ArrayList<>(assetsJsonArray.length());
                            tvTitle.setText(outlet.optString("name"));
                            String title=outlet.optString("name");
                            getDirections.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String geoUri = "http://maps.google.com/maps?q=loc:" + Double.valueOf(Latitude) +
                                            "," + Double.valueOf(Longtitude) + " (" + title+ ")";
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                                    startActivity(intent);
                                }
                            });
                            tvoutletname.setText(outlet.optString("name"));
                            stroutletname=""+outlet.optString("name");

                            tvoutletaddress.setText("Address: "+outlet.optString("address") + "," + outlet.optString("city") + "," + outlet.optString("zipcode"));
                            //      tvphone.setText(outlet.optString("name"));
                            tvphone.setText("Phone: "+outlet.optString("merchantPhone"));
                            tvphone.setOnClickListener(new View.OnClickListener() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onClick(View view) {
                                    try {
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:" + outlet.optString("merchantPhone")));
                                        startActivity(callIntent);
                                    } catch (Exception e) {

                                    }

                                }
                            });
                            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(DetailNewActivity.this);
                            SharedPreferences.Editor editor=sharedPreferences.edit();

                            description=Html.fromHtml(outlet.optString("description"));
                            termsandconditions=Html.fromHtml(outlet.optString("termAndCondition"));
                            editor.putString("description", String.valueOf(description));
                            editor.putString("tnc", String.valueOf(termsandconditions));
                            editor.commit();


                            tvdetail.setText(Html.fromHtml(outlet.optString("description")));

                            Location loc1 = new Location("");
                            try {
                                loc1.setLatitude(Double.valueOf(SessionManager.getLatitude(getApplicationContext())));
                                loc1.setLongitude(Double.valueOf(SessionManager.getLongitude(getApplicationContext())));
                                Location loc2 = new Location("");
                                loc2.setLatitude(Double.valueOf(outlet.optString("lat")));
                                loc2.setLongitude(Double.valueOf(outlet.optString("lng")));
                                float distanceInMeters = loc1.distanceTo(loc2);
                                tvoutletdistncekm.setText((new DecimalFormat("##.##").format(distanceInMeters * 0.001)) + "km");

                            } catch (Exception e) {

                            }
                            LatLng test = new LatLng(Double.valueOf(Latitude), Double.valueOf(Longtitude));
                            mMap.addMarker(new MarkerOptions().position(test).title(outlet.optString("name")));
                            //mMap.moveCamera(CameraUpdateFactory.newLatLng(test));

                            float zoomLevel = 16.0f; //This goes up to 21
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test, zoomLevel));
                            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

                            RLlocation.setVisibility(View.VISIBLE);

                            ArrayList<DealImagesModel> ImagesModels = new ArrayList<DealImagesModel>();
                            // DealImagesModel.deleteRecord("account" + accountJson.optInt("id"));
                            for (int j = 0; j < dealJsonArray.length(); j++) {
                                JSONObject review = dealJsonArray.optJSONObject(j);
                                DealImagesModel um = new DealImagesModel();
                                um.setImage_ID(review.optString("id"));
                                //       um.setOutlet_id(outlet.optString("id"));
                                um.setImage_name(review.optString("image_name"));
                                //  um.setImage_name(review.optString("image_name"));
                                um.setImages_url(Path + "/" + review.optString("image_name"));
                                ImagesModels.add(um);
                                dealimglist.add(um);
                            }
                            //       DealImagesModel.saveInTx(ImagesModels);
                            JSONArray dealArray = outlet.optJSONArray("deals");
                            for (int j = 0; j < dealArray.length(); j++) {
                                ListModel listModel = new ListModel();
                                JSONObject data = dealArray.getJSONObject(j);
                                tvlike.setText(data.optString("totalLike"));
                                listModel.setDealid(data.optString("id"));
                                listModel.setDealname(data.optString("deal_title"));
                                listModel.setActualprice(data.optString("deal_price"));
                                listModel.setOutletname(data.optString("deal_title"));
                                listModel.setOutletintime(data.optString("deal_available_from"));
                                listModel.setOutletouttime(data.optString("deal_available_to"));
                                listModel.setDesciption(data.optString("deal_description"));
                                listModel.setNumofOffers(data.optInt("stock_qty"));
                                listModel.setDealCount(outlet.optInt("dealCount"));
                                listModel.setDiscountpercent(data.optString("percentage"));
                                listModel.setAfterdiscountprice(data.optString("deal_discount_price"));
                                listModel.setDealimg(Path_deal + "/" + data.optString("deal_display_photo"));
                                listModel.setDealimgname(data.optString("deal_display_photo"));
                                listModel.setDealday(data.optString("days"));
                                listModel.setDealDate(data.optString("deal_date"));
                                listModel.setRefundablePolicy(data.optString("refundable_policy"));
                                listModel.setShowPercentage(data.optString("show_percentage"));
                                tvstartime.setText(Customutils.dateFormat(data.optString("deal_available_from")));
                                tvendtime.setText(Customutils.dateFormat(data.optString("deal_available_to")));
                                //   tvactualprice.setText("\u20B9" + (data.optString("deal_price")));
                                //   tvfinalamount.setText("\u20B9" + (data.optString("deal_discount_price")));

                                arrayList.add(j, listModel);

                                tvbuy.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (Qtycount > 0) {
                                            Intent i = new Intent(DetailNewActivity.this, SingleOrderActivity.class);
                                            HotDealsModel hotDealsModel=new HotDealsModel();
                                            hotDealsModel.setOutletName(stroutletname);
                                            i.putExtra("Dealid", pos);
                                            i.putExtra("Qtycount", Qtycount);
                                            i.putExtra("Fromdiscount", Fromdiscount);
                                            i.putExtra("Dealimgname",listModel.getDealimg());
                                            System.out.println("deal image "+listModel.getDealimg());
                                            i.putExtra("Dealstarttime", Dealstarttime);
                                            i.putExtra("Dealendtime", Dealendtime);
                                            i.putExtra("path", Path_deal + "/" );
                                            i.putExtra("order_list", SessionManager.setRecent1(orderarrayList, DetailNewActivity.this));
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please choose any order", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                // TODO: 11/13/2019 add gift functionality here
                                tvGift.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (Qtycount > 0) {
                                            giftPopup(listModel.getDealimg(),listModel.getDealimg(),Path_deal);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please choose any order", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                        try {
                            JSONObject jSONObject1 = new JSONObject(new String(response));
                            //   JSONObject jSONObject = new JSONObject(new String(""));
                            similararrayList.clear();
                            int Status1 = jSONObject1.optInt("status");
                            String Path1 = Constaints.BaseUrl + jSONObject1.optString("dealsCdnpath");
                            //       dealsModel.setDealsName(Constaints.BaseUrl + merchant.optString("dealsCdnpath"));
                            if (Status == 1) {
                                JSONArray infoArray1 = jSONObject.getJSONArray("similarDeals");

                                for (int i = 0; i < infoArray1.length(); i++) {
                                    JSONObject outlet = infoArray1.getJSONObject(i);
                                    JSONArray dealArray = outlet.optJSONArray("deals");
                                    //  JSONObject dealArray = outlet.optJSONObject("deals");
                                    JSONArray compaing_array = outlet.optJSONArray("compain");
                                    if (infoArray1.length() > 0) {
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
                                            dealsModel.setDealimage(Path1 + "/" + data.optString("deal_display_photo"));
                                            dealsModel.setLikes(data.optString("like"));
                                            dealsModel.setLikesCount(data.optString("totalLike"));
                                            similararrayList.add(dealsModel);
                                        }

                                    }
                                }
                            } else {
                                //
                                AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                                builder.setTitle("Could Not Find Deal");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                                builder.show();
                            }
                            similaradapter = new SimilairDealsAdaptor(similararrayList, DetailNewActivity.this, DetailNewActivity.this);
                            //  mRecyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(similaradapter);

                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            Log.e("", e.getMessage());
                            progressDialog.dismiss();

                        }


                    } else {
                        //  Toast.makeText(getApplicationContext(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder=new AlertDialog.Builder(DetailNewActivity.this);
                        builder.setTitle("Could Not Find Deal");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();
                            }
                        });
                        builder.create();
                        builder.show();
                    }
                    Intent intent = getIntent();
                    adapter = new OutletsDeallistAdaptorNew(DetailNewActivity.this, arrayList, DetailNewActivity.this,
                            intent.getStringExtra("outletid"));
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    // linearLayoutManager.setStackFromEnd(true);

                    if (!intent.getStringExtra("numberOfOffes").equals("")) {
                        int offers = Integer.parseInt(intent.getStringExtra("numberOfOffes"));

                        if (offers > 1) {
                            viewlist.setClipToPadding(false);
                            viewlist.setPadding(0, 0, 40, 0);
                        } else {
                            viewlist.setPadding(0, 0, 0, 0);
                        }
                    }
                    viewlist.setLayoutManager(linearLayoutManager);
                    viewlist.setAdapter(adapter);
                    CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getApplicationContext(), dealimglist);
                    view_pager.setAdapter(mCustomPagerAdapter);

                    /*After setting the adapter use the timer */
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == dealimglist.size() - 1) {
                                currentPage = 0;

                            }
                            view_pager.setCurrentItem(currentPage++, true);
                        }
                    };

                    if (dealimglist.size()>1){

                        viewlist.setLayoutManager(linearLayoutManager);
                        viewlist.setClipToPadding(false);
                        viewlist.setPadding(0,0,0,0);
                        tvCountBanner.setText(1+"/"+dealimglist.size());

                    view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            tvCountBanner.setText(position+1+"/"+dealimglist.size());
                        }

                        @Override
                        public void onPageSelected(int position) {
                            tvCountBanner.setText(position+1+"/"+dealimglist.size());
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    }else {
                        tvCountBanner.setVisibility(View.GONE);
                    }

                    timer = new Timer(); // This will create a new Thread
                    timer.schedule(new TimerTask() { // task to be scheduled
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, DELAY_MS, PERIOD_MS);
                    //   CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getApplicationContext(), dealimglist);
                    //    view_pager.setAdapter(mCustomPagerAdapter);
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                    progressDialog.dismiss();
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("error", "" + error);
                progressDialog.dismiss();
                AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Could Not Find Deal");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
                builder.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Intent intent = getIntent();
                params.put("XAPIKEY", "XXXXX");
                //    params.put("user_id", SessionManager.getUserID(getApplicationContext()).toString());
                // params.put("id", HotDealsModel.getHotDealsModel().getOutletid());
                params.put("category_id",intent.getStringExtra("category_id"));
                params.put("outlet_id", intent.getStringExtra("outletid"));
                params.put("user_id", SessionManager.getUserID(getApplicationContext()));
                System.out.println("data details "+params);
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

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //   LatLng latLng = new LatLng(13.05241, 80.25082);
        mMap = map;
       /* try {
            LatLng latLng = new LatLng(Double.valueOf(HotDealsModel.getHotDealsModel().getOutletLatitude()), Double.valueOf(HotDealsModel.getHotDealsModel().getOutletLongtitude().toString()));
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            map.addMarker(new MarkerOptions().position(latLng).title("Position"));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            map.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        }
        catch (Exception ee)
        {
        }*/
    }
    String strOid;
    public BroadcastReceiver uiUpdated = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //  String From = intent.getStringExtra("data");
            int discount = intent.getIntExtra("data", 0);
            int Fromprice = intent.getIntExtra("dataprice", 0);
            int Fromitempos = intent.getIntExtra("itempos", 0);
            int Qty = intent.getIntExtra("qty", 0);
            String imgname = intent.getStringExtra("imgname");
            String start_time = intent.getStringExtra("start_time");
            String end_time = intent.getStringExtra("end_time");

            int calculate = intent.getIntExtra("calculate", 0);
            tvfinalamount.setText(discount + "");
            tvactualprice.setText("\u20B9" + Fromprice + "");
            pos = Fromitempos;
            Qtycount = Qty;
            Dealprice = Fromprice;
            Fromdiscount = discount;

            Dealimgname = imgname;
            Dealstarttime = start_time;
            Dealendtime = end_time;
            isReceiverRegistered = true;
            int actual = 0, finalprice = 0;


            orderarrayList = SessionManager.getRecent1(DetailNewActivity.this, intent.getStringExtra("order_array"));

            for (int i = 0; i < orderarrayList.size(); i++) {
                actual = actual + (Integer.parseInt(orderarrayList.get(i).getDealQTY()) * Integer.parseInt(orderarrayList.get(i).getActualprice()));
                finalprice = finalprice + (Integer.parseInt(orderarrayList.get(i).getDealQTY()) * Integer.parseInt(orderarrayList.get(i).getAfterdiscountprice()));

            }
            tvfinalamount.setText("\u20B9"+finalprice + "");
            tvactualprice.setText("\u20B9" + actual + "");
            if (finalprice == 0) {
                tvfinalamount.setVisibility(View.INVISIBLE);
            } else {
                tvfinalamount.setVisibility(View.VISIBLE);
            }
            Fromdiscount = finalprice;
            if (orderarrayList.isEmpty()) {
                //  LLpayment.setVisibility(View.GONE);
                // layoutParams.setMargins(0, 0, 0, 0);
                // scroll_view.setLayoutParams(layoutParams);
                tvactualprice.setPaintFlags(0);
            } else {
                // LLpayment.setVisibility(View.VISIBLE);
                //  layoutParams.setMargins(0, 0, 0, 55);
                //  scroll_view.setLayoutParams(layoutParams);
                tvactualprice.setPaintFlags(tvactualprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (isReceiverRegistered) {
            try {
                getApplication().unregisterReceiver(uiUpdated);
            } catch (IllegalArgumentException e) {
                // Do nothing
            }
            isReceiverRegistered = false;
        }
    }



    public void loadOfflineDeals(final String From) {
        // if (From.equals("PullToRefresh")) {

        String url = Constaints.DealList;
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
                    similararrayList.clear();
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
                            JSONObject outlet = infoArray.getJSONObject(i);
                            JSONArray dealArray = outlet.optJSONArray("deals");
                            //  JSONObject dealArray = outlet.optJSONObject("deals");
                            JSONArray compaing_array = outlet.optJSONArray("compain");
                            if (infoArray.length() > 0) {
                                for (int j = 0; j < dealArray.length(); j++) {
                                    JSONObject data = dealArray.getJSONObject(j);
                                    System.out.println("data= "+compaing_array.getJSONObject(j));

                                    HotDealsModel dealsModel = new HotDealsModel();
                                    dealsModel.setOutletid(outlet.optString("id"));
                                    strOid=""+outlet.optString("id");
                                    dealsModel.setOutletName(outlet.optString("outletName"));
                                    dealsModel.setOutletAddress(outlet.optString("address"));
                                    dealsModel.setOutletstate(outlet.optString("state"));
                                    dealsModel.setOutletCity(outlet.optString("city"));
                                    dealsModel.setOutletzipcode(outlet.optString("zipcode"));
                                    dealsModel.setNumofOffers(outlet.optString("dealCount"));
                                    dealsModel.setOutletcontactperson(outlet.optString("contactPersonName"));
                                    dealsModel.setOutletcontactnumber(outlet.optString("contactNumber"));
                                    dealsModel.setOutletLatitude(outlet.optString("lat"));
                                    dealsModel.setOutletLongtitude(outlet.optString("lng"));
                                    dealsModel.setTndc(outlet.optString("termAndCondition"));
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
                                    similararrayList.add(dealsModel);
                                }
                                for (int k = 0; k < compaing_array.length(); k++) {
                                    try {
                                        JSONObject jsonObject = compaing_array.optJSONObject(k);
                                        HotDealsModel  dealsModel = new HotDealsModel();
                                        System.out.println("data= "+compaing_array.getJSONObject(k));
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
                                        //  similararrayList.add(dealsModel);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } else {
                        //  Toast.makeText(DetailNewActivity.this, jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                    }
                    similaradapter = new SimilairDealsAdaptor(similararrayList, DetailNewActivity.this, DetailNewActivity.this);
                    //  mRecyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(similaradapter);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Log.e("", e.getMessage());

                }
                progressBar.setVisibility(View.GONE);
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
                params.put("user_id", SessionManager.getUserID(DetailNewActivity.this));
              /*  params.put("lat", "" + 18.1608226);
                params.put("lng", "" + 72.99661590000005);*/
                params.put("lat", SessionManager.getLatitude(DetailNewActivity.this));
                params.put("lng", SessionManager.getLongitude(DetailNewActivity.this));
                params.put("city_id", SessionManager.getCityid(DetailNewActivity.this));
                params.put("page", "0");
                System.out.println("data params "+ params);
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
        Volley.newRequestQueue(DetailNewActivity.this).add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        progressBar.setVisibility(View.GONE);
      /*  } else {

            //   List<HotDealsModel> deallist = HotDealsModel.getAlldeals();
            //    arrayList = HotDealsModel.getAlldeals();
            arrayList.clear();
            adapter = new HotDealsAdaptor(arrayList, getActivity());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(adapter);

        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                adapter.onActivityResult(requestCode, resultCode,
                        intent);


            }
        }        super.onActivityResult(requestCode, resultCode, intent);

    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WAKE_LOCK);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FifthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(DetailNewActivity.this, new String[]
                {
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        WAKE_LOCK,
                        READ_PHONE_STATE,
                        ACCESS_NETWORK_STATE
                }, RequestPermissionCode);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Description(), "Description");
        adapter.addFrag(new TermsAndConditiona(), "Terms & Conditions");

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

    View promptView;
    android.app.AlertDialog alert;
    String gifteeMobile;

    public void giftPopup(String dealimgname, String dealImg, String path){
            LayoutInflater layoutInflater = LayoutInflater.from(DetailNewActivity.this);
            promptView = layoutInflater.inflate(R.layout.new_gift_popup, null);
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(DetailNewActivity.this);
            alertDialogBuilder.setView(promptView);
            alertDialogBuilder.setCancelable(true);
            alert = alertDialogBuilder.create();
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alert.show();
            EditText tvmob1 = (EditText) promptView.findViewById(R.id.tvmob1);
            TextView giftButton = (TextView) promptView.findViewById(R.id.tvgift);

            giftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gifteeMobile = tvmob1.getText().toString();
                    if (tvmob1.getText().toString().trim().length() == 10 || tvmob1.getText().toString().trim().length() == 13) {

                        Intent i = new Intent(DetailNewActivity.this, SingleOrderActivity.class);
                        HotDealsModel hotDealsModel = new HotDealsModel();
                        hotDealsModel.setOutletName(stroutletname);
                        i.putExtra("Dealid", pos);
                        i.putExtra("Qtycount", Qtycount);
                        i.putExtra("Fromdiscount", Fromdiscount);
                        i.putExtra("Dealimgname", dealimgname);
                        System.out.println("deal image " + dealImg);
                        i.putExtra("Dealstarttime", Dealstarttime);
                        i.putExtra("Dealendtime", Dealendtime);
                        i.putExtra("path", path + "/");
                        i.putExtra("order_list", SessionManager.setRecent1(orderarrayList, DetailNewActivity.this));
                        i.putExtra("gifteeMobile", gifteeMobile);

                        startActivity(i);
                    }else{
                        tvmob1.setError("Please enter a Valid mobile number");
                    }
                }
            });
    }
}
