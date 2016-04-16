package com.androquad.shobujekattor.model;

/**
 * Created by sujon on 06/04/2016.
 */
public class MessageFeed {
    private String name, phone, message ;

    public MessageFeed(String name, String phone, String message) {
        this.name = name;
        this.phone = phone;
        this.message = message;
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
}
