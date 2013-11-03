package com.github.dementati.aurelia;

import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public class GeophysInstRetriever {	
	public static int retrieveLevel(int year, int month, int day) {
		Log.i("GeophysInstRetriever", "retrieveLevel(" + year + ", " + month + ", " + day + ")");
		
		String url = "http://www.gi.alaska.edu/AuroraForecast/Europe/" + year + "/" + month + "/" + day;
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements spans = doc.select(".main .levels>span");
			return Integer.parseInt(spans.get(1).text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static int retrieveLevel() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		return retrieveLevel(year, month, day);
	}
}
