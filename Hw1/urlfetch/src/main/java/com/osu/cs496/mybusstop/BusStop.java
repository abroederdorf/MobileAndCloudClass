package com.osu.cs496.mybusstop;

import java.lang.String;
import java.util.ArrayList;
import com.osu.cs496.mybusstop.BusInfo;

public class BusStop{
	public ArrayList<BusInfo> buses;
	public String name;
	public String id;
	public String direction;
	
	public BusStop(ArrayList<BusInfo> buses, String name, String id, String direction){
		this.buses = new ArrayList<BusInfo>(buses); 
		this.name = name;
		this.id = id;
		this.direction = direction;
	}
}