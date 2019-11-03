package com.hmi.dealsnxt.Activity;

import android.app.Notification;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.hmi.dealsnxt.Fragement.NotificationFragment;
import com.hmi.dealsnxt.Fragement.RedeemCancel;
import com.hmi.dealsnxt.Fragement.UpcommingOrderfragment;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.CityModel;
import com.hmi.dealsnxt.Model.OrderModel;
import com.hmi.dealsnxt.Model.TimeModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.MultiSelectAdapter;
import com.hmi.dealsnxt.Utils.TimeselsectionAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationActivity extends FragmentActivity {


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

    public TabLayout tabLayout;

    int tabnum=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
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

        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);

        tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white),getResources().getColor(R.color.blackfontcolor));
        tabLayout.setupWithViewPager(viewPager);
        //   tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        setupViewPager(viewPager);


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

      /*  ivmoveup = (ImageView) newtoolbar.findViewById(R.id.ivmoveup);
        ivmoveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recycleVIew.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });*/



    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NotificationFragment(), "Alerts");
        adapter.addFrag(new RedeemCancel(), "Promo Codes");

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






}