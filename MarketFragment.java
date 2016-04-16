package com.androquad.shobujekattor.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androquad.shobujekattor.Adapter.ForumFeedAdapter;
import com.androquad.shobujekattor.Controller.AppController;
import com.androquad.shobujekattor.model.ForumFeed;
import com.androquad.shobujekattor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sujon on 05/04/2016.
 */
public class MarketFragment extends Fragment {

    private EditText informationEt;
    static ArrayList<ForumFeed> forumfeeds ;
    static ForumFeed forumfeed;
    ListView marketLv;

    Button submitBt;
    JSONObject userObject;


    String url = "http://mrs.edu.bd/androquad/forum.php";
    String messageurl = "http://mrs.edu.bd/androquad/forumJson.php";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        informationEt = (EditText) view.findViewById(R.id.informationEt);
        marketLv = (ListView) view.findViewById(R.id.marketLv);
        submitBt = (Button) view.findViewById(R.id.submitBt);
        if(forumfeeds==null){

            getFeedInfo();

        }else{
            ForumFeedAdapter adapter = new ForumFeedAdapter(getActivity(), forumfeeds);
            marketLv.setAdapter(adapter);
        }

        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "fgyh", Toast.LENGTH_SHORT).show();
                String name = "DATA FROM SHARE PREFERENCE";
                String message = informationEt.getText().toString();


                userObject = new JSONObject();
                try {
                    userObject.put("name", name);
                    userObject.put("message", message);

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, userObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            try {
                                boolean result = response.getBoolean("ResultState");
                                Toast.makeText(getActivity(), String.valueOf(result), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    AppController.getInstance().addToRequestQueue(request);
                    Log.i("URL", request.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        return view;
    }

    private void getFeedInfo() {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, messageurl, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    forumfeeds = new ArrayList<ForumFeed>();
                    JSONArray array = response.getJSONArray("district");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("name");

                        String datetime = object.getString("datetime");

                        String message = object.getString("message");

                        forumfeed = new ForumFeed(name, message, datetime);
                        forumfeeds.add(forumfeed);
                    }

                    ForumFeedAdapter adapter = new ForumFeedAdapter(getActivity(), forumfeeds);
                    marketLv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError){
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(request);

    }

}