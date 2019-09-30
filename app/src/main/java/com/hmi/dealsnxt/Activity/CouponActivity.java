package com.hmi.dealsnxt.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

public class CouponActivity extends AppCompatActivity {
    ImageView imBack, imlocation, ivfilter;
    TextView tvTitle, tvaddress, tvusername;
    ImageView ivsearch;
    LinearLayout LLloc;
    LinearLayout LLbanner;
    TextView tvcoupondetail, tvtext;
    ImageView QRcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
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
        tvTitle.setText("Coupon");
        LLbanner = (LinearLayout) findViewById(R.id.LLbanner);
        tvcoupondetail = (TextView) findViewById(R.id.tvcoupondetail);
        QRcode = (ImageView) findViewById(R.id.QRcode);
        tvtext = (TextView) findViewById(R.id.tvtext);
        tvusername= (TextView) toolbar.findViewById(R.id.tvusername);
        String test = SessionManager.getUserName(getApplicationContext()).toString();

        tvusername.setText(test);
        VCard QRdealdeatil = new VCard("123456789");
        Bitmap myBitmap = QRCode.from(QRdealdeatil).withSize(300, 300).bitmap();
        QRcode.setImageBitmap(myBitmap);
    }
}
