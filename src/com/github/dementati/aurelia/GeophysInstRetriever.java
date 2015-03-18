package com.github.dementati.aurelia;

import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public class GeophysInstRetriever {	
	public static int retrieveLevel(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		String url = "http://auroraforecast.gi.alaska.edu";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements spans = doc.select(".main .levels>span");
			if(spans.size() == 0) {
				return -1;
			} else {
				return Integer.parseInt(spans.get(1).text());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static int retrieveLevel() {
		Calendar calendar = Calendar.getInstance();
		
		int level = retrieveLevel(calendar);
		if(level == -1) { // Data for today may not be published, try for yesterday.
			calendar.add(Calendar.DATE, -1);
			return retrieveLevel(calendar);
		} else {
			return level;
		}
	}
}
