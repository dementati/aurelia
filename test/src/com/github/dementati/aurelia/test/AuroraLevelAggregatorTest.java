package com.github.dementati.aurelia.test;

import com.github.dementati.aurelia.AuroraLevelAggregator;
import com.github.dementati.aurelia.AuroraLevelRetriever;
import com.github.dementati.aurelia.AuroraServiceEuRetriever;
import com.github.dementati.aurelia.GeophysInstRetriever;

import junit.framework.TestCase;

public class AuroraLevelAggregatorTest extends TestCase {
	public void testAggregation() {
		AuroraLevelAggregator aggregator = new AuroraLevelAggregator();
		aggregator.addRetriever(new AuroraLevelRetriever() {
			@Override
			public double retrieveLevel() {
				// TODO Auto-generated method stub
				return 2;
			}
		});
		aggregator.addRetriever(new AuroraLevelRetriever() {
			@Override
			public double retrieveLevel() {
				// TODO Auto-generated method stub
				return 3;
			}
		});
		aggregator.addRetriever(new AuroraLevelRetriever() {
			@Override
			public double retrieveLevel() {
				// TODO Auto-generated method stub
				return NO_LEVEL;
			}
		});
		
		assertEquals(2.5, aggregator.retrieveLevel());
	}
	
	public void testLive() {
		AuroraLevelAggregator aggregator = new AuroraLevelAggregator();
		aggregator.addRetriever(new GeophysInstRetriever());
		aggregator.addRetriever(new AuroraServiceEuRetriever());
		
		double level = aggregator.retrieveLevel();
		assertTrue((0 <= level && level <= 9) || level == AuroraLevelRetriever.NO_LEVEL);
	}
}
