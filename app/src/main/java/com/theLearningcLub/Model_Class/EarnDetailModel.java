package com.theLearningcLub.Model_Class;

/**
 * Created by HP on 5/10/2019.
 */

public class EarnDetailModel {

    private  String user_fname,amount,date;

    public EarnDetailModel(String user_fname, String amount, String date) {
        this.user_fname = user_fname;
        this.amount = amount;
        this.date = date;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
