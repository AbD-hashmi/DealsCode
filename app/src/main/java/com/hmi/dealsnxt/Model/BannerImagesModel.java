package com.hmi.dealsnxt.Model;


import com.orm.SugarRecord;


public class BannerImagesModel extends SugarRecord {

    public String bannerimages_url;

    public String getBannerimages_url() {
        return bannerimages_url;
    }

    public void setBannerimages_url(String bannerimages_url) {
        this.bannerimages_url = bannerimages_url;
    }

    public String getBannerimage_ID() {
        return bannerimage_ID;
    }

    public void setBannerimage_ID(String bannerimage_ID) {
        this.bannerimage_ID = bannerimage_ID;
    }

    public String getBannerimage_name() {
        return bannerimage_name;
    }

    public void setBannerimage_name(String bannerimage_name) {
        this.bannerimage_name = bannerimage_name;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String bannerimage_ID;
    public String bannerimage_name;
    public String banner_id;
    public String getBanner_outletid() {
        return banner_outletid;
    }

    public void setBanner_outletid(String banner_outletid) {
        this.banner_outletid = banner_outletid;
    }

    public String banner_outletid;


}
