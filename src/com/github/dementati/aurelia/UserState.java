package com.github.dementati.aurelia;

import java.util.Calendar;

public class UserState {
	private String region = "Europe";
	private GeophysInstRetriever retriever = new GeophysInstRetriever();
	
	public int retrieveLevel() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		return retriever.retrieveLevel(region, year, month, day);
	}
}
