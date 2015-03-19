package com.github.dementati.aurelia;

import java.util.ArrayList;

public class AuroraLevelAggregator implements AuroraLevelRetriever {
	private ArrayList<AuroraLevelRetriever> retrievers = new ArrayList<AuroraLevelRetriever>();

	public void addRetriever(AuroraLevelRetriever retriever) {
		retrievers.add(retriever);
	}
	
	@Override
	public double retrieveLevel() {
		double sum = 0;
		int retrieverCount = 0;
		for(AuroraLevelRetriever retriever : retrievers) {
			double level = retriever.retrieveLevel();
			if(level != NO_LEVEL) {
				retrieverCount++;
				sum += retriever.retrieveLevel();
			}
		}
		
		if(retrieverCount > 0) {
			return sum/retrieverCount;
		} else {
			return NO_LEVEL;
		}
	}
}
