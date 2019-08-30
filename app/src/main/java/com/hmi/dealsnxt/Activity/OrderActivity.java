package com.hmi.dealsnxt.Activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.hmi.dealsnxt.Adaptor.OrderBookedAdaptor;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderActivity extends FragmentActivity {


    RecyclerView recycleVIew;
    public static ProgressBar progressBar;
    public List<OrderModel> arrayList = new ArrayList<>();
    // public LinearLayout LLloc;
    public ImageView ivfilter;
    public TextView tvusername, tvTitle;
    public LinearLayout newtoolbar;
    public ImageView imBack, ivmoveup;
    public ImageView ivsearch;
    private SwipeRefreshLayout swipeContainer;
    public LinearLayout LLfooter, LLfooterHome, LLfooterOrder, LLfooterProfile, LLfootermore, LLfooterFavourite, LLloc;
    public TextView tvHome, tvOrder, tvProfile, tvFavourite, tvmore;
    public ImageView ivHome, ivOrder, ivProfile, ivFavourite, ivmore;
    public DrawerLayout drawer;
    LinearLayout LLshare, LLNotification, LLRefer, LLPrivacy, LLUse, LLrate, LLAbout, LLcontact, LLlogout;
    LinearLayout LLpopulardeals, LLproximity, LLtrading, LLorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
/*        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);      ;*/

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_order);
        recycleVIew = (RecyclerView) findViewById(R.id.recycleVIew);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        newtoolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        imBack = (ImageView) newtoolbar.findViewById(R.id.imBack);
        tvTitle = (TextView) newtoolbar.findViewById(R.id.tvTitle);
        LLloc = (LinearLayout) newtoolbar.findViewById(R.id.LLloc);
        ivfilter = (ImageView) newtoolbar.findViewById(R.id.ivfilter);
        ivsearch = (ImageView) newtoolbar.findViewById(R.id.ivsearch);
        tvusername = (TextView) newtoolbar.findViewById(R.id.tvusername);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        LLfooter = (LinearLayout) findViewById(R.id.LLfooter);
        LLfooterHome = (LinearLayout) LLfooter.findViewById(R.id.LLfooterHome);
        LLfooterOrder = (LinearLayout) LLfooter.findViewById(R.id.LLfooterOrder);
        LLfooterProfile = (LinearLayout) LLfooter.findViewById(R.id.LLfooterProfile);
        LLfooterFavourite = (LinearLayout) LLfooter.findViewById(R.id.LLfooterFavourite);
        LLfootermore = (LinearLayout) LLfooter.findViewById(R.id.LLfootermore);
        tvHome = (TextView) LLfooter.findViewById(R.id.tvHome);
        tvOrder = (TextView) LLfooter.findViewById(R.id.tvOrder);
        tvProfile = (TextView) LLfooter.findViewById(R.id.tvProfile);
        tvFavourite = (TextView) LLfooter.findViewById(R.id.tvFavourite);
        tvmore = (TextView) LLfooter.findViewById(R.id.tvmore);
        ivHome = (ImageView) LLfooter.findViewById(R.id.ivHome);
        ivOrder = (ImageView) LLfooter.findViewById(R.id.ivOrder);
        ivProfile = (ImageView) LLfooter.findViewById(R.id.ivProfile);
        ivFavourite = (ImageView) LLfooter.findViewById(R.id.ivFavourite);
        ivmore = (ImageView) LLfooter.findViewById(R.id.ivmore);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        LLloc.setVisibility(View.GONE);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivsearch.setVisibility(View.GONE);
        tvusername.setVisibility(View.GONE);
        tvTitle.setText("My Orders");
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findviewbyDrawer();
        loadDetailist("");
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDetailist("PullToRefresh");
            }
        });

        ivmoveup = (ImageView) findViewById(R.id.ivmoveup);
        ivmoveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recycleVIew.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });


        LLfooterHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.active_home));
                ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
                ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.inactive_profile));
                ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));

                tvHome.setTextColor(getResources().getColor(R.color.redcolor));
                tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvProfile.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
                ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
                tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));
                Intent i = new Intent(OrderActivity.this, LandingNewActivity.class);
                startActivity(i);
            }
        });

        LLfooterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
                ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
                ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.active_profile));
                ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));
                tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvProfile.setTextColor(getResources().getColor(R.color.redcolor));
                tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
                ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
                tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));
                Intent i = new Intent(OrderActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        LLfooterFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
                ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
                ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.inactive_profile));
                ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.active_favorite));
                tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvProfile.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvFavourite.setTextColor(getResources().getColor(R.color.redcolor));
                ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
                tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));
                //    Intent i = new Intent(OrderActivity.this, LandingNewActivity.class);
                //     startActivity(i);
            }
        });
        LLfootermore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
                ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.inactive_order));
                ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.inactive_profile));
                ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));
                tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvOrder.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvProfile.setTextColor(getResources().getColor(R.color.greyfontcol));
                tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
                ivmore.setImageDrawable(getResources().getDrawable(R.drawable.active_more));
                tvmore.setTextColor(getResources().getColor(R.color.redcolor));
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
    }

    private void findviewbyDrawer() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvmore = (TextView) findViewById(R.id.tvmore);
        LLorder = (LinearLayout) findViewById(R.id.LLorder);
        LLtrading = (LinearLayout) findViewById(R.id.LLtrading);
        LLproximity = (LinearLayout) findViewById(R.id.LLproximity);
        LLpopulardeals = (LinearLayout) findViewById(R.id.LLpopulardeals);
        LLshare = (LinearLayout) findViewById(R.id.LLshare);
        LLNotification = (LinearLayout) findViewById(R.id.LLNotification);
        LLRefer = (LinearLayout) findViewById(R.id.LLRefer);
        LLPrivacy = (LinearLayout) findViewById(R.id.LLPrivacy);
        LLUse = (LinearLayout) findViewById(R.id.LLUse);
        LLrate = (LinearLayout) findViewById(R.id.LLrate);
        LLAbout = (LinearLayout) findViewById(R.id.LLAbout);
        LLcontact = (LinearLayout) findViewById(R.id.LLcontact);
        LLlogout = (LinearLayout) findViewById(R.id.LLlogout);

        tvmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });
        LLorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, OrderActivity.class);
                startActivity(i);

                drawer.closeDrawers();
            }
        });
        LLtrading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });
        LLproximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, NearByActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLpopulardeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });
        LLshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("android.intent.action.SEND");
                i.setType("text/plain");
                i.putExtra("android.intent.extra.TEXT", "Enjoy deals around you. Download here+ App:- https://play.google.com/store/apps/details?id=com.mhi.dealnxt");
                startActivity(Intent.createChooser(i, "Share App"));
                drawer.closeDrawers();
            }
        });
        LLNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OrderActivity.this, NotificationActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mhi.dealnxt"));
                    startActivity(i);
                } catch (ActivityNotFoundException error) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mhi.dealnxt"));
                    startActivity(i);
                }
                drawer.closeDrawers();
            }
        });
        LLlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(OrderActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Alert");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Successfully Logout", Toast.LENGTH_SHORT).show();
                        SessionManager.setIsRegistered(getApplicationContext(), false);
                        SharedPreferences preferences = getSharedPreferences("uid", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        SessionManager.setIsRegistered(getApplicationContext(), false);
                        SessionManager.setIssignup(getApplicationContext(), false);
                        SessionManager.setLatitude(getApplicationContext(), "");
                        SessionManager.setLongitude(getApplicationContext(), "");
                        SessionManager.setUserID(getApplicationContext(), "");
                        SessionManager.setIsloc(getApplicationContext(), false);
                        SessionManager.setIstut(getApplicationContext(), true);
                        SessionManager.setIsotp(getApplicationContext(), false);
                        editor.clear();
                        editor.commit();
                        finish();
                        Intent i = new Intent(OrderActivity.this, OTPActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
                drawer.closeDrawers();
                drawer.openDrawer(GravityCompat.END);
            }
        });
        LLRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, RefertofrndActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, PrivacypolicyActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, TncActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, AboutUsActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        LLcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, ContactUsActivity.class);
                startActivity(i);
                drawer.closeDrawers();
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
                        Toast.makeText(getApplicationContext(), jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                        swipeContainer.setRefreshing(false);
                    }
                    OrderBookedAdaptor adapter = new OrderBookedAdaptor(arrayList, OrderActivity.this, OrderActivity.this);
                    recycleVIew.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivHome.setImageDrawable(getResources().getDrawable(R.drawable.inactive_home));
        ivOrder.setImageDrawable(getResources().getDrawable(R.drawable.active_order));
        ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.inactive_profile));
        ivFavourite.setImageDrawable(getResources().getDrawable(R.drawable.inactive_favorite));
        ivmore.setImageDrawable(getResources().getDrawable(R.drawable.inactive_more));
        tvHome.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvOrder.setTextColor(getResources().getColor(R.color.redcolor));
        tvProfile.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvFavourite.setTextColor(getResources().getColor(R.color.greyfontcol));
        tvmore.setTextColor(getResources().getColor(R.color.greyfontcol));
    }
}