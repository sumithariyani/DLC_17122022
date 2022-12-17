package com.theLearningcLub.Model_Class;

public class City_Model {
    public String cityName;
    public String id;
    private String is_free;
    String cat_id,price,pack_image,offer_price,
    time_perioud,alpha_id,grammar_id,description,rateStar;
    private boolean isSelected = false;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPack_image() {
        return pack_image;
    }

    public void setPack_image(String pack_image) {
        this.pack_image = pack_image;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public String getTime_perioud() {
        return time_perioud;
    }

    public void setTime_perioud(String time_perioud) {
        this.time_perioud = time_perioud;
    }

    public String getAlpha_id() {
        return alpha_id;
    }

    public void setAlpha_id(String alpha_id) {
        this.alpha_id = alpha_id;
    }

    public String getGrammar_id() {
        return grammar_id;
    }

    public void setGrammar_id(String grammar_id) {
        this.grammar_id = grammar_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRateStar() {
        return rateStar;
    }

    public void setRateStar(String rateStar) {
        this.rateStar = rateStar;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }
}
