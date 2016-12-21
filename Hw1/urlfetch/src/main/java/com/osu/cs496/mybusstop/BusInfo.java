package com.osu.cs496.mybusstop;

import java.lang.String;

public class BusInfo{
	public String route;
	public String arrival;
	public String wait;
	public String scheduled; //~~~~~~~~new
	
	public BusInfo(String route, String arrival, String wait, String scheduled){
		this.route = route;
		this.arrival = arrival;
		this.wait = wait;
		this.scheduled = scheduled; //~~~~~~~~new
	}
}