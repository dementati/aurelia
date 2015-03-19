package com.github.dementati.aurelia;

import com.github.dementati.aurelia.DataRetriever.DataRetrievalResult;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	private static final int ALARM_INTERVAL = 30000;
	
	DataRetriever retriever = new DataRetriever();
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
    	outputText.setText(R.string.output_neutral);
		outputText.setTextColor(getResources().getColor(R.color.neutral));
		explanationText.setText("");
   

    	new DataRetrievalTask().execute();
    }
    
    public void openSettings() {
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    }
    
    private void updateUi(DataRetrievalResult result) {
	    
	    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		int minLevel = pref.getInt("pref_level", 2);

		if(result.currentLevel == AuroraLevelRetriever.NO_LEVEL) {
		    setState(R.string.output_neutral, R.color.neutral, R.string.explanation_confused, result.currentLevel);
		}
		else if(result.currentLevel >= minLevel) {
		    switch(result.weather) {
			    case FAIR: 
				    setState(R.string.output_go, R.color.go, R.string.explanation_go_fair, result.currentLevel);
				    break;
				    
			    case PARTLY_CLOUDY:
				    setState(R.string.output_go, R.color.go, R.string.explanation_go_partly_cloudy, result.currentLevel);
				    break;
				    
			    case HEAVY_RAIN:
			    case RAIN_SHOWERS:
			    case RAIN:
				    setState(R.string.output_stay, R.color.stay, R.string.explanation_stay_rainy, result.currentLevel);
				    break;
			    
			    case SLEET:
			    case SNOW:
				    setState(R.string.output_stay, R.color.stay, R.string.explanation_stay_snow, result.currentLevel);
				    break;
				    
			    case CLOUDY:
				    setState(R.string.output_stay, R.color.stay, R.string.explanation_stay_cloudy, result.currentLevel);
				    break;
				    
			    default:
				    setState(R.string.output_neutral, R.color.neutral, R.string.explanation_confused, result.currentLevel);
				    break;
		    }
	    } else {
		    setState(R.string.output_stay, R.color.stay, R.string.explanation_stay_level, result.currentLevel);
	    }
    }
    
    private void setState(int text, int color, int explanation, double level) {
    	TextView outputText = (TextView)findViewById(R.id.output);
	    TextView explanationText = (TextView)findViewById(R.id.explanation);
    	outputText.setText(text);
	    outputText.setTextColor(getResources().getColor(color));
	    explanationText.setText(getString(explanation, level));
    }
    
    private class DataRetrievalTask extends AsyncTask<Void, Void, DataRetrievalResult> {
    	@Override
    	protected DataRetrievalResult doInBackground(Void... params) {
    		return retriever.retrieve();
    	}
    	
    	@Override
    	protected void onPostExecute(DataRetrievalResult result) {
    		updateUi(result);
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
