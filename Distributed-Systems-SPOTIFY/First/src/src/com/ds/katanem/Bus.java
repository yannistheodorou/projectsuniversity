package com.ds.katanem;
import java.io.Serializable;

public class Bus implements Serializable {
	public String lineCode;
	public String routeCode;
	public String vehicleId;
	public String lineName;
	public String buslineId;
	
	@Override
	public String toString() {
		return "LineCode: " + lineCode + " RouteCode: " + routeCode + " VehicleId: " + vehicleId + " LineName: " + lineName + " LineId: " + buslineId; 
	}
}
