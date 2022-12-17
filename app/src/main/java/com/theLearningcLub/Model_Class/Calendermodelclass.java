package com.theLearningcLub.Model_Class;


import java.io.Serializable;
import java.util.ArrayList;


public class Calendermodelclass implements Serializable {

    private String title, genre;


    private ArrayList<Testseriesmodel> sList;

    public Calendermodelclass(String stitle, String genre, Calendermodelclass storymodel) {

    }


    public Calendermodelclass(String title, String genre, ArrayList<Testseriesmodel> sList) {

        this.title = title;

        this.genre = genre;

        this.sList=sList;

    }


    public String getTitle() {

        return title;

    }


    public void setTitle(String name) {

        this.title = name;

    }



    public String getGenre() {

        return genre;

    }


    public void setGenre(String genre) {

        this.genre = genre;

    }


    public ArrayList<Testseriesmodel> getsList() {

        return sList;

    }


    public void setsList(ArrayList<Testseriesmodel> sList) {

        this.sList = sList;

    }

}