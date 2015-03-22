package com.github.dementati.aurelia.levelretriever;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class GeophysInstRetriever implements LevelRetriever {	
	/* (non-Javadoc)
	 * @see com.github.dementati.aurelia.AuroraLevelRetriever#retrieveLevel()
	 */
	@Override
	public double retrieveLevel() {
		String url = "http://auroraforecast.gi.alaska.edu";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements spans = doc.select(".main .levels>span");
			if(spans.size() == 0) {
				return NO_LEVEL;
			} else {
				return Double.parseDouble(spans.get(1).text());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return NO_LEVEL;
	}
}
