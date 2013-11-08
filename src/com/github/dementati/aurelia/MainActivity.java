package com.github.dementati.aurelia;

import com.github.dementati.aurelia.YrRetriever.Weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	update();
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
    
    private class DataRetrievalResult {
		int currentLevel;
		int minLevel; 
		YrRetriever.Weather weather;
	}
    
    private class DataRetrievalTask extends AsyncTask<Void, Void, DataRetrievalResult> {
    	
    	
    	@Override
    	protected DataRetrievalResult doInBackground(Void... params) {
    		DataRetrievalResult result = new DataRetrievalResult();
    		result.currentLevel = GeophysInstRetriever.retrieveLevel();
    		
    		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
    		result.minLevel = pref.getInt("pref_level", 2);
    		
    		result.weather = YrRetriever.retrieveWeather("Sweden", "Västerbotten", "Umeå");
    		
    		return result;
    	}
    	
    	@Override
    	protected void onPostExecute(DataRetrievalResult result) {
    		TextView outputText = (TextView)findViewById(R.id.output);
    		TextView explanationText = (TextView)findViewById(R.id.explanation);
    		
    		if(result.currentLevel >= result.minLevel && result.weather == YrRetriever.Weather.FAIR) {
    			outputText.setText(R.string.output_go);
    			outputText.setTextColor(getResources().getColor(R.color.go));
    			explanationText.setText(getString(R.string.explanation_go, result.currentLevel));
    		} else if(result.currentLevel < result.minLevel) {
    			outputText.setText(R.string.output_stay);
    			outputText.setTextColor(getResources().getColor(R.color.stay));
    			explanationText.setText(getString(R.string.explanation_stay_level, result.currentLevel));
    		} else {
    			outputText.setText(R.string.output_stay);
    			outputText.setTextColor(getResources().getColor(R.color.stay));
    			explanationText.setText(getString(R.string.explanation_stay_cloudy, result.currentLevel));
    		}
    	}
    }
}
