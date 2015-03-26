package com.github.dementati.aurelia.levelretriever;

import java.util.ArrayList;

import android.util.Log;


public class LevelAggregator implements LevelRetriever {
	private ArrayList<LevelRetriever> retrievers = new ArrayList<LevelRetriever>();

	public void addRetriever(LevelRetriever retriever) {
		retrievers.add(retriever);
	}
	
	@Override
	public double retrieveLevel() {
		Log.v(getClass().getSimpleName(), "Retrieveing aggregate level...");
		double sum = 0;
		int retrieverCount = 0;
		for(LevelRetriever retriever : retrievers) {
			double level = retriever.retrieveLevel();
			if(level != NO_LEVEL) {
				retrieverCount++;
				sum += level;
			}
		}
		
		if(retrieverCount > 0) {
			double avgLevel = sum/retrieverCount;
			Log.v(getClass().getSimpleName(), "Retrieved an average level of " + avgLevel);
			return avgLevel;
		} else {
			Log.v(getClass().getSimpleName(), "Retrieved no levels.");
			return NO_LEVEL;
		}
	}
}
