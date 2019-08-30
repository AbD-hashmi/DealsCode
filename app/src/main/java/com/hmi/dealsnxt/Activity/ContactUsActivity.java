package com.hmi.dealsnxt.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;

public class ContactUsActivity extends AppCompatActivity {
    ImageView imBack, imlocation, ivfilter;
    TextView tvTitle, tvaddress, tvusername, tvsubmit, tvvname;
    ImageView ivsearch;
    LinearLayout LLloc;
    TextView etmobile, etaddress;
    EditText etQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
       /* View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }*/
        final LinearLayout toolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        LLloc = (LinearLayout) toolbar.findViewById(R.id.LLloc);
        imBack = (ImageView) toolbar.findViewById(R.id.imBack);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        LLloc = (LinearLayout) toolbar.findViewById(R.id.LLloc);
        imlocation = (ImageView) toolbar.findViewById(R.id.imlocation);
        tvaddress = (TextView) toolbar.findViewById(R.id.tvaddress);
        ivfilter = (ImageView) toolbar.findViewById(R.id.ivfilter);
        tvsubmit = (TextView) findViewById(R.id.tvsubmit);
        ivsearch = (ImageView) toolbar.findViewById(R.id.ivsearch);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        imlocation.setVisibility(View.GONE);
        tvaddress.setVisibility(View.GONE);
        ivfilter.setVisibility(View.GONE);
        ivsearch.setVisibility(View.GONE);
        tvusername = (TextView) toolbar.findViewById(R.id.tvusername);
        String test = SessionManager.getUserName(getApplicationContext()).toString();

        tvusername.setText(test);
        tvusername.setVisibility(View.INVISIBLE);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("Contact Us");

        etmobile = (TextView) findViewById(R.id.etmobile);
        etaddress = (TextView) findViewById(R.id.etaddress);
        //     etname = (EditText) toolbar.findViewById(R.id.etname);
        etQuery = (EditText) findViewById(R.id.etQuery);

        tvvname = (TextView) findViewById(R.id.tvvname);
        tvvname.setText(SessionManager.getUserName(getApplicationContext()));

        tvsubmit.setVisibility(View.INVISIBLE);
        etQuery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etQuery.setFocusable(true);
                etQuery.setFocusableInTouchMode(true);
                tvsubmit.setVisibility(View.VISIBLE);
                return false;
            }
        });

    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
