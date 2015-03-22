package com.github.dementati.aurelia.test;

import com.github.dementati.aurelia.levelretriever.AuroraServiceEuRetriever;
import com.github.dementati.aurelia.levelretriever.GeophysInstRetriever;
import com.github.dementati.aurelia.levelretriever.LevelAggregator;
import com.github.dementati.aurelia.levelretriever.LevelRetriever;

import junit.framework.TestCase;

public class AuroraLevelAggregatorTest extends TestCase {
	public void testAggregation() {
		LevelAggregator aggregator = new LevelAggregator();
		aggregator.addRetriever(new LevelRetriever() {
			@Override
			public double retrieveLevel() {
				// TODO Auto-generated method stub
				return 2;
			}
		});
		aggregator.addRetriever(new LevelRetriever() {
			@Override
			public double retrieveLevel() {
				// TODO Auto-generated method stub
				return 3;
			}
		});
		aggregator.addRetriever(new LevelRetriever() {
			@Override
			public double retrieveLevel() {
				// TODO Auto-generated method stub
				return NO_LEVEL;
			}
		});
		
		assertEquals(2.5, aggregator.retrieveLevel());
	}
	
	public void testLive() {
		LevelAggregator aggregator = new LevelAggregator();
		aggregator.addRetriever(new GeophysInstRetriever());
		aggregator.addRetriever(new AuroraServiceEuRetriever());
		
		double level = aggregator.retrieveLevel();
		assertTrue((0 <= level && level <= 9) || level == LevelRetriever.NO_LEVEL);
	}
}
