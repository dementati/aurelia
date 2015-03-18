package com.github.dementati.aurelia;

public class AuroraLevelAggregator implements AuroraLevelRetriever {
	private AuroraLevelRetriever[] retrievers = new AuroraLevelRetriever[] {
		new AuroraServiceEuRetriever(),
		new GeophysInstRetriever()
	};

	@Override
	public double retrieveLevel() {
		double sum = 0;
		for(AuroraLevelRetriever retriever : retrievers) {
			sum += retriever.retrieveLevel();
		}
		return sum/retrievers.length;
	}
}
