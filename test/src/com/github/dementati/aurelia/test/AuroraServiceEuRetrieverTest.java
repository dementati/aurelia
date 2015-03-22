package com.github.dementati.aurelia.test;

import com.github.dementati.aurelia.levelretriever.AuroraServiceEuRetriever;

import junit.framework.TestCase;

public class AuroraServiceEuRetrieverTest extends TestCase {
	public void testRetrieveLevel() {
		AuroraServiceEuRetriever retriever = new AuroraServiceEuRetriever();
		double level = retriever.retrieveLevel();
		assertTrue(0 <= level && level <= 9);
	}
}