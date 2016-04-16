package com.androquad.shobujekattor.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.androquad.shobujekattor.model.UserInfo;

/**
 * Created by sujon on 07/03/2016.
 */
public class Preference {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static  Context context;

    public Preference(Context context) {
        preferences = context.getSharedPreferences("bitm", context.MODE_PRIVATE);
        editor = preferences.edit();
        this.context = context;
    }

    public static  int retrieveId(){
        int id = preferences.getInt("id",0);
        return id;
    }
    public static  String retrieveUserName(){
        String user = preferences.getString("user", "NO USER");

        return user;
        }
    public static  String retrievePhone(){
        String phone = preferences.getString("phone", "NO phone");

        return phone;
    }

    public static void saveLoginData(UserInfo userInfo){
        editor.putInt("id", userInfo.getId());
        editor.putString("user", userInfo.getName());
        editor.putString("phone", userInfo.getPhone());
        editor.commit();
    }

    public static void deleteLoginData(){
        editor.remove("id");
        editor.remove("user");
        editor.remove("phone");
        editor.commit();

    }
}
