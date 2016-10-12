package com.osu.cs496.mybusstop;

import java.lang.String;
import java.util.ArrayList;
import com.osu.cs496.mybusstop.BusInfo;

public class BusStop{
	public ArrayList<BusInfo> buses;
	public String name;
	
	public BusStop(ArrayList<BusInfo> buses, String name){
		this.buses = new ArrayList<BusInfo>(buses); 
		this.name = name;
	}
}