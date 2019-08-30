package com.hmi.dealsnxt.Model;

public class CityModel {
    private String name;
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private String lat;
    private String lang;

    public CityModel(String name, String posting,String lat,String lang,String type) {
        this.name = name;
        this.id = posting;
        this.lat=lat;
        this.lang=lang;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getcityid() {
        return id;
    }

    public void setCityId(String posting) {
        this.id = posting;
    }

}
