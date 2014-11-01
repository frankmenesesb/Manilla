package com.example.accesibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

@SuppressLint("NewApi")
public class NotificationService extends AccessibilityService {

	final static String MY_ACTION = "MY_ACTION";
	PendingIntent pi;

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {

		System.out.println("Kevin clicked"
				+ AccessibilityEvent.TYPE_VIEW_CLICKED);
		if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {

			String s = (String) event.getPackageName();

			System.out.println("Kevin package: " + event.getPackageName());

			if (s.contains("com.whatsapp")) {
				Intent intent = new Intent();
				intent.setAction(MY_ACTION);
				intent.putExtra("Notification", "WHATSAPP");
				sendBroadcast(intent);
			}
			if (s.contains("com.google.android.gm")) {
				Intent intent = new Intent();
				intent.setAction(MY_ACTION);
				intent.putExtra("Notification", "GMAIL");
				sendBroadcast(intent);
			}
			if (s.contains("com.google.android.dialer") && s.contains("Missed call")) {
				Intent intent = new Intent();
				intent.setAction(MY_ACTION);
				intent.putExtra("Notification", "PERDIDA");
				sendBroadcast(intent);
			}

			if (s.contains("com.facebook.orca")) {
				Intent intent = new Intent();
				intent.setAction(MY_ACTION);
				intent.putExtra("Notification", "FACE");
				sendBroadcast(intent);
			}
			if (s.contains("com.google.android.talk")) {
				Intent intent = new Intent();
				intent.setAction(MY_ACTION);
				intent.putExtra("Notification", "TALK");
				sendBroadcast(intent);
			}
			if (s.contains("com.twitter.android")) {
				Intent intent = new Intent();
				intent.setAction(MY_ACTION);
				intent.putExtra("Notification", "TWITTER");
				sendBroadcast(intent);
			}
		}
	}

	@Override
	protected void onServiceConnected() {
		try {
			System.out.println("onServiceConnected");
			AccessibilityServiceInfo info = new AccessibilityServiceInfo();
			info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
			info.notificationTimeout = 100;
			info.feedbackType = AccessibilityEvent.TYPES_ALL_MASK;
			setServiceInfo(info);	
		}catch(Exception e) {
			
		}
		
	}

	@Override
	public void onInterrupt() {
		System.out.println("onInterrupt");
	}
}
