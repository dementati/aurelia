package com.github.dementati.aurelia.levelretriever;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;


public class GeophysInstRetriever implements LevelRetriever {	
	/* (non-Javadoc)
	 * @see com.github.dementati.aurelia.AuroraLevelRetriever#retrieveLevel()
	 */
	@Override
	public double retrieveLevel() {
		Log.v(this.getClass().getSimpleName(), "Retrieving level from auroraforecast.gi.alaska.edu...");
		String url = "http://auroraforecast.gi.alaska.edu";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements spans = doc.select(".main .levels>span");
			if(spans.size() == 0) {
                Log.e(this.getClass().getSimpleName(), "Couldn't parse a level from the retrieved page.");
				return NO_LEVEL;
			} else {
				double level = Double.parseDouble(spans.get(1).text());
                Log.v(this.getClass().getSimpleName(), "Retrieved level " + level);
				return level;
			}
		} 
		catch (SocketTimeoutException e) {
		    Log.e(this.getClass().getSimpleName(), "Couldn't retrieve page from " + url + ", socket timed out.");
		} 
		catch (IOException e) {
		    Log.e(this.getClass().getSimpleName(), "Couldn't retrieve page from " + url + ", unspecified I/O error.");
		}
		
		return NO_LEVEL;
	}
}
