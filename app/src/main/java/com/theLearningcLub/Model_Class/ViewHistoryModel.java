package com.theLearningcLub.Model_Class;

/**
 * Created by HP on 5/10/2019.
 */

public class ViewHistoryModel {
    private String h_id, user_id, name, email, contact, message, feedback,date;

    public ViewHistoryModel(String h_id, String user_id, String name, String email, String contact, String message, String feedback, String date) {
        this.h_id = h_id;
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.message = message;
        this.feedback = feedback;
        this.date = date;
    }

    public String getH_id() {
        return h_id;
    }

    public void setH_id(String h_id) {
        this.h_id = h_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

