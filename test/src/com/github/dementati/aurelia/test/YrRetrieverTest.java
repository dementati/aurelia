package com.github.dementati.aurelia.test;

import com.github.dementati.aurelia.YrRetriever;

import junit.framework.TestCase;

public class YrRetrieverTest extends TestCase {
	public void testRetrieveWeatherCopenhagen() {
		String country = "Denmark";
		String region = "Capital";
		String city = "Copenhagen";
		
		assertEquals(YrRetriever.Weather.CLOUDY, YrRetriever.retrieveWeather(country, region, city));
	}
	
	public void testRetrieveWeatherUmea() {
		String country = "Sweden";
		String region = "Västerbotten";
		String city = "Umeå";
		
		assertEquals(YrRetriever.Weather.CLOUDY, YrRetriever.retrieveWeather(country, region, city));
	}
	
	public void testRetrieveWeatherSaoPaulo() {
		String country = "Brazil";
		String region = "São_Paulo";
		String city = "São_Paulo";
		
		assertEquals(YrRetriever.Weather.PARTLY_CLOUDY, YrRetriever.retrieveWeather(country, region, city));
	}
}
