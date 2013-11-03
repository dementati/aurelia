package com.github.dementati.aurelia;

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
    	TextView textView = (TextView)findViewById(R.id.output);
    	textView.setText(R.string.output_neutral);
		textView.setTextColor(getResources().getColor(R.color.neutral));
    	
    	new DataRetrievalTask().execute();
    }
    
    public void openSettings() {
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    }
    
    private class DataRetrievalTask extends AsyncTask<Void, Void, Boolean> {
    	@Override
    	protected Boolean doInBackground(Void... params) {
    		int currentLevel = GeophysInstRetriever.retrieveLevel();
    		
    		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
    		int minLevel = pref.getInt("pref_level", 2);
    		
    		return Boolean.valueOf(currentLevel >= minLevel);
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean result) {
    		TextView textView = (TextView)findViewById(R.id.output);
    		
    		if(result.booleanValue()) {
    			textView.setText(R.string.output_go);
    			textView.setTextColor(getResources().getColor(R.color.go));
    		} else {
    			textView.setText(R.string.output_stay);
    			textView.setTextColor(getResources().getColor(R.color.stay));
    		}
    	}
    }
}
