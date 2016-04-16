package com.androquad.shobujekattor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Reza on 6-Apr-2016.
 */
public class MainActivity extends BaseActivity {
    //private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();

        /*if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, "BackStackEntryCount=" + backStackEntryCount, Toast.LENGTH_LONG);
        toast.show();*/

        if ( backStackEntryCount > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
