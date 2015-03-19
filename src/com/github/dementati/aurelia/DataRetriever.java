package com.github.dementati.aurelia;

public class DataRetriever {
	private final AuroraLevelAggregator levelAggregator = new AuroraLevelAggregator();
	private int minLevel = 3;

	public DataRetriever() {
		levelAggregator.addRetriever(new AuroraServiceEuRetriever());
		levelAggregator.addRetriever(new GeophysInstRetriever());
	}
	
	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public DataRetrievalResult retrieve() {
		DataRetrievalResult result = new DataRetrievalResult();
	    result.currentLevel = levelAggregator.retrieveLevel();
	    result.weather = YrRetriever.retrieveWeather("Sweden", "V%C3%A4sterbotten", "Ume%C3%A5");
	    
	    return result;
	}
	
	class DataRetrievalResult {
		double currentLevel;
		YrRetriever.Weather weather;
	}
}
