package com.androquad.shobujekattor.Fragment;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.androquad.shobujekattor.BaseActivity;
import com.androquad.shobujekattor.MainActivity;
import com.androquad.shobujekattor.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Reza on 6-Apr-2016.
 */
public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    Switch mySwitch = null;
    TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;

    Handler bluetoothIn;

    final int handlerState = 0; //used to identify handler message
    //private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    //private static String address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        //Link the buttons and textViews to respective views
        mySwitch = (Switch) view.findViewById(R.id.myswitch);
        if(MainActivity.statusLED !=null){
            mySwitch.setChecked(MainActivity.statusLED); // T || F;
        }
        mySwitch.setOnCheckedChangeListener(this);


        txtString = (TextView) view.findViewById(R.id.txtString);
        txtStringLength = (TextView) view.findViewById(R.id.testView1);
        sensorView0 = (TextView) view.findViewById(R.id.sensorView0);
        sensorView1 = (TextView) view.findViewById(R.id.sensorView1);
        sensorView2 = (TextView) view.findViewById(R.id.sensorView2);
        sensorView3 = (TextView) view.findViewById(R.id.sensorView3);

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) { //if message is what we want
                    String readMessage = (String) msg.obj; // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage); //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~"); // determine the end-of-line
                    if (endOfLineIndex > 0) { // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex); // extract string
                        txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length(); //get length of data received
                        txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        //if it starts with # we know it is what we are looking for
                        if (recDataString.charAt(0) == '#'){
                            String sensor0 = recDataString.substring(1, 5); //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10); //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);

                            //update the textviews with sensor values
                            sensorView0.setText(sensor0 + "V");
                            sensorView1.setText(sensor1 + "V");
                            sensorView2.setText(sensor2 + "V");
                            sensorView3.setText(sensor3 + "V");
                        }

                        //clear all string data
                        recDataString.delete(0, recDataString.length());
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        MainActivity.mBtAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        //onResume2(view);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // do something when check is selected
            MainActivity.statusLED = true;
            mConnectedThread.write("1");    // Send "1" via Bluetooth
            Toast.makeText(getActivity(), "Machine is ON", Toast.LENGTH_SHORT).show();
        } else {
            //do something when unchecked
            MainActivity.statusLED = false;
            mConnectedThread.write("0");    // Send "0" via Bluetooth
            Toast.makeText(getActivity(), "Machine is OFF", Toast.LENGTH_SHORT).show();
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        //Intent intent = getActivity().getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        //address = intent.getStringExtra(DeviceList.EXTRA_DEVICE_ADDRESS);

        Bundle getArgs = getArguments();
        if(getArgs != null) {
            MainActivity.blueToothAddress = getArgs.getString(DeviceList.EXTRA_DEVICE_ADDRESS);
        }


        //create device and set the MAC address
        BluetoothDevice device = MainActivity.mBtAdapter.getRemoteDevice(MainActivity.blueToothAddress);



        try {

            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //Don't leave Bluetooth sockets open when leaving activity
            if(btSocket != null)
                btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(MainActivity.mBtAdapter==null) {
            Toast.makeText(getActivity(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (MainActivity.mBtAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getActivity(), "Connection Failure", Toast.LENGTH_LONG).show();


                //resend to device-list:
                Fragment nextFrag=new DeviceList();
                BaseActivity.fragmentChange(nextFrag);

                //getActivity().finish();
            }
        }
    }
}
