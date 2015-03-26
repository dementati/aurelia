package com.github.dementati.aurelia;

public class DataRetrievalResult {
	double currentLevel;
	YrRetriever.Weather weather;
	
	public DataRetrievalResult() {
	};
	
	public DataRetrievalResult(double currentLevel, YrRetriever.Weather weather) {
		this.currentLevel = currentLevel;
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "DataRetrievalResult [currentLevel=" + currentLevel
				+ ", weather=" + weather + "]";
	}
	
}