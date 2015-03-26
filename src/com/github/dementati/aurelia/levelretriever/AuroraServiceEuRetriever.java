package com.github.dementati.aurelia.levelretriever;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;


public class AuroraServiceEuRetriever implements LevelRetriever {	
	@Override
	public double retrieveLevel() {
		Log.v(this.getClass().getSimpleName(), "Retrieving level from aurora-service.eu...");
		
		String url = "http://www.aurora-service.eu/aurora-forecast";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements spans = doc.select("#hourly>h4>span");
			if(spans.size() == 0) {
                Log.e(this.getClass().getSimpleName(), "Couldn't parse a level from the retrieved page.");
				return NO_LEVEL;
			} else {
				String raw = spans.get(0).text();
				raw = raw.replace("Kp ", "");
				double level = Double.parseDouble(raw); 
                Log.v(this.getClass().getSimpleName(), "Retrieved level " + level);
				return level;
			}
		} 
		catch (SocketTimeoutException e) {
		    Log.e(this.getClass().getName(), "Couldn't retrieve page from " + url + ", socket timed out.");
		} 
		catch (IOException e) {
		    Log.e(this.getClass().getName(), "Couldn't retrieve page from " + url + ", unspecified I/O error.");
		}
	
		return NO_LEVEL;
	}
}
