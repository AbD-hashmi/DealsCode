package com.hmi.dealsnxt.Model;

import com.orm.SugarRecord;

import java.util.ArrayList;


public class ListModel extends SugarRecord {

    public String outletid;
    public String dealid;
    public String userid;
    public String discountpercent;
    public String dealname;
    public String dealday;
    public String actualprice;
    public String afterdiscountprice;
    public String numberofitem;
    public ArrayList<HotDealsModel> hotDealsModels;
    public String outletname;
    public String outletstate;
    public String outletLatitude;
    public String dealDate;
    public String refundablePolicy;
    public String showPercentage;

    public String getOutletLatitude() {
        return outletLatitude;
    }

    public void setOutletLatitude(String outletLatitude) {
        this.outletLatitude = outletLatitude;
    }

    public String getOutletLongtitude() {
        return outletLongtitude;
    }

    public void setOutletLongtitude(String outletLongtitude) {
        this.outletLongtitude = outletLongtitude;
    }

    public String outletLongtitude;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String count;


    public static ListModel getcount(String count) {
        return ListModel.find(ListModel.class, "dealid = ?",new  String(count)).get(0);
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getRefundablePolicy() {
        return refundablePolicy;
    }

    public void setRefundablePolicy(String refundablePolicy) {
        this.refundablePolicy = refundablePolicy;
    }

    public String getShowPercentage() {
        return showPercentage;
    }

    public void setShowPercentage(String showPercentage) {
        this.showPercentage = showPercentage;
    }

    public String getDealimg() {
        return Dealimg;
    }

    public void setDealimg(String dealimg) {
        Dealimg = dealimg;
    }

    public String Dealimg;

    public String getDealimgname() {
        return Dealimgname;
    }

    public void setDealimgname(String dealimgname) {
        Dealimgname = dealimgname;
    }

    public String Dealimgname="";

    public static ListModel getListModel() {
        return listModel;
    }

    public static void setListModel(ListModel listModel) {
        ListModel.listModel = listModel;
    }

    public static ListModel listModel;

    public String getTimein() {
        return timein;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String timein;
    public String timeout;
    public String desciption;

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public String outletAddress;

    public String getOutletname() {
        return outletname;
    }

    public void setOutletname(String outletname) {
        this.outletname = outletname;
    }

    public String getOutletstate() {
        return outletstate;
    }

    public void setOutletstate(String outletstate) {
        this.outletstate = outletstate;
    }

    public String getOutletcountry() {
        return outletcountry;
    }

    public void setOutletcountry(String outletcountry) {
        this.outletcountry = outletcountry;
    }

    public String getOutletzipcode() {
        return outletzipcode;
    }

    public void setOutletzipcode(String outletzipcode) {
        this.outletzipcode = outletzipcode;
    }

    public String getOutletdistancefrompresent() {
        return outletdistancefrompresent;
    }

    public void setOutletdistancefrompresent(String outletdistancefrompresent) {
        this.outletdistancefrompresent = outletdistancefrompresent;
    }

    public String getTndc() {
        return tndc;
    }

    public void setTndc(String tndc) {
        this.tndc = tndc;
    }

    public String getOutletintime() {
        return outletintime;
    }

    public void setOutletintime(String outletintime) {
        this.outletintime = outletintime;
    }

    public String getOutletouttime() {
        return outletouttime;
    }

    public void setOutletouttime(String outletouttime) {
        this.outletouttime = outletouttime;
    }

    public String outletcountry;
    public String outletzipcode;
    public String outletdistancefrompresent;
    public String tndc;
    public String outletintime;
    public String outletouttime;


    public ArrayList<DealsDetailModel> getDealsDetailModels() {
        return dealsDetailModels;
    }

    public void setDealsDetailModels(ArrayList<DealsDetailModel> dealsDetailModels) {
        this.dealsDetailModels = dealsDetailModels;
    }

    public ArrayList<DealsDetailModel> dealsDetailModels;


    public String getOutletid() {
        return outletid;
    }

    public void setOutletid(String outletid) {
        this.outletid = outletid;
    }

    public String getDealid() {
        return dealid;
    }

    public void setDealid(String dealid) {
        this.dealid = dealid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

    public ArrayList<HotDealsModel> getHotDealsModels() {
        return hotDealsModels;
    }

    public void setHotDealsModels(ArrayList<HotDealsModel> hotDealsModels) {
        this.hotDealsModels = hotDealsModels;
    }








}
