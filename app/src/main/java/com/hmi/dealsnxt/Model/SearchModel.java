package com.hmi.dealsnxt.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kuldeep Saini  on 04-01-2018.
 */

public class SearchModel
{
    public int getOutletid() {
        return outletid;
    }

    public void setOutletid(int outletid) {
        this.outletid = outletid;
    }

    public String getOutlet_name() {
        return outlet_name;
    }

    public void setOutlet_name(String outlet_name) {
        this.outlet_name = outlet_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    int outletid;
    String outlet_name;
    String lat,lng,city_id;




}
