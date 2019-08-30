package com.hmi.dealsnxt.Model;

import com.orm.SugarRecord;


public class    ProfileModel  {

    public String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserdob() {
        return userdob;
    }

    public void setUserdob(String userdob) {
        this.userdob = userdob;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getUserage() {
        return userage;
    }

    public void setUserage(String userage) {
        this.userage = userage;
    }

    public String getUsergender() {
        return usergender;
    }

    public void setUsergender(String usergender) {
        this.usergender = usergender;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static ProfileModel getProfileModel() {
        return profileModel;
    }

    public static void setProfileModel(ProfileModel profileModel) {
        ProfileModel.profileModel = profileModel;
    }

    public String useremail;
    public String userdob;
    public String usermobile;
    public String userage;
    public String usergender;
    public String userid;
    public String user;
    public static ProfileModel profileModel;


}
