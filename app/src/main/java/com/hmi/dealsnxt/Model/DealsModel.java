package com.hmi.dealsnxt.Model;

import com.orm.SugarRecord;

import java.util.ArrayList;


public class DealsModel extends SugarRecord {

    public String outletid;
    public String userid;
    public String outletLikes;
    public String outletname;
    public String outletaddress;
    public String outlettime_in;
    public String outlettime_out;
    public String outlet_contactperson;
    public String outlet_contact;
    public String outlet_distance;
    public String dealid;
    public String dealname;
    public String dealtitle;
    public String dealavailtime_to;
    public String dealavailtime_from;
    public String dealdisplayphoto;
    public String dealcoverphoto;
    public String dealdescription;
    public String dealapplicableat;

    public String getDealactualprice() {
        return dealactualprice;
    }

    public void setDealactualprice(String dealactualprice) {
        this.dealactualprice = dealactualprice;
    }

    public String getDealafterdiscountprice() {
        return dealafterdiscountprice;
    }

    public void setDealafterdiscountprice(String dealafterdiscountprice) {
        this.dealafterdiscountprice = dealafterdiscountprice;
    }

    public String getDealdiscountpernt() {
        return dealdiscountpernt;
    }

    public void setDealdiscountpernt(String dealdiscountpernt) {
        this.dealdiscountpernt = dealdiscountpernt;
    }

    public String getDealcount() {
        return dealcount;
    }

    public void setDealcount(String dealcount) {
        this.dealcount = dealcount;
    }

    public String dealactualprice;
    public String dealafterdiscountprice;
    public String dealdiscountpernt;
    public String dealcount;

    public String getDealid() {
        return dealid;
    }

    public void setDealid(String dealid) {
        this.dealid = dealid;
    }

    public String getDealname() {
        return dealname;
    }

    public void setDealname(String dealname) {
        this.dealname = dealname;
    }

    public String getDealtitle() {
        return dealtitle;
    }

    public void setDealtitle(String dealtitle) {
        this.dealtitle = dealtitle;
    }

    public String getDealavailtime_to() {
        return dealavailtime_to;
    }

    public void setDealavailtime_to(String dealavailtime_to) {
        this.dealavailtime_to = dealavailtime_to;
    }

    public String getDealavailtime_from() {
        return dealavailtime_from;
    }

    public void setDealavailtime_from(String dealavailtime_from) {
        this.dealavailtime_from = dealavailtime_from;
    }

    public String getDealdisplayphoto() {
        return dealdisplayphoto;
    }

    public void setDealdisplayphoto(String dealdisplayphoto) {
        this.dealdisplayphoto = dealdisplayphoto;
    }

    public String getDealcoverphoto() {
        return dealcoverphoto;
    }

    public void setDealcoverphoto(String dealcoverphoto) {
        this.dealcoverphoto = dealcoverphoto;
    }

    public String getDealdescription() {
        return dealdescription;
    }

    public void setDealdescription(String dealdescription) {
        this.dealdescription = dealdescription;
    }

    public String getDealapplicableat() {
        return dealapplicableat;
    }

    public void setDealapplicableat(String dealapplicableat) {
        this.dealapplicableat = dealapplicableat;
    }


    public ArrayList<DealsModel> getHotDealsModels() {
        return hotDealsModels;
    }

    public void setHotDealsModels(ArrayList<DealsModel> hotDealsModels) {
        this.hotDealsModels = hotDealsModels;
    }

    public ArrayList<DealsModel> hotDealsModels;

    public DealsModel() {
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
