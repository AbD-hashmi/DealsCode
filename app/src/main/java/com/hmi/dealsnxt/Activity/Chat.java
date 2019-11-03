package com.hmi.dealsnxt.Activity;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hmi.dealsnxt.Adaptor.AllinoneAdaptor;
import com.hmi.dealsnxt.Adaptor.ChatAdapter;
import com.hmi.dealsnxt.HelperClass.Constaints;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.Model.ChatModel;
import com.hmi.dealsnxt.Model.HotDealsModel;
import com.hmi.dealsnxt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLDisplay;

public class Chat extends AppCompatActivity {

    EditText editMessage;
    Button sendButton;
    RecyclerView recyclerView;
    Toolbar toolbar;
    public LinearLayout LLloc;
    public ImageView ivfilter;
    public TextView tvusername, tvTitle;
    public LinearLayout newtoolbar;
    public ImageView imBack, ivmoveup;
    public ImageView ivsearch;
    public ArrayList<ChatModel> arrayList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    private EditText editText;
    private Button button;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        newtoolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        imBack = (ImageView) newtoolbar.findViewById(R.id.imBack);
        tvTitle = (TextView) newtoolbar.findViewById(R.id.tvTitle);
        LLloc = (LinearLayout) newtoolbar.findViewById(R.id.LLloc);
        ivfilter = (ImageView) newtoolbar.findViewById(R.id.ivfilter);
        ivsearch = (ImageView) newtoolbar.findViewById(R.id.ivsearch);
        tvusername = (TextView) newtoolbar.findViewById(R.id.tvusername);
        recyclerView= (RecyclerView) findViewById(R.id.recycleVIew);

        editMessage=(EditText)findViewById(R.id.ed_message);
        sendButton=(Button)findViewById(R.id.btn_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editMessage.getText().toString().trim().equals(""))
                sendMessage();
            }
        });

        tvTitle.setText("Xclusify Chat");
        tvTitle.setVisibility(View.VISIBLE);

        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        linearLayoutManager = new LinearLayoutManager(this);
        LLloc.setVisibility(View.GONE);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivsearch.setVisibility(View.GONE);
        tvusername.setVisibility(View.GONE);


        loadDetailist();
       /* swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  loadOfflineDeals("PullToRefresh");
                loadDetailist();

            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDetailist();
    }

    public void sendMessage(){

        String url = Constaints.sendChat;
        final HashMap<String, String> postParams = new HashMap<String, String>();
        postParams.put("XAPIKEY","XXXXX");
        postParams.put("user_id", SessionManager.getUserID(getApplicationContext()));
        postParams.put("message", editMessage.getText().toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParams),new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("TAG", response.toString());
                        try {
                            //Toast.makeText(mContext, response.getString("message"), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "message Sent", Toast.LENGTH_LONG).show();
                            editMessage.setText("");
                            loadDetailist();
                            if (response.getBoolean("status")) {
                            }
                        } catch (JSONException e) {
                            Log.e("TAG", e.toString());
                        }
                        //pDialog.dismiss();
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("TAG", "Error: " + error.getMessage());

            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        int socketTimeout = 30000;
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public void loadDetailist() {
        String url = Constaints.RecieveChats;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jSONObject = new JSONObject(new String(response));
                    //   JSONObject jSONObject = new JSONObject(new String(""));

                    arrayList.clear();
                    int Status = jSONObject.optInt("status");
                    if (Status == 1) {
                        JSONArray infoArray = jSONObject.getJSONArray("reversed_data");
                        //  scheduleJSONArray = infoArray.toString();
                        ////  JSONArray scheduleArray = new JSONArray(scheduleJSONArray);
                        //  arrayLength = scheduleArray.length();
                        //   arrayList = new ArrayList<>(scheduleArray.length());

                            JSONArray dealArray = jSONObject.optJSONArray("reversed_data");
                            //  JSONObject dealArray = outlet.optJSONObject("deals");
                            if (infoArray.length() > 0) {
                                for (int j = 0; j < dealArray.length(); j++) {
                                    JSONObject data = dealArray.getJSONObject(j);
                                    ChatModel chatModel = new ChatModel();

                                    chatModel.setFrom_user_id(data.optString("from_user_id"));
                                    chatModel.setMessage(data.optString("message"));

                                    System.out.println("data "+ data.optString("message"));

                                    arrayList.add(chatModel);
                                }
                        }
                    } else {
                    }
                    ChatAdapter adapter = new ChatAdapter(arrayList, Chat.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
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
        Volley.newRequestQueue(getApplicationContext()).add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
}