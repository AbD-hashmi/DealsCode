package com.hmi.dealsnxt.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class AftersplashActivity extends AppCompatActivity  {
    // TextView tvstart;

    public static ViewPager view_pager;
    ImageView ivbanner;
    //  int[] listbanner = {R.drawable.banner01, R.drawable.banner02, R.drawable.banner03};
    int[] listicon = {R.drawable.circle_border_red};


    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    public TextView tvstart;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private SessionManager prefManager;
    private TextView tvtheme;
    private View view;
    private LinearLayout LLimage;
    public ImageView iv1, iv2, iv3;
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    DisplayImageOptions options;



    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersplash);
         DisplayImageOptions defaultOptions;

        tvstart = (TextView) findViewById(R.id.tvstart);
        changeStatusBarColor();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.circle_border_red)
                .showImageOnLoading(R.drawable.circle_border_red)
                .showStubImage(R.drawable.circle_border_red)
                .showImageForEmptyUri(R.drawable.circle_border_red)
                .showImageOnFail(R.drawable.circle_border_red)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));

        prefManager = new SessionManager(getApplicationContext());


        setContentView(R.layout.activity_aftersplash);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tvstart = (TextView) findViewById(R.id.tvstart);
        dotsLayout = (LinearLayout) findViewById(R.id.LLimage);

        view = (View) findViewById(R.id.view);

        layouts = new int[]{
                R.layout.slide1,
                R.layout.slide2,
                R.layout.slide3};


        addBottomDots(0);


        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);



        tvstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchHomeScreen();

            }
        });


    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
       //   prefManager.setFirstTimeLaunch(true);
        //  SessionManager.setIsRegistered(getApplicationContext(), false);
        startActivity(new Intent(AftersplashActivity.this, OTPActivity.class));
        finish();
        SessionManager.setIstut(getApplicationContext(),true);
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}
