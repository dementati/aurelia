package com.github.dementati.aurelia;

import java.lang.ref.WeakReference;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Vibrator;
import android.util.Log;

public class AuroraService extends Service {
	public static final int MSG_SUPPLY_MESSENGER = 1;
	public static final int MSG_REQUEST_STATUS = 2;
	public static final int MSG_STATUS_RESPONSE = 3;
	
	private static final int VIBRATE_DURATION = 500;
	private static final int ALARM_INTERVAL = 30000;
	
	final Messenger serviceMessenger = new Messenger(new MessageHandler(this));
	Messenger activityMessenger;

	AuroraStatusRetriever retriever = new AuroraStatusRetriever();
	AlarmManager manager;
	PendingIntent pendingIntent;
	AlarmReceiver receiver;
	AuroraStatus lastStatus;
	int minLevel;

	@Override
	public void onCreate() {
        Log.v(getClass().getSimpleName(), "Creating and initializing aurora service...");

        receiver = new AlarmReceiver();
        registerReceiver(receiver, new IntentFilter("com.github.dementati.aurelia.RETRIEVE_DATA"));
        Intent alarmIntent = new Intent("com.github.dementati.aurelia.RETRIEVE_DATA");
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
    	
    	manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    	manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 
    			ALARM_INTERVAL, pendingIntent);
	}
	
	@Override
	public void onDestroy() {
        Log.v(getClass().getSimpleName(), "Destroying aurora service...");
        
        unregisterReceiver(receiver);
        
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(getClass().getSimpleName(), "Start command received.");

    	return START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
        Log.v(getClass().getSimpleName(), "Service was bound");
        
		return serviceMessenger.getBinder();
	}

	private void update() {
    	new StatusRetrievalTask(minLevel).execute();
	}
	
	private void setStatus(AuroraStatus status) {
		assert status != null : "Status must not be null";

		lastStatus = status;
		
		if(status.notify) {
		    Log.v(getClass().getSimpleName(), "Should notify, vibrating...");
	    	
	    	Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	    	vibrator.vibrate(VIBRATE_DURATION);
	    }

	}

	public void sendLastStatus() {
		sendStatus(lastStatus);
	}
	
	public void sendStatus(AuroraStatus status) {
		assert status != null : "Status must not be null";
		
		if(activityMessenger != null) {
		    Log.v(getClass().getSimpleName(), "Sending status to activity...");
		    
		    Bundle bundle = new Bundle();
			bundle.putParcelable("status", status);

		    Message statusResponseMsg = new Message();
		    statusResponseMsg.what = MSG_STATUS_RESPONSE;
			statusResponseMsg.setData(bundle);
			
			try {
				activityMessenger.send(statusResponseMsg);
			} catch (RemoteException e) {
				Log.e(getClass().getSimpleName(), e.getMessage());
			}
		} else {
		    Log.v(getClass().getSimpleName(), "Would send status to activity, but no activity has bound the service");
		}
	}
	
	private class StatusRetrievalTask extends AsyncTask<Void, Void, AuroraStatus> {
    	private int minLevel;
    	
    	public StatusRetrievalTask(int minLevel) {
    		assert minLevel >= 0 && minLevel <= 9 : "Minimum level must be in [0, 9]";
    		
    		this.minLevel = minLevel;
    	}
    	
    	@Override
    	protected AuroraStatus doInBackground(Void... params) {
    		return retriever.retrieve(minLevel);
    	}
    	
    	@Override
    	protected void onPostExecute(AuroraStatus result) {
    		setStatus(result);
			sendStatus(result);
    	}
    }
	
	public class AlarmReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			update();
		}
    }
	
	static class MessageHandler extends Handler {
		private WeakReference<AuroraService> service;
		
		public MessageHandler(AuroraService pService) {
			this.service = new WeakReference<AuroraService>(pService);
		}
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case MSG_SUPPLY_MESSENGER:
					Log.v(AuroraService.class.getSimpleName(), "Received activity messenger");
					service.get().activityMessenger = msg.replyTo;
					break;
					
				case MSG_REQUEST_STATUS:
					Log.v(AuroraService.class.getSimpleName(), "Received status request");
					service.get().sendLastStatus();
					break;
					
				default:
					Log.e(AuroraService.class.getSimpleName(), "Received unexpected message with what = " + msg.what);
					break;
			}
		}
	}
}
