package com.hmi.dealsnxt.Fragement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import org.w3c.dom.Text;

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
    DetailNewActivity detailNewActivity;
    String descrip;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.textview, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        no_result=(TextView)view.findViewById(R.id.text);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());

        no_result.setText(sharedPreferences.getString("description", "No description found"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          //  .setText(Html.fromHtml(items.get(position).getDesciption(),Html.FROM_HTML_MODE_COMPACT));
            no_result.setMovementMethod(new ScrollingMovementMethod());

           /* no_result.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    no_result.getParent().requestDisallowInterceptTouchEvent(true);

                    return false;
                }
            });*/


        } else {
           // viewHolder.tvavailtime.setText(items.get(position).getDesciption());
            no_result.setMovementMethod(new ScrollingMovementMethod());

        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
     /*   String s=getArguments().getString("description", "none");
        no_result= (TextView) getView().findViewById(R.id.text);
        no_result.setText(s);
*/

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}