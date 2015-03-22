package com.github.dementati.aurelia.test;

import com.github.dementati.aurelia.AuroraStatusRetriever;
import com.github.dementati.aurelia.AuroraStatusRetriever.AuroraStatus;
import com.github.dementati.aurelia.DataRetrievalResult;
import com.github.dementati.aurelia.DataRetriever;
import com.github.dementati.aurelia.YrRetriever.Weather;
import com.github.dementati.aurelia.levelretriever.LevelRetriever;
import com.github.dementati.aurelia.R;

import junit.framework.TestCase;

public class AuroraStatusRetrieverTest extends TestCase {
	private static final double DEFAULT_LEVEL = 3.0;
	private static final int DEFAULT_MIN_LEVEL = 3;
	private static final Weather DEFAULT_WEATHER = Weather.FAIR;
	
	public void testLowLevelUnknownWeather() {
		double currentLevel = 0;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.UNKNOWN);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_level, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testLowLevelGoodWeather() {
		double currentLevel = 0;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.FAIR);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_level, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testLowLevelBadWeather() {
		double currentLevel = 0;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.CLOUDY);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_level, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelUnknownWeather() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.UNKNOWN);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_confused, status.text);
		assertEquals(R.color.neutral, status.color);
		assertEquals(R.string.explanation_confused, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelCloudy() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.CLOUDY);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_cloudy, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelFair() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.FAIR);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_go, status.text);
		assertEquals(R.color.go, status.color);
		assertEquals(R.string.explanation_go_fair, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelHeavyRain() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.HEAVY_RAIN);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_rainy, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelPartlyCloudy() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.PARTLY_CLOUDY);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_go, status.text);
		assertEquals(R.color.go, status.color);
		assertEquals(R.string.explanation_go_partly_cloudy, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelRain() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.RAIN);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_rainy, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelRainShowers() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.RAIN_SHOWERS);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_rainy, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelSleet() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.SLEET);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_snow, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testHighLevelSnow() {
		double currentLevel = DEFAULT_MIN_LEVEL + 1;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.SNOW);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_stay, status.text);
		assertEquals(R.color.stay, status.color);
		assertEquals(R.string.explanation_stay_snow, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testNoLevelUnknownWeather() {
		double currentLevel = LevelRetriever.NO_LEVEL;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.UNKNOWN);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_confused, status.text);
		assertEquals(R.color.neutral, status.color);
		assertEquals(R.string.explanation_confused, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testNoLevelGoodWeather() {
		double currentLevel = LevelRetriever.NO_LEVEL;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.FAIR);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_confused, status.text);
		assertEquals(R.color.neutral, status.color);
		assertEquals(R.string.explanation_confused, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testNoLevelBadWeather() {
		double currentLevel = LevelRetriever.NO_LEVEL;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.CLOUDY);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_confused, status.text);
		assertEquals(R.color.neutral, status.color);
		assertEquals(R.string.explanation_confused, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testOutOfBoundsLowLevel() {
		double currentLevel = -2.0;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, DEFAULT_WEATHER);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_confused, status.text);
		assertEquals(R.color.neutral, status.color);
		assertEquals(R.string.explanation_confused, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testOutOfBoundsHighLevel() {
		double currentLevel = 10;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, DEFAULT_WEATHER);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_confused, status.text);
		assertEquals(R.color.neutral, status.color);
		assertEquals(R.string.explanation_confused, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testOutOfBoundsNaNLevel() {
		double currentLevel = Double.NaN;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, DEFAULT_WEATHER);
		AuroraStatus status = retriever.retrieve(DEFAULT_MIN_LEVEL);
		
		assertEquals(R.string.output_confused, status.text);
		assertEquals(R.color.neutral, status.color);
		assertEquals(R.string.explanation_confused, status.explanation);
		assertEquals(currentLevel, status.level);
	}
	
	public void testLevelLimit() {
		double currentLevel = 2;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, Weather.FAIR);
		AuroraStatus lowStatus = retriever.retrieve(3);
		AuroraStatus equalStatus = retriever.retrieve(2);
		
		assertEquals(R.string.output_stay, lowStatus.text);
		assertEquals(R.color.stay, lowStatus.color);
		assertEquals(R.string.explanation_stay_level, lowStatus.explanation);
		assertEquals(currentLevel, lowStatus.level);
		
		assertEquals(R.string.output_go, equalStatus.text);
		assertEquals(R.color.go, equalStatus.color);
		assertEquals(R.string.explanation_go_fair, equalStatus.explanation);
		assertEquals(currentLevel, equalStatus.level);
	}
	
	public void testOutOfBoundsMinLevelLow() {
		double currentLevel = DEFAULT_LEVEL;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, DEFAULT_WEATHER);
		
		try {
			retriever.retrieve(-1);
			fail("Missing IllegalArgumentException.");
		} catch(IllegalArgumentException e) {
			assertEquals("Invalid minimum level", e.getMessage());
		}
	}
	
	public void testOutOfBoundsMinLevelHigh() {
		double currentLevel = DEFAULT_LEVEL;
		AuroraStatusRetriever retriever = getTestRetriever(currentLevel, DEFAULT_WEATHER);
		
		try {
			retriever.retrieve(10);
			fail("Missing IllegalArgumentException.");
		} catch(IllegalArgumentException e) {
			assertEquals("Invalid minimum level", e.getMessage());
		}
	}
	
	private AuroraStatusRetriever getTestRetriever(double currentLevel, Weather weather) {
		final double finalCurrentLevel = currentLevel;
		final Weather finalWeather = weather;
		return new AuroraStatusRetriever(new DataRetriever() {
			@Override
			public DataRetrievalResult retrieve() {
				return new DataRetrievalResult(finalCurrentLevel, finalWeather);
			}
		});
	}
}
