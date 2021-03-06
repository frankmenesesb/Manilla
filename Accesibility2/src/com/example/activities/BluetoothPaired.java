package com.example.activities;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.accesibility.NotificationService;
import com.example.accesibility.R;
import com.example.accesibility.ServiceBt;

public class BluetoothPaired extends Activity implements OnItemClickListener {

	ArrayAdapter<String> listAdapter;
	ListView listView;
	BluetoothAdapter btAdapter;
	Set<BluetoothDevice> devicesArray;
	ArrayList<String> pairedDevices;
	ArrayList<BluetoothDevice> devices;
	protected static final int SUCCESS_CONNECT = 0;
	protected static final int MESSAGE_READ = 1;
	IntentFilter filter;
	BroadcastReceiver receiver;
	String tag = "debugging";

	// base de datos

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//EL llamado al XML
		setContentView(R.layout.activity_paired_bt);
		init();
        if(btAdapter==null){
        	Toast.makeText(getApplicationContext(), "No bluetooth detected", 0).show();
        	finish();
        }
        else{
        	if(!btAdapter.isEnabled()){
        		turnOnBT();
        	}
        	getPairedDevices();
        	startDiscovery();
        }
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		if (btAdapter.isDiscovering()) {
			btAdapter.cancelDiscovery();
		}
		if (listAdapter.getItem(arg2).contains("Paired")) {

			BluetoothDevice selectedDevice = devices.get(arg2);
			// ConnectThread connect = new ConnectThread(selectedDevice);
			// connect.start();
			Log.i(tag, "in click listener");
			Intent servi= new Intent (this, ServiceBt.class);
			Intent splash = new Intent(this, Splash.class);
			Intent menutelefono = new Intent(this, AdminTelephone.class);
			
			Log.d("", "Kevin "+selectedDevice.getAddress()+"   "+ selectedDevice.getName());
			
			servi.putExtra("DEVICE", selectedDevice);
			//Intent intentenServis = new Intent(AccessibilityService.);
			startService(servi);
			startActivity(splash);
			//startActivity(menutelefono);
		} else {
			Toast.makeText(getApplicationContext(), "device is not paired", 0)
					.show();
		}

	}

	private void init() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, 0);
		listView.setAdapter(listAdapter);
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		pairedDevices = new ArrayList<String>();
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		devices = new ArrayList<BluetoothDevice>();
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String action = intent.getAction();

				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					devices.add(device);
					String s = "";
					for (int a = 0; a < pairedDevices.size(); a++) {
						if (device.getName().equals(pairedDevices.get(a))) {
							// append
							s = "(Paired)";
							break;
						}
					}

					listAdapter.add(device.getName() + " " + s + " " + "\n"
							+ device.getAddress());
				}

				else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED
						.equals(action)) {
					// run some code
				} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
						.equals(action)) {
					// run some code
				} else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
					if (btAdapter.getState() == btAdapter.STATE_OFF) {
						turnOnBT();
					}
				}

			}
		};

		registerReceiver(receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		registerReceiver(receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(receiver, filter);
	}

	private void turnOnBT() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(intent, 1);
	}

	private void getPairedDevices() {
		// TODO Auto-generated method stub
		devicesArray = btAdapter.getBondedDevices();
		if (devicesArray.size() > 0) {
			for (BluetoothDevice device : devicesArray) {
				pairedDevices.add(device.getName());
			}
		}
	}

	private void startDiscovery() {
		// TODO Auto-generated method stub
		btAdapter.cancelDiscovery();
		btAdapter.startDiscovery();

	}

	@Override
	protected void onPause() { 
		// TODO Auto-generated method stub
		super.onPause();
		//unregisterReceiver(receiver);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplicationContext(),
					"Bluetooth must be enabled to continue", Toast.LENGTH_SHORT)
					.show();
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
}
