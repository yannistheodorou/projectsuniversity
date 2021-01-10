package com.ds.katanem;

import java.io.Serializable;

public class Value implements Serializable {
	public Bus bus;
	public double latitude;
	public double longtitude;
	public String timestamp;
	
	@Override
	public String toString() {
		return "Bus: " + bus.lineName + " latitude: " + latitude + " longtitude: " + longtitude + " timestamp: " + timestamp; 
	}
}
