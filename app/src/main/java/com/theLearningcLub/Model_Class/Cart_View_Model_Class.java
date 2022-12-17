package com.theLearningcLub.Model_Class;

/**
 * Created by HP on 5/24/2019.
 */

public class Cart_View_Model_Class {
    private String Title;
    private String Image_Drawable;
    private String Image_Delete;
    private String price;
    private String cardid;
    private String package_type;

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage_Drawable() {
        return Image_Drawable;
    }

    public void setImage_Drawable(String image_Drawable) {
        Image_Drawable = image_Drawable;
    }

    public String getImage_Delete() {
        return Image_Delete;
    }

    public void setImage_Delete(String image_Delete) {
        Image_Delete = image_Delete;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }
}
