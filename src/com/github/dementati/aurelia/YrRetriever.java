package com.github.dementati.aurelia;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

public class YrRetriever {
	public enum Weather {
		CLOUDY,
		PARTLY_CLOUDY,
		HEAVY_RAIN,
		RAIN,
		SLEET,
		FAIR,
		SNOW,
		RAIN_SHOWERS,
		UNKNOWN
	}
	
	public static Weather retrieveWeather(String country, String region, String city) {		
		String url = "http://www.yr.no/place/" + country + "/" + region + "/" + city;
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Element img = doc.select("table[class=yr-table yr-table-overview2 yr-popup-area]:first-child > tbody > tr > td:eq(1) > img").last();
			String weatherStr = img.attr("alt");
			if(weatherStr.equals("Cloudy")) {
				return Weather.CLOUDY;
			} else if(weatherStr.equals("Partly cloudy")) {
				return Weather.PARTLY_CLOUDY;
			} else if(weatherStr.equals("Heavy rain")) {
				return Weather.HEAVY_RAIN;
			} else if(weatherStr.equals("Rain")) {
				return Weather.RAIN;
			} else if(weatherStr.equals("Sleet")) {
				return Weather.SLEET;
			} else if(weatherStr.equals("Fair")) {
				return Weather.FAIR;
			} else if(weatherStr.equals("Snow")) {
				return Weather.SNOW;
			} else if(weatherStr.equals("Rain showers")) {
				return Weather.RAIN_SHOWERS;
			} else {
				return Weather.UNKNOWN;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Weather.UNKNOWN;
	}
}
