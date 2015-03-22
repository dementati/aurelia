package com.github.dementati.aurelia.levelretriever;

import java.util.ArrayList;


public class LevelAggregator implements LevelRetriever {
	private ArrayList<LevelRetriever> retrievers = new ArrayList<LevelRetriever>();

	public void addRetriever(LevelRetriever retriever) {
		retrievers.add(retriever);
	}
	
	@Override
	public double retrieveLevel() {
		double sum = 0;
		int retrieverCount = 0;
		for(LevelRetriever retriever : retrievers) {
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
