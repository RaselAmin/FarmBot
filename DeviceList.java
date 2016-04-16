package com.androquad.shobujekattor.Fragment;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androquad.shobujekattor.BaseActivity;
import com.androquad.shobujekattor.MainActivity;
import com.androquad.shobujekattor.R;

import java.util.Set;

/**
 * Created by Reza on 6-Apr-2016.
 */
public class DeviceList extends Fragment {

    // Debugging for LOGCAT
    private static final String TAG = "DeviceList";
    private static final boolean D = true;


    // declare button for launching website and textview for connection status
    Button tlbutton;
    TextView textView1;

    // EXTRA string to send on to mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    //private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.device_list, container, false);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        //***************
        checkBTState();

        textView1 = (TextView) getActivity().findViewById(R.id.connecting);
        textView1.setTextSize(40);
        textView1.setText(" ");

        // Initialize array adapter for paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.device_name);

        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) getActivity().findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Get the local Bluetooth adapter
        MainActivity.mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices and append to 'pairedDevices'
        Set<BluetoothDevice> pairedDevices = MainActivity.mBtAdapter.getBondedDevices();

        // Add previosuly paired devices to the array
        if (pairedDevices.size() > 0) {
            getActivity().findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
    }

    // Set up on-click listener for the list (nicked this - unsure)
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            textView1.setText("Connecting...");
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);


            /*// Make an intent to start next activity while taking an extra which is the MAC address.
            Intent i = new Intent(DeviceListActivity.this, getActivity().class);
            i.putExtra(EXTRA_DEVICE_ADDRESS, address);
            startActivity(i);*/

            //---Using Fragment----
            Bundle bundle=new Bundle();
            bundle.putString(EXTRA_DEVICE_ADDRESS, address);

            Fragment nextFrag=new SettingsFragment();
            nextFrag.setArguments(bundle);
            BaseActivity.fragmentChange(nextFrag);
        }
    };

    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        MainActivity.mBtAdapter=BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if(MainActivity.mBtAdapter==null) {
            Toast.makeText(getActivity(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (MainActivity.mBtAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);

            }
        }
    }
}
