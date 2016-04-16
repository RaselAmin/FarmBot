package com.androquad.shobujekattor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.androquad.shobujekattor.Controller.Preference;
import com.androquad.shobujekattor.Fragment.AgroInfoFragment;
import com.androquad.shobujekattor.Fragment.CurrentSystemFragment;
import com.androquad.shobujekattor.Fragment.DeviceList;
import com.androquad.shobujekattor.Fragment.MarketFragment;
import com.androquad.shobujekattor.Fragment.MesssageFragment;
import com.androquad.shobujekattor.Fragment.SettingsFragment;
import com.androquad.shobujekattor.Fragment.SignIn;
import com.androquad.shobujekattor.Fragment.SignUp;
import com.androquad.shobujekattor.model.UserInfo;

/**
 * Created by Reza on 6-Apr-2016.
 */
public class BaseActivity extends AppCompatActivity  {
    static FragmentManager fragmentManager;
    static FragmentTransaction transaction;
    TextView settings;
    UserInfo userInfo;
    static Preference preference;

    public static BluetoothAdapter mBtAdapter = null;
    public static String blueToothAddress = null;
    public static Boolean statusLED = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preference = new Preference(this);
        userInfo = getLoginSession();
        if(userInfo != null){
            Button t = (Button) findViewById(R.id.signIn);
            t.setText(R.string.logout);
        }

        fragmentManager = getFragmentManager();
        Fragment startFragment = new AgroInfoFragment();
        String backStateName = startFragment.getClass().getSimpleName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            fragmentChange(startFragment);
        }

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#20a780"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        settings = (TextView) findViewById(R.id.setting);
    }

    private UserInfo getLoginSession() {
        int id = preference.retrieveId();
        String name = preference.retrieveUserName();
        String phone = preference.retrievePhone();
        UserInfo userInfo;

        if(id!=0 && !name.isEmpty() && !phone.isEmpty()){
            return new UserInfo(id, name, phone);
        }

        return null;
    }

    public void signin(View view ){
        if(userInfo == null){
            fragmentChange(new SignIn());
        }else{
            preference.deleteLoginData();
            Button t = (Button) this.findViewById(R.id.signIn);
            t.setText(R.string.login);
        }
    }
    public void currentSystem(View view) {
        fragmentChange(new CurrentSystemFragment());
    }

    public void agroInfo(View view) {
        fragmentChange(new AgroInfoFragment());
    }

    public void message(View view) {
        fragmentChange(new MesssageFragment());
    }

    public void market(View view) {
        fragmentChange(new MarketFragment());
    }

    public void setting(View view) {
        if( mBtAdapter==null || !mBtAdapter.isEnabled() ){
            fragmentChange(new DeviceList());
        }else{
            fragmentChange(new SettingsFragment());
        }
    }

    static public void fragmentChange(Fragment fragment){
        String backStateName = fragment.getClass().getSimpleName();
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(
                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                R.animator.card_flip_left_in, R.animator.card_flip_left_out);
        transaction.replace(R.id.myFragment, fragment, backStateName);
        transaction.addToBackStack(backStateName);
        transaction.commit();

    }
   static public void register(View view) {
        fragmentChange(new SignUp()) ;
    }

    @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.activity_main_actions, menu);
        // Associate searchable configuration with the SearchView


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.aboutApp) {
            Intent intentUs = new Intent(this, AboutUsActivity.class);
            startActivity(intentUs);
        }
        if (id == R.id.refresh) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        if (id == R.id.actionExit) {
            finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }
}
