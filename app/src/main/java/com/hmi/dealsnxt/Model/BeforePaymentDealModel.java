package com.hmi.dealsnxt.Model;

import com.orm.SugarRecord;

import java.util.List;


public class BeforePaymentDealModel extends SugarRecord {
    public List<Deal> getDeal() {
        return deal;
    }

    public void setDeal(List<Deal> deal) {
        this.deal = deal;
    }

    public List<Deal> deal;


    public class Deal {

        public String dealtitle;
        public String dealid;

        public String getDealtitle() {
            return dealtitle;
        }

        public void setDealtitle(String dealtitle) {
            this.dealtitle = dealtitle;
        }

        public String getDealid() {
            return dealid;
        }

        public void setDealid(String dealid) {
            this.dealid = dealid;
        }

        public String getDealqty() {
            return dealqty;
        }

        public void setDealqty(String dealqty) {
            this.dealqty = dealqty;
        }

        public String getDealprice() {
            return dealprice;
        }

        public void setDealprice(String dealprice) {
            this.dealprice = dealprice;
        }

        public String getDealpercentoff() {
            return dealpercentoff;
        }

        public void setDealpercentoff(String dealpercentoff) {
            this.dealpercentoff = dealpercentoff;
        }

        public String dealqty;
        public String dealprice;
        public String dealpercentoff;


    }

}
