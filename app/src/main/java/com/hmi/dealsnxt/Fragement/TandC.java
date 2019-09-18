package com.hmi.dealsnxt.Fragement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmi.dealsnxt.R;

public class TandC extends Fragment {
    TextView no_result;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.textview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            no_result= (TextView) getView().findViewById(R.id.text);
            no_result.setText(prefs.getString("terms", "No Terms and conditions Found"));
        }
//        String s=getArguments().getString("terms", "none");
        //      no_result= (TextView) getView().findViewById(R.id.text);
        //    no_result.setText(s);


    }
    @Override
    public void onResume() {
        super.onResume();

    }
}