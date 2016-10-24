package com.osu.cs496.runnersaidapi;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import static java.lang.Math.*;
import java.util.Date;
import java.util.List;

@Entity
public class Place{
	@Id public Long id;
	@Index public String type;
	public String name;
	@Index public Date createdDate;
	public long createdUserId;
	@Index public double latitude;
	@Index public double longitude;
	@Index public String status;
	public Date statusDate;
	public int vote;
	
	//Simple constructor to set the dates and initial vote
	public Place(){
		createdDate = new Date();
		statusDate = new Date();
		this.vote = 1;
	}
	
	//Constructor for all required fields
	public Place(String type, String name, double latitude, double longitude, String status){
		this(); //Calls simple constructor to set dates
		
		this.type = type;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;
	}
	
	//Constructor for all fields if user is signed in
	public Place(String type, String name, double latitude, double longitude, String status, long createdUserId){
		//Call req'd fields constructor
		this(type, name, latitude, longitude, status); 
		
		this.createdUserId = createdUserId;
	}
	
	//Check object with vote
	public Place(String type, String name, double latitude, double longitude, String status, long createdUserId, int vote){
		//Call req'd fields constructor
		this(type, name, latitude, longitude, status, createdUserId); 
		
		this.vote = vote;
	}
	
	//Setter & Getter methods
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public Date getStatusDate(){
		return statusDate;
	}
	
	public void setStatusDate(Date date){
		this.statusDate = date;
	}
	
	public int getVote(){
		return vote;
	}
	
	public void setVote(int num){
		this.vote = num;
	}
	
	public void incVote(int num){
		this.vote += num;
	}
	
	public long getCreatedUserId(){
		return createdUserId;
	}
	
	public void setCreatedUserId(long userId){
		this.createdUserId = userId;
	}
	
	public Date getCreatedDate(){
		return createdDate;
	}
	
	public double getDistance(double latitude, double longitude){
		//http://www.movable-type.co.uk/scripts/latlong.html
		//Haversine formula
		double thisLat = toRadians(this.latitude);
		double thatLat = toRadians(latitude);
		double latDiff = toRadians(latitude - this.latitude);
		double longDiff = toRadians(longitude - this.longitude);
		
		double a = sin(latDiff/2.0) * sin(latDiff/2.0) + cos(thisLat) * cos(thatLat) * sin(longDiff/2.0) * sin(longDiff/2.0);
		double c = 2.0 * atan2(sqrt(a), sqrt(1 - a));
		return 3958.756 * c; //miles
	}
}