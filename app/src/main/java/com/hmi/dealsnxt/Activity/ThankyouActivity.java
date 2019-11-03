package com.hmi.dealsnxt.Activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmi.dealsnxt.R;

public class ThankyouActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ThankyouActivity.this,Dashboard.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);


        final TextView tvtransaction = (TextView) findViewById(R.id.tvtransaction);
        final TextView tvQRcode = (TextView) findViewById(R.id.tvQRcode);
        final ImageView ivQRnew = (ImageView)findViewById(R.id.ivQRnew);
        final TextView tvsubmit = (TextView) findViewById(R.id.tvsubmit);
        final TextView ins = (TextView) findViewById(R.id.ins);

       String trans = getIntent().getExtras().get("trans_id").toString();
        Bitmap qr= (Bitmap) getIntent().getParcelableExtra("qrCode");


        tvtransaction.setText(trans);
        ivQRnew.setImageBitmap(qr);
        tvsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThankyouActivity.this, OrderActivity.class);
                startActivity(i);
            }
        });
    }
}
