package com.osu.cs496.mybusstop;

import java.lang.String;

public class BusInfo{
	public String route;
	public String arrival;
	public String wait;
	
	public BusInfo(String route, String arrival, String wait){
		this.route = route;
		this.arrival = arrival;
		this.wait = wait;
	}
}