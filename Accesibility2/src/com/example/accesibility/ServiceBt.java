package com.example.accesibility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import com.example.activities.AdminTelephone;
import com.example.activities.Splash;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ServiceBt extends Service {

	/**
	 * NOTIFICATION LED
	 */
	final String notredOn = "N:1:1:;";
	final String notredOff = "N:1:0:;";
	final String notgreenOn = "N:2:1:;";
	final String notgreenOff = "N:2:0:;";
	final String notblueOn = "N:3:1:;";
	final String notblueOff = "N:3:0:;";
	final String notyellowOn = "N:4:1:;";
	final String notyellowOff = "N:4:0:;";
	final String notcianOn = "N:5:1:;";
	final String notcianOff = "N:5:0:;";
	final String notpurpleOn = "N:6:1:;";
	final String notpurpleOff = "N:6:0:;";
	final String notwhiteOn = "N:7:1:;";
	final String notwhiteOff = "N:7:0:;";
	final String allOff = "N:0:;";

	// SOCIAL

	// ACTION LED

	final String actgreen = "A:1:1;";
	final String actred = "A:2:1;";
	final String actyellow = "A:3:1;";
	final String activitiesOff = "A:0;";

	// PLACES LED
	final String plagreen = "P:1:1;";
	final String plared = "P:2:1;";
	final String playellow = "P:3:1;";
	final String placesOff = "P:0;";

	// MUSIC LED
	final String musgreen = "M:1:1;";
	final String musred = "M:2:1;";
	final String musyellow = "M:3:1;";
	final String musicOff = "M:0;";

	BroadcastReceiver receiver;
	public ConnectedThread connectedThread;
	BluetoothDevice device;
	BluetoothAdapter btAdapter;
	public static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	protected static final int SUCCESS_CONNECT = 0;
	protected static final int MESSAGE_READ = 1;
	String tag = "debugging";
	String buffer;
    ArrayList tipoNotificacion = new ArrayList();
        

	//
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.i(tag, "in handler");
			super.handleMessage(msg);
			switch (msg.what) {
			case SUCCESS_CONNECT:
				// DO something
				connectedThread = new ConnectedThread((BluetoothSocket) msg.obj);
				connectedThread.start();
				Toast.makeText(getApplicationContext(), "CONNECT", 0).show();
				String s = "successfully connected";
				connectedThread.write(s.getBytes());
				Log.d("asd", "kevin successfully connected");
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				String string = new String(readBuf);
				buffer = string;
				evalString(string);
				Toast.makeText(getApplicationContext(), string, 0).show();
				break;
			}
		}
	};

	/**
	 * 
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void evalString(String string) {
		String msj;
		Log.d("", " ?");
		
		//1 si llego bien la O:;
		//2 si llego bien la N:x:x:;
		//0 si llego bien la notificacion para apagar
		
		if (string.contains("?")) {
			if(!tipoNotificacion.isEmpty()){
				for (int i = 0; i < tipoNotificacion.size(); i++) {
					msj=(String) tipoNotificacion.get(i);
					connectedThread.write(msj.getBytes());
					Log.d("CICLOOOOO ", msj);
				}
			}else{
				msj = "O:;";
				Log.d("", "ENTRO MSJ");
				connectedThread.write(msj.getBytes());
			}
		}
		if (string.contains("2")){
			tipoNotificacion.clear();
		}
	}

	private void turnOnBT() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivity(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// receiver = new notifyReciver();

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				String action = intent.getExtras().getString("Notification");
				/*
				 * String estado = intent.getExtras().getString(
				 * TelephonyManager.EXTRA_STATE);
				 * 
				 * if (estado.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				 * connectedThread.write(notpurpleOn.getBytes()); }
				 */

				/*if (action.equalsIgnoreCase("WHATSAPP")) {
					connectedThread.write(notgreenOn.getBytes());
					Log.d("", "Whatsapp");
					//mythread mt= new mythread(notgreenOff, connectedThread);
					//mt.start();
				}
				if (action.equalsIgnoreCase("GMAIL")) {
					Log.d("", "gmail");
					connectedThread.write(notredOn.getBytes());
				}
				if (action.equalsIgnoreCase("PERDIDA")) {
					Log.d("", "perdida");
					connectedThread.write(notyellowOn.getBytes());
				}
				if (action.equalsIgnoreCase("FACE")) {
					connectedThread.write(notblueOn.getBytes());
					mythread mt= new mythread(notblueOff, connectedThread);
					mt.start();
				}
				if (action.equalsIgnoreCase("GTALK")) {
					connectedThread.write(notwhiteOn.getBytes());
				}
				if (action.equalsIgnoreCase("TWITTER")) {
					connectedThread.write(notcianOn.getBytes());
				}*/
				if (action.equalsIgnoreCase("WHATSAPP")) {
					//connectedThread.write(notgreenOn.getBytes());
					tipoNotificacion.add(notgreenOn);
                                      Log.d("", "Whatsapp");
                                        
					//mythread mt= new mythread(notgreenOff, connectedThread);
					//mt.start();
				}else if (action.equalsIgnoreCase("GMAIL")) {
				tipoNotificacion.add(notredOn);	
                                    Log.d("", "gmail");
					//connectedThread.write(notredOn.getBytes());
				}else if (action.equalsIgnoreCase("PERDIDA")) {
				tipoNotificacion.add(notyellowOn);	
                                    Log.d("", "perdida");
					//connectedThread.write(notyellowOn.getBytes());
				}else	if (action.equalsIgnoreCase("FACE")) {
					//connectedThread.write(notblueOn.getBytes());
                                    tipoNotificacion.add(notblueOn);
					mythread mt= new mythread(notblueOff, connectedThread);
					mt.start();
				}else if (action.equalsIgnoreCase("GTALK")) {
                                    tipoNotificacion.add(notwhiteOn);
					//connectedThread.write(notwhiteOn.getBytes());
				}else if (action.equalsIgnoreCase("TWITTER")) {
                                    tipoNotificacion.add(notcianOn);
					//connectedThread.write(notcianOn.getBytes());
				}else{
					evalString(buffer);
				}


			}
		};
		IntentFilter filter = new IntentFilter(NotificationService.MY_ACTION);
		registerReceiver(receiver, filter);
			}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		device = (BluetoothDevice) intent.getExtras().get("DEVICE");
		ConnectThread connect = new ConnectThread(device);
		Log.d("", "Kevin START BLUETOOTH");
		connect.start();

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private class mythread extends Thread {
		String app;
		ConnectedThread conect;

		public mythread(String appx, ConnectedThread c) {
			conect=c;
			app = appx;
		}
		
		public void run() {
			try {
				Thread.sleep(10000);
				conect.write(app.getBytes());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

	private class ConnectThread extends Thread {

		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			// Use a temporary object that is later assigned to mmSocket,
			// because mmSocket is final
			BluetoothSocket tmp = null;
			mmDevice = device;
			Log.i(tag, "construct");
			// Get a BluetoothSocket to connect with the given BluetoothDevice
			try {
				// MY_UUID is the app's UUID string, also used by the server
				// code
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
				Log.d("", "Kevin CONNECT THREAD");
			} catch (IOException e) {
				Log.i(tag, "get socket failed");

			}
			mmSocket = tmp;
		}

		public void run() {
			// Cancel discovery because it will slow down the connection
			// btAdapter.cancelDiscovery();
			Log.i(tag, "connect - run");
			try {
				// Connect the device through the socket. This will block
				// until it succeeds or throws an exception
				mmSocket.connect();
				Log.i(tag, "connect - succeeded");
			} catch (IOException connectException) {
				Log.i(tag, "connect failed");
				// Unable to connect; close the socket and get out
				try {
					mmSocket.close();
				} catch (IOException closeException) {
				}
				return;
			}

			// Do work to manage the connection (in a separate thread)

			mHandler.obtainMessage(SUCCESS_CONNECT, mmSocket).sendToTarget();
		}

		/** Will cancel an in-progress connection, and close the socket */
		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}

	public class ConnectedThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;

		public ConnectedThread(BluetoothSocket socket) {
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;

			// Get the input and output streams, using temp objects because
			// member streams are final
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
				Log.d("", " SOCKETE");
			} catch (IOException e) {
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}

		public void run() {
			byte[] buffer; // buffer store for the stream
			int bytes; // bytes returned from read()
			Log.d("", "Kevin RUN CONNECTED");
			// Keep listening to the InputStream until an exception occurs
			while (true) {
				try {
					Log.d("", " LEO");
					// Read from the InputStream
					buffer = new byte[1024];
					bytes = mmInStream.read(buffer);
					// Send the obtained bytes to the UI activity
					mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
							.sendToTarget();

				} catch (IOException e) {
					break;
				}
			}
		}

		/* Call this from the main activity to send data to the remote device */
		public void write(byte[] bytes) {
			try {
				mmOutStream.write(bytes);
				mmOutStream.write(32);
				Log.d("WRITEEEEEE", bytes+"");
			} catch (IOException e) {
			}
		}

		/* Call this from the main activity to shutdown the connection */
		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}
	/**
	 * public static class notifyReciver extends BroadcastReceiver{
	 * 
	 * @Override public void onReceive(Context context, Intent intent) { String
	 *           s= intent.getExtras().getString("Notification");
	 *           if(s.equalsIgnoreCase("WHATSAPP")){ connectedThread. } }
	 * 
	 *           }
	 */
}
