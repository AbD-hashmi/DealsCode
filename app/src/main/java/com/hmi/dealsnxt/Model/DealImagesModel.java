package com.hmi.dealsnxt.Model;


import android.net.Uri;

import com.orm.SugarRecord;

import java.util.List;


public class DealImagesModel extends SugarRecord {

    public String images_url;
    public String image_ID;
    public String image_name;
    public String outlet_id;

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String deal_id;

    public String getImages_url() {
        return images_url;
    }

    public void setImages_url(String images_url) {
        this.images_url = images_url;
    }

    public String getImage_ID() {
        return image_ID;
    }

    public void setImage_ID(String image_ID) {
        this.image_ID = image_ID;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }




}
