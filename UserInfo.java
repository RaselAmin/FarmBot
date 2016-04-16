package com.androquad.shobujekattor.model;

/**
 * Created by sujon on 07/04/2016.
 */
public class UserInfo {

    int id;
    String name;
    String phone;

    public UserInfo() {

    }

    public UserInfo(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
