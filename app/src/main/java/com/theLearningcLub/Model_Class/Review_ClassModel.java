package com.theLearningcLub.Model_Class;

/**
 * Created by HP on 5/10/2019.
 */

public class Review_ClassModel {
    private String name;
    private String image_drawable;
    private String reting_bar;
    private String desc;
    private String date;

    public Review_ClassModel(String name, String image_drawable, String reting_bar, String desc, String date) {
        this.name = name;
        this.image_drawable = image_drawable;
        this.reting_bar = reting_bar;
        this.desc = desc;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_drawable() {
        return image_drawable;
    }

    public void setImage_drawable(String image_drawable) {
        this.image_drawable = image_drawable;
    }

    public String getReting_bar() {
        return reting_bar;
    }

    public void setReting_bar(String reting_bar) {
        this.reting_bar = reting_bar;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
