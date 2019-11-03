package com.hmi.dealsnxt.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hmi.dealsnxt.HelperClass.RegisValidation;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;

public class RefertofrndActivity extends AppCompatActivity {
    ImageView imBack, imlocation, ivfilter;
    TextView tvTitle, tvaddress, tvusername, save;
    ImageView ivsearch;
    LinearLayout LLloc;
    LinearLayout LLbanner;
    TextView tvcoupondetail, tvtext;
    ImageView QRcode;
    EditText etmobileval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refertofrnd);
        final LinearLayout toolbar = (LinearLayout) findViewById(R.id.toolbarnew);
        LLloc = (LinearLayout) toolbar.findViewById(R.id.LLloc);
        imBack = (ImageView) toolbar.findViewById(R.id.imBack);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        LLloc = (LinearLayout) toolbar.findViewById(R.id.LLloc);
        imlocation = (ImageView) toolbar.findViewById(R.id.imlocation);
        tvaddress = (TextView) toolbar.findViewById(R.id.tvaddress);
        ivfilter = (ImageView) toolbar.findViewById(R.id.ivfilter);
        ivsearch = (ImageView) toolbar.findViewById(R.id.ivsearch);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        imlocation.setVisibility(View.GONE);
        tvaddress.setVisibility(View.GONE);
        ivfilter.setVisibility(View.GONE);
        ivsearch.setVisibility(View.GONE);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("Refer to Friend");
        etmobileval = (EditText) toolbar.findViewById(R.id.etmobileval);
        save = (TextView) findViewById(R.id.save);
        tvusername= (TextView) toolbar.findViewById(R.id.tvusername);
        String test = SessionManager.getUserName(getApplicationContext()).toString();

        tvusername.setText(test);
        tvusername.setVisibility(View.INVISIBLE);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "Download Deal next and user referer code NNALNF and get 40% off on all deals ";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download Deal next");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
               /* if (checkValidation()) {
                    String shareBody = "Here is the share content body";
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(RefertofrndActivity.this, "Please enter Valid Mobile Number", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    public boolean checkValidation() {
        boolean ret = true;
        etmobileval.setError(null);
        if (!etmobileval.getText().toString().equals("")) {
            if (!RegisValidation.hasMobile(etmobileval)) {
                ret = false;
            }
        } else {
            etmobileval.setError("Please enter valid mobile number of 10 digits ");
            ret = false;
        }
        return ret;
    }

}
