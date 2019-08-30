package com.hmi.dealsnxt.Model;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;


public class FoodDrinkModel extends SugarRecord {
    public FoodDrinkModel() {
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String merchantid;
    public String outletstate;
    public String outletcountry;
    public String outletzipcode;
    public String outletdistancefrompresent;
    public String tndc;
    public String outletcontactperson;
    public String outletcontactnumber;
    public String outletAddress;

    public String getOutletdescription() {
        return outletdescription;
    }

    public void setOutletdescription(String outletdescription) {
        this.outletdescription = outletdescription;
    }

    public String outletdescription;
    public String ReminderTime;
    public String Location;
    public String AvailibiltyTime;
    public String AvailibiltyDate;
    public String ActualPrice;
    public String AfterDiscountPrice;
    public String dealTitle;
    public int dealid;
    public String outletid;

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

    public String outletLatitude;
    public String outletLongtitude;


    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        Likes = likes;
    }

    public String getLikesCount() {
        return LikesCount;
    }

    public void setLikesCount(String likesCount) {
        LikesCount = likesCount;
    }

    public String Likes;
    public String LikesCount;
    public int Discount;
    public String outletName;
    public String Dealimage;
    public String Percentage;
    public int NumofOffers;

    public String getDealdescription() {
        return Dealdescription;
    }

    public void setDealdescription(String dealdescription) {
        Dealdescription = dealdescription;
    }

    public String Dealdescription;

    public String getOutletCity() {
        return outletCity;
    }

    public void setOutletCity(String outletCity) {
        this.outletCity = outletCity;
    }

    public String outletCity;


    public ArrayList<DealImagesModel> getUserImages() {
        return userImages;
    }

    public void setUserImages(ArrayList<DealImagesModel> userImages) {
        this.userImages = userImages;
    }

    public ArrayList<DealImagesModel> userImages;

    public String getAvailibiltyDate() {
        return AvailibiltyDate;
    }

    public void setAvailibiltyDate(String availibiltyDate) {
        AvailibiltyDate = availibiltyDate;
    }

    public String getOutletcontactperson() {
        return outletcontactperson;
    }

    public void setOutletcontactperson(String outletcontactperson) {
        this.outletcontactperson = outletcontactperson;
    }

    public String getOutletcontactnumber() {
        return outletcontactnumber;
    }

    public void setOutletcontactnumber(String outletcontactnumber) {
        this.outletcontactnumber = outletcontactnumber;
    }


    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
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

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public String getOutletid() {
        return outletid;
    }

    public void setOutletid(String outletid) {
        this.outletid = outletid;
    }


    public static FoodDrinkModel getFoodDrinkModel() {
        return foodDrinkModel;
    }

    public static void setFoodDrinkModel(FoodDrinkModel foodDrinkModel) {
        FoodDrinkModel.foodDrinkModel = foodDrinkModel;
    }

    public static FoodDrinkModel foodDrinkModel;

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }


    public String getDealimage() {
        return Dealimage;
    }

    public void setDealimage(String dealimage) {
        Dealimage = dealimage;
    }

    public int getDealid() {
        return dealid;
    }

    public void setDealid(int dealid) {
        this.dealid = dealid;
    }


    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public String getReminderTime() {
        return ReminderTime;
    }

    public void setReminderTime(String reminderTime) {
        ReminderTime = reminderTime;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAvailibiltyTime() {
        return AvailibiltyTime;
    }

    public void setAvailibiltyTime(String availibiltyTime) {
        AvailibiltyTime = availibiltyTime;
    }

    public String getActualPrice() {
        return ActualPrice;
    }

    public void setActualPrice(String actualPrice) {
        ActualPrice = actualPrice;
    }

    public String getAfterDiscountPrice() {
        return AfterDiscountPrice;
    }

    public void setAfterDiscountPrice(String afterDiscountPrice) {
        AfterDiscountPrice = afterDiscountPrice;
    }

    public int getNumofOffers() {
        return NumofOffers;
    }

    public void setNumofOffers(int numofOffers) {
        NumofOffers = numofOffers;
    }

    public static List<FoodDrinkModel> getAlldeals() {
        //  List<HotDealsModel> allList = new ArrayList<>(0);
        //  allList = HotDealsModel.listAll(HotDealsModel.class);
        List<FoodDrinkModel> allList = FoodDrinkModel.findWithQuery(FoodDrinkModel.class, "Select * from HotDealsModel where original = ?", "true");
        return allList;
    }

    public static FoodDrinkModel getdealById(String Id) {
        return FoodDrinkModel.find(FoodDrinkModel.class, "newsid = ?", new String(Id)).get(0);
    }

}
