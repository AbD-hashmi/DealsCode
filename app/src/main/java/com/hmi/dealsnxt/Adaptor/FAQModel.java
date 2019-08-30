package com.hmi.dealsnxt.Adaptor;

import com.orm.SugarRecord;

import java.util.ArrayList;


public class FAQModel extends SugarRecord {

    public String questionid = "";
    public String questionname = "";
    public String questionstarttime = "";
    public String questionendtime = "";
    public String questiontype = "";
    public String question = "";
    public String questiondescription = "";
    public int IsFeedbackable;

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    public String getQuestionstarttime() {
        return questionstarttime;
    }

    public void setQuestionstarttime(String questionstarttime) {
        this.questionstarttime = questionstarttime;
    }

    public String getQuestionendtime() {
        return questionendtime;
    }

    public void setQuestionendtime(String questionendtime) {
        this.questionendtime = questionendtime;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestiondescription() {
        return questiondescription;
    }

    public void setQuestiondescription(String questiondescription) {
        this.questiondescription = questiondescription;
    }

    public int getIsFeedbackable() {
        return IsFeedbackable;
    }

    public void setIsFeedbackable(int isFeedbackable) {
        IsFeedbackable = isFeedbackable;
    }

}
