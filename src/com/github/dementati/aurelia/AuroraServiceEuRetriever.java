package com.github.dementati.aurelia;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AuroraServiceEuRetriever implements AuroraLevelRetriever {	
	@Override
	public double retrieveLevel() {
		String url = "http://www.aurora-service.eu/aurora-forecast";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements spans = doc.select("#hourly>h4>span");
			if(spans.size() == 0) {
				return -1.0;
			} else {
				String raw = spans.get(0).text();
				raw = raw.replace("Kp ", "");
				return Double.parseDouble(raw);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1.0;
	}
}
