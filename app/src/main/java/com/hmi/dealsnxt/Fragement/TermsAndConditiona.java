package com.hmi.dealsnxt.Fragement;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmi.dealsnxt.Activity.DetailNewActivity;
import com.hmi.dealsnxt.R;

public class TermsAndConditiona extends Fragment {
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

        String terms=sharedPreferences.getString("tnc", "No Terms and conditions Found");
        no_result.setText(Html.fromHtml(Html.toHtml(Html.fromHtml(terms))));
        // no_result.setText(sharedPreferences.getString("description", "Description"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //  .setText(Html.fromHtml(items.get(position).getDesciption(),Html.FROM_HTML_MODE_COMPACT));
            no_result.setMovementMethod(new ScrollingMovementMethod());

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