package com.theLearningcLub.Model_Class;


import java.io.Serializable;


public class Searchmodelclass implements Serializable {

    private String name, id;

    public Searchmodelclass(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}