package com.hmi.dealsnxt.Model;

import com.orm.SugarRecord;

import java.util.ArrayList;


public class DealDetailsModel extends SugarRecord {

    public static DealDetailsModel dealDetailsModel;
    public String outletid;
    public String deal_id;
    public String userid;
    public String discountpercent;
    public String title;
    public String dealday;
    public String actualprice;
    public String price;
    public String dealcalculatedprice;
    public String qty;
    public String dealTotalAmount;
    public String dealdescription;
    public String dealimgurl;
    public ArrayList<HotDealsModel> hotDealsModels;
    public String dealImge = "";
    public String percent = "";
    public String description = "";
    public String timeFrom = "";
    public String timeTo = "";
    public String deal_date;
    public String refundable_policy;
    public String show_percentage;

    public static DealDetailsModel getDealDetailsModel() {
        return dealDetailsModel;
    }

    public static void setDealDetailsModel(DealDetailsModel dealDetailsModel) {
        DealDetailsModel.dealDetailsModel = dealDetailsModel;
    }

    public String getDeal_date() {
        return deal_date;
    }

    public void setDeal_date(String deal_date) {
        this.deal_date = deal_date;
    }

    public String getRefundable_policy() {
        return refundable_policy;
    }

    public void setRefundable_policy(String refundable_policy) {
        this.refundable_policy = refundable_policy;
    }

    public String getShow_percentage() {
        return show_percentage;
    }

    public void setShow_percentage(String show_percentage) {
        this.show_percentage = show_percentage;
    }

    public String getDealcalculatedprice() {
        return dealcalculatedprice;
    }

    public void setDealcalculatedprice(String dealcalculatedprice) {
        this.dealcalculatedprice = dealcalculatedprice;
    }

    public String getDealQTY() {
        return qty;
    }

    public void setDealQTY(String dealQTY) {
        this.qty = dealQTY;
    }

    public String getDealTotalAmount() {
        return dealTotalAmount;
    }

    public void setDealTotalAmount(String dealTotalAmount) {
        this.dealTotalAmount = dealTotalAmount;
    }

    public String getDealimgurl() {
        return dealimgurl;
    }

    public void setDealimgurl(String dealimgurl) {
        this.dealimgurl = dealimgurl;
    }

    public String getDealdescription() {
        return dealdescription;
    }

    public void setDealdescription(String dealdescription) {
        this.dealdescription = dealdescription;
    }

    public String getOutletid() {
        return outletid;
    }

    public void setOutletid(String outletid) {
        this.outletid = outletid;
    }

    public String getDealid() {
        return deal_id;
    }

    public void setDealid(String dealid) {
        this.deal_id = dealid;
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
        return title;
    }

    public void setDealname(String dealname) {
        this.title = dealname;
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
        return price;
    }

    public void setAfterdiscountprice(String afterdiscountprice) {
        this.price = afterdiscountprice;
    }

    public ArrayList<HotDealsModel> getHotDealsModels() {
        return hotDealsModels;
    }

    public void setHotDealsModels(ArrayList<HotDealsModel> hotDealsModels) {
        this.hotDealsModels = hotDealsModels;
    }

    public String getDealImge() {
        return dealImge;
    }

    public void setDealImge(String dealImge) {
        this.dealImge = dealImge;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }


}
