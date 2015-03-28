package com.github.dementati.aurelia.test;

import android.os.Parcel;

import com.github.dementati.aurelia.AuroraStatus;

import junit.framework.TestCase;

public class AuroraStatusTest extends TestCase {
	public void testParceling() {
		int color = 1;
		int text = 2;
		int explanation = 3;
		double level = 4.0;
		boolean notify = true;
		
		AuroraStatus writeStatus = new AuroraStatus();
		writeStatus.color = color;
		writeStatus.text = text;
		writeStatus.explanation = explanation;
		writeStatus.level = level;
		writeStatus.notify = notify;
		
		Parcel parcel = Parcel.obtain();
		writeStatus.writeToParcel(parcel, 0);
		
		parcel.setDataPosition(0);
		
		AuroraStatus readStatus = AuroraStatus.CREATOR.createFromParcel(parcel);
		assertEquals(writeStatus, readStatus);
	}
}
