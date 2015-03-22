package com.github.dementati.aurelia.levelretriever;

public interface LevelRetriever {

	static final double NO_LEVEL = -1.0;
	
	public abstract double retrieveLevel();

}