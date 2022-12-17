package com.theLearningcLub.Model_Class;

import java.util.ArrayList;

public class EventMainModelClass
{
    private String month_name;
    private ArrayList<EventSubModelClass> sList;

    public EventMainModelClass(String month_name, ArrayList<EventSubModelClass> sList) {
        this.month_name = month_name;
        this.sList = sList;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public ArrayList<EventSubModelClass> getsList() {
        return sList;
    }

    public void setsList(ArrayList<EventSubModelClass> sList) {
        this.sList = sList;
    }
}
