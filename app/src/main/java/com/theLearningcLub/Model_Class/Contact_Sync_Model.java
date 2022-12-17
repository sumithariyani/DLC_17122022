package com.theLearningcLub.Model_Class;

/**
 * Created by HP on 6/12/2019.
 */

public class Contact_Sync_Model {
    private String number;
    private  String name;
    private char firstletter;
    private char lastletter;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getLastletter() {
        return lastletter;
    }

    public void setLastletter(char lastletter) {
        this.lastletter = lastletter;
    }

    public char getFirstletter() {
        return firstletter;
    }

    public void setFirstletter(char firstletter) {
        this.firstletter = firstletter;
    }
}
