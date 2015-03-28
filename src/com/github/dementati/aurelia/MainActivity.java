package com.github.dementati.aurelia;


import java.lang.ref.WeakReference;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	Messenger serviceMessenger;
	Messenger activityMessenger = new Messenger(new MessageHandler(this));
	AuroraServiceConnection serviceConnection = new AuroraServiceConnection();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.v(getClass().getSimpleName(), "Initializing main activity...");
        
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        Intent intent = new Intent(this, AuroraService.class);
        startService(intent);
    }
   
    @Override
    protected void onStart() {
    	super.onStart();

    	Log.v(getClass().getSimpleName(), "Starting activity...");
    	
    	Log.v(getClass().getSimpleName(), "Binding aurora service...");
        Intent intent = new Intent(this, AuroraService.class);
        bindService(intent, serviceConnection, 0);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	
    	Log.v(getClass().getSimpleName(), "Stopping activity...");
    	
    	Log.v(getClass().getSimpleName(), "Unbinding aurora service...");
    	unbindService(serviceConnection);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();

    	Log.v(getClass().getSimpleName(), "Resuming activity.");

    	requestStatus();
    }
    
    private void requestStatus() {
    	if(serviceMessenger != null) {
		    Message requestStatusMsg = new Message();
		    requestStatusMsg.what = AuroraService.MSG_REQUEST_STATUS;
		    try {
				serviceMessenger.send(requestStatusMsg);
			} catch (RemoteException e) {
				Log.e(getClass().getSimpleName(), e.getMessage());
			}
    	} else {
    		Log.e(getClass().getSimpleName(), "Would request aurora status from service, but there is no service messenger");
    	}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.action_settings:
    			openSettings();
    			return true;
    			
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    }  
    
    public void update() {
    	Log.v(getClass().getSimpleName(), "Initiating status update");
    	
    	TextView outputText = (TextView)findViewById(R.id.output);
    	TextView explanationText = (TextView)findViewById(R.id.explanation);
    	outputText.setText(R.string.output_loading);
		outputText.setTextColor(getResources().getColor(R.color.neutral));
		explanationText.setText("");
		
	    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		int minLevel = pref.getInt("pref_level", 2);
    }
    
    public void openSettings() {
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    }
    
    public void setStatus(AuroraStatus status) {
    	assert status != null : "Aurora Status to set cannot be null";
    	
    	Log.v(getClass().getSimpleName(), "Setting new status " + status);
    	
    	TextView outputText = (TextView)findViewById(R.id.output);
	    TextView explanationText = (TextView)findViewById(R.id.explanation);
    	outputText.setText(status.text);
	    outputText.setTextColor(getResources().getColor(status.color));
	    explanationText.setText(getString(status.explanation, status.level));
    }
    
    class AuroraServiceConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v(getClass().getSimpleName(), "Service connected.");
			
			serviceMessenger = new Messenger(service);
			
			Message supplyMessengerMsg = new Message();
			supplyMessengerMsg.what = AuroraService.MSG_SUPPLY_MESSENGER;
			supplyMessengerMsg.replyTo = activityMessenger;
			try {
				serviceMessenger.send(supplyMessengerMsg);
			} catch (RemoteException e) {
				Log.e(getClass().getSimpleName(), e.getMessage());
			}
			
			requestStatus();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serviceMessenger = null;
		}
    }
    
    static class MessageHandler extends Handler {
    	private final WeakReference<MainActivity> activity;
    	
    	public MessageHandler(MainActivity pActivity) {
    		this.activity = new WeakReference<MainActivity>(pActivity);
		}
    	
    	@Override
    	public void handleMessage(Message msg) {
    		switch(msg.what) {
			    case AuroraService.MSG_STATUS_RESPONSE:
			    	AuroraStatus status = msg.getData().getParcelable("status");
			    	Log.v(MainActivity.class.getSimpleName(), "Received new status from service: " + status);
			    	activity.get().setStatus(status);
				    break;
    		}
    	}
    }
}
