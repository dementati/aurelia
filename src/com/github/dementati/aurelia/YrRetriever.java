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

	private static String getWeatherStr(String url) {
		assert url != null : "Weather site URL cannot be null";
		
		Log.v(YrRetriever.class.getSimpleName(), "Retrieving today's weather...");
		
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			
			String selectStr = "body > div:eq(3) > table > tbody > tr:last-child > td:first-child > img";
			selectStr = "body > div:eq(3) > table > tbody > tr:last-child > td > img";
			//Log.i("YrRetriever", "selectStr = " + selectStr);
			Element img = doc.select(selectStr).first();
			
			if(img != null) {
				String weatherStr = img.attr("alt");
				Log.v(YrRetriever.class.getSimpleName(), "Retrieved raw weather string " + weatherStr);
				return weatherStr;
			} else {
				Log.v(YrRetriever.class.getSimpleName(), "Couldn't retrieve weather string for today, trying to retrieve for tomorrow...");
				return getTomorrowsWeatherStr(url);
			}
		} catch (IOException e) {
			Log.e(YrRetriever.class.getSimpleName(), e.getMessage());
			return null;
		}
	}
	
	private static String getTomorrowsWeatherStr(String url) {
		assert url != null : "Tomorrow's weather site URL cannot be null";
		
		url = url + "/hour_by_hour_tomorrow.html";
		
		Log.v(YrRetriever.class.getSimpleName(), "Retrieving tomorrow's weather...");
		
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			
			String selectStr = "body > div:eq(3) > table > tbody > tr:last-child > td:first-child > img";
			selectStr = "body > div:eq(3) > table > tbody > tr:last-child > td > img";
			Element img = doc.select(selectStr).first();
			
			String weatherStr = img.attr("alt");
			Log.v(YrRetriever.class.getSimpleName(), "Retrieved raw weather string " + weatherStr);
			return img.attr("alt");
		} catch (IOException e) {
			Log.e(YrRetriever.class.getSimpleName(), e.getMessage());
			return null;
		}
	}
	
	public static Weather retrieveWeather(String country, String region, String city) {		
		assert country != null : "Country cannot be null";
		assert region != null : "Region cannot be null";
		assert city != null : "City cannot be null";

		Log.v(YrRetriever.class.getSimpleName(), String.format("Retrieving weather data for %s/%s/%s from yr.no...", country, region, city));
		
		String url = "http://m.yr.no/place/" + country + "/" + region + "/" + city;

        String weatherStr = getWeatherStr(url);
        Weather weather;
        if(weatherStr.equals("Cloudy")) {
			weather = Weather.CLOUDY;
        } else if(weatherStr.equals("Partly cloudy")) {
			weather = Weather.PARTLY_CLOUDY;
        } else if(weatherStr.equals("Heavy rain")) {
			weather = Weather.HEAVY_RAIN;
        } else if(weatherStr.equals("Rain")) {
			weather = Weather.RAIN;
        } else if(weatherStr.equals("Sleet")) {
			weather = Weather.SLEET;
        } else if(weatherStr.equals("Fair")) {
			weather = Weather.FAIR;
        } else if(weatherStr.equals("Snow")) {
			weather = Weather.SNOW;
        } else if(weatherStr.equals("Rain showers")) {
        	weather = Weather.RAIN_SHOWERS;
        } else {
        	weather = Weather.UNKNOWN;
        }
        
        Log.v(YrRetriever.class.getSimpleName(), "Retrieved weather " + weather);
        
        return weather;
	}
}
