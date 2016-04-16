package com.androquad.shobujekattor.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androquad.shobujekattor.Controller.AppController;
import com.androquad.shobujekattor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sujon on 06/04/2016.
 */
public class SignUp extends Fragment {
    private EditText nameEt, mobileEt;
    private Spinner districtSp;
    private EditText passwordEt;

    Button registerBt;
    JSONObject userObject;
    static ArrayList<String> districtList ;

    String url = "http://mrs.edu.bd/androquad/districtJson.php";
    String urlPost = "http://mrs.edu.bd/androquad/register.php";


    public SignUp() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        nameEt = (EditText) view.findViewById(R.id.nameEt);
        mobileEt = (EditText) view.findViewById(R.id.mobileEt);
        districtSp = (Spinner) view.findViewById(R.id.districSp);
        passwordEt = (EditText) view.findViewById(R.id.passwordEt);

        registerBt = (Button) view.findViewById(R.id.registerBt);
            getDistrict();

        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"fgyh", Toast.LENGTH_SHORT).show();
                String name = nameEt.getText().toString();
                String mobile = mobileEt.getText().toString();
                String district =districtSp.getSelectedItem().toString();
                String password = passwordEt.getText().toString();

                userObject = new JSONObject();
                try {
                    userObject.put("name", name);
                    userObject.put("mobile", mobile);
                    userObject.put("district", district);
                    userObject.put("password",password);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlPost, userObject, new Response.Listener<JSONObject>() {
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

    private void getDistrict() {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    districtList = new ArrayList<String>();
                    JSONArray array = response.getJSONArray("district");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("name");
                        districtList.add(name);
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_spinner_item, districtList);
                    spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );


                    districtSp.setAdapter(spinnerArrayAdapter);

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
