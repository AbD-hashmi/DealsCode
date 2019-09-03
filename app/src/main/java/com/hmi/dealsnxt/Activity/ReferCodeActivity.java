package com.hmi.dealsnxt.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hmi.dealsnxt.HelperClass.Global;
import com.hmi.dealsnxt.HelperClass.SessionManager;
import com.hmi.dealsnxt.R;

public class ReferCodeActivity extends AppCompatActivity {
    EditText etcode;
    TextView tvagree, Skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_refer_code);
     /*   LayoutInflater layoutInflater = LayoutInflater.from(SignInActivity.this);
        promptView = layoutInflater.inflate(R.layout.activity_share, null);
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SignInActivity.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false);
        alert = alertDialogBuilder.create();
        alert.show();*/
        etcode = (EditText) findViewById(R.id.etcode);
        tvagree = (TextView) findViewById(R.id.tvagree);
        Skip = (TextView) findViewById(R.id.Skip);

        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReferCodeActivity.this, LocationActivity.class);
                startActivity(i);
                // SessionManager.setrefercode(getApplicationContext(), true);
                finish();
            }
        });

        tvagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation_share()) {
                    if (Global.isInternetAvail(ReferCodeActivity.this)) {
                        SessionManager.setIssignup(getApplicationContext(), true);
                        SessionManager.setrefercode(getApplicationContext(), true);
                        Intent i = new Intent(ReferCodeActivity.this, LocationActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.ConnectionErrorResponse, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean checkValidation_share() {
        boolean ret = true;
        etcode.setError(null);
        if (etcode.getText().toString().equals("")) {
            etcode.setError("Please provide Refer Code");
            ret = false;
        }
        return ret;
    }

    @Override
    protected void onResume() {
        SessionManager.setrefercode(getApplicationContext(), false);
        super.onResume();
    }
}
