package com.hmi.dealsnxt.Model;

import com.orm.SugarRecord;

public class ChatModel extends SugarRecord {

    String message;
    String from_user_id;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

}
