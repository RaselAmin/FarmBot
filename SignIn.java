package com.androquad.shobujekattor.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androquad.shobujekattor.BaseActivity;
import com.androquad.shobujekattor.Controller.AppController;
import com.androquad.shobujekattor.Controller.Preference;
import com.androquad.shobujekattor.R;
import com.androquad.shobujekattor.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sujon on 06/04/2016.
 */
public class SignIn extends Fragment {
    Button registerBt, loginBt;
    EditText nameEt, passwordEt;
    TextView user;


    String loginUrl = "http://mrs.edu.bd/androquad/logincheck.php";
    JSONObject loginObject;

    public SignIn() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signin, container, false);
        loginBt = (Button) view.findViewById(R.id.loginBt);
        registerBt = (Button) view.findViewById(R.id.registerBt);
        nameEt = (EditText) view.findViewById(R.id.nameEt);
        passwordEt = (EditText) view.findViewById(R.id.passwordEt);
        user = (TextView) view.findViewById(R.id.user);
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.register(view);
            }
        });
        return view;
    }

    private boolean login() throws JSONException {

        //Toast.makeText(getActivity(), "HTTP....", Toast.LENGTH_SHORT).show();

      final String name = nameEt.getText().toString();
       String password = passwordEt.getText().toString();
        if(name.isEmpty() || password.isEmpty()){
            Toast.makeText(getActivity(), "Enter Your Correct Username And Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        loginObject = new JSONObject();
        loginObject.put("UserId", name);
        loginObject.put("Password", password);



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginUrl, loginObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    int uid=0;
                    String uname="", umobile="";
                    boolean result = response.getBoolean("ResultState");
                    if (result = true){
                        //UserInfo
                        JSONObject object = response.getJSONObject("UserInfo");
                        uid = object.getInt("id");
                        uname = object.getString("name");
                        umobile = object.getString("mobile");

                        UserInfo userInfo = new UserInfo(uid, uname, umobile);
                        Preference preference = new Preference(getActivity());
                        preference.saveLoginData(userInfo);


                        Bundle bundle=new Bundle();
                        bundle.putString("login", "success");
                        //set Fragmentclass Arguments
                        Fragment nextFrag=new AgroInfoFragment();
                        nextFrag.setArguments(bundle);
                        BaseActivity.fragmentChange(nextFrag);

                    }
                    Toast.makeText(getActivity(), uid + "::"+uname+"::"+umobile, Toast.LENGTH_SHORT).show();
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
        return true;
    }
}