package com.theLearningcLub.Model_Class;

/**
 * Created by HP on 6/14/2019.
 */

public class Itemsearch_Model  {
   private String id;
   private String name;
   private String type;

    public Itemsearch_Model(String name, String id, String type) {
        this.name=name;
        this.id=id;
        this.type=type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
