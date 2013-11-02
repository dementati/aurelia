package com.github.dementati.aurelia;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	UserState userState = new UserState();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        String url = "http://www.gi.alaska.edu/AuroraForecast/Europe/2013/11/02";
        new DataRetrievalTask().execute(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private class DataRetrievalTask extends AsyncTask<String, Void, String> {
    	@Override
    	protected String doInBackground(String... urls) {
    		return Integer.toString(userState.retrieveLevel());
    	}
    	
    	@Override
    	protected void onPostExecute(String result) {
    		TextView textView = (TextView)findViewById(R.id.hello_world);
			textView.setText(result);
    	}
    }
    
}
