package com.hmi.dealsnxt.Activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hmi.dealsnxt.Adaptor.HotDealsAdaptor;
import com.hmi.dealsnxt.Adaptor.SearchOutletAdapter;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.SearchModel;
import com.hmi.dealsnxt.R;
import com.hmi.dealsnxt.Utils.Common;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static co.kaush.core.util.CoreNullnessUtils.isNotNullOrEmpty;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    private RecyclerView mRecyclerView;
    //  public static RecyclerView.Adapter mAdapter;
    public  ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;
    LinearLayoutManager linearLayoutManager;
    HotDealsAdaptor adapter;
    ImageView ivmoveup;
    SearchOutletAdapter searchOutletAdapter;

    private Disposable _disposable;
    ArrayList<SearchModel> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        searchView = (SearchView) findViewById(R.id.iv_search);
        ImageView imBack= (ImageView) findViewById(R.id.imBack);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        ivmoveup = (ImageView) findViewById(R.id.ivmoveup);

        ivmoveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) Dashboard.htab_appbar.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                if (behavior != null) {
                    behavior.setTopAndBottomOffset(0);
                    //   behavior.onNestedPreScroll(Dashboard.htab_maincontent, Dashboard.htab_appbar, null, 0, 1, new int[2]);
                    //   behavior.onNestedFling(Dashboard.htab_maincontent, Dashboard.htab_appbar, Dashboard.view_pager, 0, 20, true);
                    behavior.onNestedPreFling(Dashboard.htab_maincontent, Dashboard.htab_appbar, null, 60, 60);
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                //  layoutManager.scrollToPositionWithOffset(0, 0);
                //  Dashboard.collapsingToolbarLayout.setFitsSystemWindows(true);

            }
        });
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ImageView im = (ImageView) searchView.findViewById(R.id.search_button);
        im.setImageResource(R.drawable.search_white);
        final SearchView.SearchAutoComplete search_src_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);

        ImageView search_close_btn = (ImageView) findViewById(R.id.search_close_btn);
        ImageView search_mag_icon = (ImageView) findViewById(R.id.search_mag_icon);

        search_close_btn.setImageResource(R.drawable.close_white);
        search_mag_icon.setImageResource(R.drawable.search_white);
        search_src_text.setTextColor(Color.WHITE);
        search_src_text.setHintTextColor(getResources().getColor(R.color.white));
        search_src_text.setHint("Search here");
        _disposable = RxTextView.textChangeEvents(search_src_text)
                .debounce(600, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(changes -> isNotNullOrEmpty(changes.text().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(_getSearchObserver());




        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {


            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Common.hideKeyboard(searchView,SearchActivity.this);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });


    }
    private DisposableObserver<TextViewTextChangeEvent> _getSearchObserver() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {

                Log.e("","--------- onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("","--------- onerror");
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {

                Log.e("Searching for %s", onTextChangeEvent.text().toString());


                loadOfflineDeals(onTextChangeEvent.text().toString());
            }
        };
    }

    public void loadOfflineDeals(final String From) {
        // if (From.equals("PullToRefresh")) {

        String url = Constaints.searchOut;
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
                    arrayList.clear();
                    if (From.equals("PullToRefresh")) {
                        swipeContainer.setRefreshing(false);
                    }
                    arrayList.clear();
                    int Status = jSONObject.optInt("status");

                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("info");


                        for (int i = 0; i < infoArray.length(); i++) {


                            JSONObject searchObject = infoArray.optJSONObject(i);
                            SearchModel sm=new SearchModel();
                            sm.setCity_id(searchObject.optString("city_id"));
                            sm.setLat(searchObject.optString("lat"));
                            sm.setLng(searchObject.optString("lng"));
                            sm.setOutlet_name(searchObject.optString("name"));
                            sm.setOutletid(searchObject.optInt("outlet_id"));
                            arrayList.add(sm);
                        }
                    } else {

                        AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                        builder.setTitle("Deal expired or Removed");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();
                            }
                        });


                        //Toast.makeText(SearchActivity.this, jSONObject.optString("msg"), Toast.LENGTH_LONG).show();
                        swipeContainer.setRefreshing(false);
                    }
                    searchOutletAdapter = new SearchOutletAdapter(SearchActivity.this, arrayList, arrayList, SearchActivity.this);
                    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

                    mRecyclerView.setLayoutManager(mLayoutManager1);

                    mRecyclerView.setAdapter(searchOutletAdapter);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                    swipeContainer.setRefreshing(false);
                    AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Could Not Find Deal");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });

                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                swipeContainer.setRefreshing(false);
                Log.e("error", "" + error);
                AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Deal expired or Removed");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("XAPIKEY", "XXXXX");
                params.put("user_id", SessionManager.getUserID(SearchActivity.this));
              /*  params.put("lat", "" + 18.1608226);
                params.put("lng", "" + 72.99661590000005);*/
                params.put("search_key", From);

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
        Volley.newRequestQueue(SearchActivity.this).add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }
}
