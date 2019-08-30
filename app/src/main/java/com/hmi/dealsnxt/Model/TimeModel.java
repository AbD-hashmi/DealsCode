package com.hmi.dealsnxt.Model;

public class TimeModel {
    private String start_time;
    private String id;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    private String end_time;

    public TimeModel(String start, String end, String posting) {
        this.start_time = start;
        this.end_time = end;
        this.id = posting;

    }

}
