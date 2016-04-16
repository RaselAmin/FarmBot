package com.androquad.shobujekattor.Controller;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by BITM Trainer 401 on 3/28/2016.
 */
public class AppController extends Application{

    private static AppController instance;
    private RequestQueue requestQueue;

    public static synchronized AppController getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    private  RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(this);
        }
        return requestQueue;
    }

    public  void addToRequestQueue(Request request){
        getRequestQueue().add(request);
    }


}
