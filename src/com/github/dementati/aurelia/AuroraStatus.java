package com.github.dementati.aurelia;

import android.os.Parcel;
import android.os.Parcelable;

public class AuroraStatus implements Parcelable {
	public int text;
	public int color;
	public int explanation;
	public double level;
	public boolean notify;

	public AuroraStatus() {}
	
	public AuroraStatus(Parcel in) {
		assert in != null : "Can't read data from null parcel";
		
		text = in.readInt();
		color = in.readInt();
		explanation = in.readInt();
		level = in.readDouble();
		notify = in.readByte() != 0;
	}

	@Override
	public String toString() {
		return "AuroraStatus [text=" + text + ", color=" + color
				+ ", explanation=" + explanation + ", level=" + level
				+ ", notify=" + notify + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + explanation;
		long temp;
		temp = Double.doubleToLongBits(level);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (notify ? 1231 : 1237);
		result = prime * result + text;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuroraStatus other = (AuroraStatus) obj;
		if (color != other.color)
			return false;
		if (explanation != other.explanation)
			return false;
		if (Double.doubleToLongBits(level) != Double
				.doubleToLongBits(other.level))
			return false;
		if (notify != other.notify)
			return false;
		if (text != other.text)
			return false;
		return true;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		assert out != null : "Can't write data to null parcel";
		
		out.writeInt(text);
		out.writeInt(color);
		out.writeInt(explanation);
		out.writeDouble(level);
		out.writeByte((byte)(notify ? 1 : 0));
	}
	
	
	
	public static final Parcelable.Creator<AuroraStatus> CREATOR = 
		new Parcelable.Creator<AuroraStatus>() {
		    public AuroraStatus createFromParcel(Parcel in) {
				return new AuroraStatus(in);
		    }

		    public AuroraStatus[] newArray(int size) {
				return new AuroraStatus[size];
		    }
		};
}