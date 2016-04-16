package com.androquad.shobujekattor.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androquad.shobujekattor.Adapter.MessageFeedAdapter;
import com.androquad.shobujekattor.Controller.AppController;
import com.androquad.shobujekattor.MainActivity;
import com.androquad.shobujekattor.R;
import com.androquad.shobujekattor.model.MessageFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sujon on 06/04/2016.
 */
public class AgroInfoFragment extends Fragment {

    static ArrayList<MessageFeed> messageFeeds ;
    static MessageFeed messageFeed;
    Button fosholSomuho,krishiProjukti;
    Fragment nextFragment;

    ListView listViewFeed;
    String url = "http://mrs.edu.bd/dbJsonView.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_agro_info, container, false);
        fosholSomuho = (Button) view.findViewById(R.id.fosholSomuho);
        krishiProjukti = (Button) view.findViewById(R.id.krishiProjukti);
        listViewFeed = (ListView) view.findViewById(R.id.listViewFeed);

        Bundle bundle = this.getArguments();
        if(bundle!=null && bundle.getString("login").equals("success") ){
            Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_LONG).show();
            //getActivity().findViewById(R.id.signIn);

            //TODO: use common methods to update login related layouts:
            //BaseActivity.updateLoginViews();
            Button t = (Button) getActivity().findViewById(R.id.signIn);
            t.setText(R.string.logout);
        }

        fosholSomuho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment = new AgricultureFragment();
                MainActivity.fragmentChange(nextFragment);
            }
        });
        krishiProjukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment = new AgroTechology();
                MainActivity.fragmentChange(nextFragment);
            }
        });

        if(messageFeeds==null){

            getFeedInfo();

        }else{
            MessageFeedAdapter adapter = new MessageFeedAdapter(getActivity(), messageFeeds);
            listViewFeed.setAdapter(adapter);
        }

        return view;
    }

    private void getFeedInfo() {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                    try {
                        messageFeeds = new ArrayList<MessageFeed>();
                        JSONArray array = response.getJSONArray("messageFeed");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String name = object.getString("name");
                            String phone = object.getString("phone");
                            String message = object.getString("message");
                            messageFeed = new MessageFeed(name, phone, message);
                            messageFeeds.add(messageFeed);
                        }

                        MessageFeedAdapter adapter = new MessageFeedAdapter(getActivity(), messageFeeds);
                        listViewFeed.setAdapter(adapter);

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
