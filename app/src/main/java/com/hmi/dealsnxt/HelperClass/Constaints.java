package com.hmi.dealsnxt.HelperClass;

public class Constaints {
    public static int ABSTRACTID = 0;

//    public static String BaseUrl = "http://logiquebrainexaminer.com/deals_nxt/";
    public static String BaseUrl = "http://dealsnxt.nuagedigitech.com/";

    // public static String LoadEventDetails = BaseUrl + "events";

    public static String UserRegistrationURL = BaseUrl + "api/v1/userRegister";
    public static String OTPverify = BaseUrl + "api/v1/userVerification";
    public static String IsUserVerified = BaseUrl + "api/v1/userFinalVerification";


    public static String RecieveChats = BaseUrl + "api/v2/get-user-chats";
    public static String sendChat = BaseUrl + "api/v2/send-message";

    public static String BannerUrl = BaseUrl + "api/v1/getBanner";
    public static String DealList = BaseUrl + "api/v1/getDeals";
    public static String DealDetailbyOutlet = BaseUrl + "api/v1/getDealByOutlets";
    public static String DealDetail = BaseUrl + "api/v1/dealDetails";
    public static String DealLikeCount = BaseUrl + "api/v1/dealLikes";
    public static String CheckForUserVerification = BaseUrl + "api/v1/userFinalVerification";

    public static String CategoryDetailbyID = BaseUrl + "api/v1/getDealByCategory";

    public static String LocationUpdate = BaseUrl + "api/v1/geolocations/add.json";



    public static String OrderBookbforePay = BaseUrl + "api/v1/orderBook";
    public static String OrderpaidafterPay = BaseUrl + "api/v1/orderConfirm";
    public static String GiftSms = BaseUrl + "api/v1/giftSMS";

    public static String OrderedListing = BaseUrl + "api/v1/myOrder";
    public static String OrderedCancel = BaseUrl + "api/v1/orderCancel";
    public static String OrderedDetailbyDealid = BaseUrl + "api/v1/orderDetail";


    public static String ProfileUpdate = BaseUrl + "api/v1/userProfileUpdate";
    public static String getCity = BaseUrl + "api/v1/getCity";
    public static String getlocality = BaseUrl + "api/v1/getLocality";
    public static String searchLoc = BaseUrl + "api/v1/getSearchLocation";
    public static String searchOut = BaseUrl + "api/v1/getSearchOutlet";
    public static String applyCoupen = BaseUrl + "api/v1/applyCoupon";




    public static String UpdateDeviceGCMID = BaseUrl + "update_notification_id";
    public static String AboutUs = BaseUrl + "api/v1/getPages";
    public static String Termscond = BaseUrl + "api/v1/getPages";
    public static String Privacypolicy = BaseUrl + "api/v1/getPages";
    public static String DealbyCategory = BaseUrl + "api/v1/getDealByCategory";
}
