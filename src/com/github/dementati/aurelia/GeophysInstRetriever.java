package com.github.dementati.aurelia;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public class GeophysInstRetriever {
	public int retrieveLevel(String region, int year, int month, int day) {
		Log.i("GeophysInstRetriever", "retrieveLevel(" + region + ", " + year + ", " + month + ", " + day + ")");
		
		String url = "http://www.gi.alaska.edu/AuroraForecast/" + region + "/" + year + "/" + month + "/" + day;
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
}
