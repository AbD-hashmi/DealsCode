package com.hmi.dealsnxt.Model;

import com.orm.SugarRecord;

import java.util.ArrayList;


public class DealsDetailModel extends SugarRecord {


    public String userid;
    public String outletLikes;
    public String outletname;
    public String outletaddress;
    public String outlettime_in;
    public String outlettime_out;
    public String outlet_contactperson;
    public String outlet_contact;
    public String outlet_distance;
    public String outletid;


    public String discountpercent;
    public String dealname;

    public String getDiscountpercent() {
        return discountpercent;
    }

    public void setDiscountpercent(String discountpercent) {
        this.discountpercent = discountpercent;
    }

    public String getDealname() {
        return dealname;
    }

    public void setDealname(String dealname) {
        this.dealname = dealname;
    }

    public String getDealday() {
        return dealday;
    }

    public void setDealday(String dealday) {
        this.dealday = dealday;
    }

    public String getActualprice() {
        return actualprice;
    }

    public void setActualprice(String actualprice) {
        this.actualprice = actualprice;
    }

    public String getAfterdiscountprice() {
        return afterdiscountprice;
    }

    public void setAfterdiscountprice(String afterdiscountprice) {
        this.afterdiscountprice = afterdiscountprice;
    }

    public String getNumberofitem() {
        return numberofitem;
    }

    public void setNumberofitem(String numberofitem) {
        this.numberofitem = numberofitem;
    }

    public String dealday;
    public String actualprice;
    public String afterdiscountprice;
    public String numberofitem;

    public ArrayList<HotDealsModel> getHotDealsModels() {
        return hotDealsModels;
    }

    public void setHotDealsModels(ArrayList<HotDealsModel> hotDealsModels) {
        this.hotDealsModels = hotDealsModels;
    }

    public ArrayList<HotDealsModel> hotDealsModels;

    public DealsDetailModel() {
    }

    public String dealid;

    public String getDealid() {
        return dealid;
    }

    public void setDealid(String dealid) {
        this.dealid = dealid;
    }

    public String getOutletid() {
        return outletid;
    }

    public void setOutletid(String outletid) {
        this.outletid = outletid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOutletLikes() {
        return outletLikes;
    }

    public void setOutletLikes(String outletLikes) {
        this.outletLikes = outletLikes;
    }

    public String getOutletname() {
        return outletname;
    }

    public void setOutletname(String outletname) {
        this.outletname = outletname;
    }

    public String getOutletaddress() {
        return outletaddress;
    }

    public void setOutletaddress(String outletaddress) {
        this.outletaddress = outletaddress;
    }

    public String getOutlettime_in() {
        return outlettime_in;
    }

    public void setOutlettime_in(String outlettime_in) {
        this.outlettime_in = outlettime_in;
    }

    public String getOutlettime_out() {
        return outlettime_out;
    }

    public void setOutlettime_out(String outlettime_out) {
        this.outlettime_out = outlettime_out;
    }

    public String getOutlet_contactperson() {
        return outlet_contactperson;
    }

    public void setOutlet_contactperson(String outlet_contactperson) {
        this.outlet_contactperson = outlet_contactperson;
    }

    public String getOutlet_contact() {
        return outlet_contact;
    }

    public void setOutlet_contact(String outlet_contact) {
        this.outlet_contact = outlet_contact;
    }

    public String getOutlet_distance() {
        return outlet_distance;
    }

    public void setOutlet_distance(String outlet_distance) {
        this.outlet_distance = outlet_distance;
    }






}
