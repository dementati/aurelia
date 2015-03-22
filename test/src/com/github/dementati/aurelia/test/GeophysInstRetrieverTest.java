package com.github.dementati.aurelia.test;

import com.github.dementati.aurelia.levelretriever.GeophysInstRetriever;

import junit.framework.TestCase;

public class GeophysInstRetrieverTest extends TestCase {
	public void testRetrieveLevel() {
		GeophysInstRetriever retriever = new GeophysInstRetriever();
		double level = retriever.retrieveLevel();
		assertTrue(0 <= level && level <= 9);
	}
}