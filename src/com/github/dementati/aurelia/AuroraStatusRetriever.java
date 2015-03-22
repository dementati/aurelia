package com.github.dementati.aurelia;


public class AuroraStatusRetriever {
	private DataRetriever dataRetriever;
	
	public AuroraStatusRetriever() {
		this.dataRetriever = new DataRetrieverImpl();
	}

	public AuroraStatusRetriever(DataRetriever dataRetriever) {
		this.dataRetriever = dataRetriever;
	}
	
	public AuroraStatus retrieve(int minLevel) {
		if(minLevel < 0 || minLevel > 9) {
			throw new IllegalArgumentException("Invalid minimum level");
		}
		
		DataRetrievalResult data = dataRetriever.retrieve();
		
		AuroraStatus result = new AuroraStatus();
		
		if(data.currentLevel < 0 || data.currentLevel > 9 || Double.isNaN(data.currentLevel)) {
			result.text = R.string.output_confused;
			result.color = R.color.neutral;
			result.explanation = R.string.explanation_confused;
			result.level = data.currentLevel;
		}
		else if(data.currentLevel >= minLevel) {
		    switch(data.weather) {
			    case FAIR: 
					result.text = R.string.output_go;
					result.color = R.color.go;
					result.explanation = R.string.explanation_go_fair;
					result.level = data.currentLevel;
				    break;
				    
			    case PARTLY_CLOUDY:
					result.text = R.string.output_go;
					result.color = R.color.go;
					result.explanation = R.string.explanation_go_partly_cloudy;
					result.level = data.currentLevel;
				    break;
				    
			    case HEAVY_RAIN:
			    case RAIN_SHOWERS:
			    case RAIN:
					result.text = R.string.output_stay;
					result.color = R.color.stay;
					result.explanation = R.string.explanation_stay_rainy;
					result.level = data.currentLevel;
				    break;
			    
			    case SLEET:
			    case SNOW:
					result.text = R.string.output_stay;
					result.color = R.color.stay;
					result.explanation = R.string.explanation_stay_snow;
					result.level = data.currentLevel;
				    break;
				    
			    case CLOUDY:
					result.text = R.string.output_stay;
					result.color = R.color.stay;
					result.explanation = R.string.explanation_stay_cloudy;
					result.level = data.currentLevel;
				    break;
				    
			    default:
					result.text = R.string.output_confused;
					result.color = R.color.neutral;
					result.explanation = R.string.explanation_confused;
					result.level = data.currentLevel;
				    break;
		    }
        } else {
			result.text = R.string.output_stay;
			result.color = R.color.stay;
			result.explanation = R.string.explanation_stay_level;
			result.level = data.currentLevel;
	    }
		
		return result;
	}
	
	public class AuroraStatus {
		public int text;
		public int color;
		public int explanation;
		public double level;
	}
}
