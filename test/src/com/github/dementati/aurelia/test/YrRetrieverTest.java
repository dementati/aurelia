package com.github.dementati.aurelia.test;

import java.util.Arrays;

import com.github.dementati.aurelia.YrRetriever;

import junit.framework.TestCase;

public class YrRetrieverTest extends TestCase {
	private static YrRetriever.Weather[] weathers = new YrRetriever.Weather[] {
		YrRetriever.Weather.CLOUDY,
		YrRetriever.Weather.FAIR,
		YrRetriever.Weather.HEAVY_RAIN,
		YrRetriever.Weather.PARTLY_CLOUDY,
		YrRetriever.Weather.RAIN,
		YrRetriever.Weather.RAIN_SHOWERS,
		YrRetriever.Weather.SLEET,
		YrRetriever.Weather.SNOW,
		YrRetriever.Weather.LIGHT_SNOW
	};
	
	public void testRetrieveWeatherCopenhagen() {
		String country = "Denmark";
		String region = "Capital";
		String city = "Copenhagen";

		assertTrue(Arrays.asList(weathers).contains(YrRetriever.retrieveWeather(country, region, city)));
	}
	
	public void testRetrieveWeatherUmea() {
		String country = "Sweden";
		String region = "Västerbotten";
		String city = "Umeå";
		
		assertTrue(Arrays.asList(weathers).contains(YrRetriever.retrieveWeather(country, region, city)));
	}
	
	public void testRetrieveWeatherSaoPaulo() {
		String country = "Brazil";
		String region = "São_Paulo";
		String city = "São_Paulo";
		
		assertTrue(Arrays.asList(weathers).contains(YrRetriever.retrieveWeather(country, region, city)));
	}
}
