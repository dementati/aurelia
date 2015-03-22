package com.github.dementati.aurelia;

import com.github.dementati.aurelia.AuroraStatusRetriever.AuroraStatus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	private static final int ALARM_INTERVAL = 30000;
	private static final int VIBRATE_DURATION = 500;
	
	AuroraStatusRetriever retriever = new AuroraStatusRetriever();
	AlarmManager manager;
	PendingIntent pendingIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        this.registerReceiver(new AlarmReceiver(), new IntentFilter("com.github.dementati.aurelia.RETRIEVE_DATA"));
        
        Intent alarmIntent = new Intent("com.github.dementati.aurelia.RETRIEVE_DATA");
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        startAlarm();
        Log.i("MainActivity", "onCreate called");
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
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	update();
    }
    
    public void startAlarm() {
    	manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    	manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 
    			ALARM_INTERVAL, pendingIntent);
    }
    
    public void update() {
    	TextView outputText = (TextView)findViewById(R.id.output);
    	TextView explanationText = (TextView)findViewById(R.id.explanation);
    	outputText.setText(R.string.output_loading);
		outputText.setTextColor(getResources().getColor(R.color.neutral));
		explanationText.setText("");
		
	    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		int minLevel = pref.getInt("pref_level", 2);
		
    	new StatusRetrievalTask(minLevel).execute();
    }
    
    public void openSettings() {
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    }
    
    private void setState(AuroraStatus status) {
    	TextView outputText = (TextView)findViewById(R.id.output);
	    TextView explanationText = (TextView)findViewById(R.id.explanation);
    	outputText.setText(status.text);
	    outputText.setTextColor(getResources().getColor(status.color));
	    explanationText.setText(getString(status.explanation, status.level));
	    
	    if(status.notify) {
	    	Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	    	vibrator.vibrate(VIBRATE_DURATION);
	    }
    }
    
    private class StatusRetrievalTask extends AsyncTask<Void, Void, AuroraStatus> {
    	private int minLevel;
    	
    	public StatusRetrievalTask(int minLevel) {
    		this.minLevel = minLevel;
    	}
    	
    	@Override
    	protected AuroraStatus doInBackground(Void... params) {
    		return retriever.retrieve(minLevel);
    	}
    	
    	@Override
    	protected void onPostExecute(AuroraStatus result) {
    		setState(result);
    	}
    }
    
    public class AlarmReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("AlarmReceiver", "onReceive called");
			update();
		}
    	
    }
}
