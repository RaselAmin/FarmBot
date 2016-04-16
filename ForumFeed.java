package com.androquad.shobujekattor.model;

/**
 * Created by sujon on 07/04/2016.
 */
public class ForumFeed {
    private String name, phone, message, datetime ;

    public ForumFeed(String name, String message, String datetime) {
        this.name = name;
        this.message = message;
        this.datetime = datetime;
    }



    public ForumFeed(String name, String phone, String message, String datetime) {
        this.name = name;
        this.phone = phone;
        this.message = message;
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }

    public String getDatetime() {
        return datetime;
    }
}
