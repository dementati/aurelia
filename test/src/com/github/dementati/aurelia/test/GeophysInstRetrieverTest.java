package com.github.dementati.aurelia.test;

import com.github.dementati.aurelia.GeophysInstRetriever;

import junit.framework.TestCase;

public class GeophysInstRetrieverTest extends TestCase {
	public void testRetrieveLevel() {
		assertEquals(3, GeophysInstRetriever.retrieveLevel());
	}
}