package com.github.dementati.aurelia;

import com.github.dementati.aurelia.levelretriever.AuroraServiceEuRetriever;
import com.github.dementati.aurelia.levelretriever.GeophysInstRetriever;
import com.github.dementati.aurelia.levelretriever.LevelAggregator;

public class DataRetrieverImpl implements DataRetriever {
	private final LevelAggregator levelAggregator = new LevelAggregator();

	public DataRetrieverImpl() {
		levelAggregator.addRetriever(new AuroraServiceEuRetriever());
		levelAggregator.addRetriever(new GeophysInstRetriever());
	}
	
	/* (non-Javadoc)
	 * @see com.github.dementati.aurelia.DataRetriever#retrieve()
	 */
	@Override
	public DataRetrievalResult retrieve() {
		DataRetrievalResult result = new DataRetrievalResult();
	    result.currentLevel = levelAggregator.retrieveLevel();
	    result.weather = YrRetriever.retrieveWeather("Sweden", "V%C3%A4sterbotten", "Ume%C3%A5");
	    
	    return result;
	}
}
