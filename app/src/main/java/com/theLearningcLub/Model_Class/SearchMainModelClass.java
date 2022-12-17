package com.theLearningcLub.Model_Class;

import java.util.ArrayList;

public class SearchMainModelClass
{
    private String month_name;
    private ArrayList<City_Model> sList;

    public SearchMainModelClass(String month_name, ArrayList<City_Model> sList) {
        this.month_name = month_name;
        this.sList = sList;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public ArrayList<City_Model> getsList() {
        return sList;
    }

    public void setsList(ArrayList<City_Model> sList) {
        this.sList = sList;
    }
}
