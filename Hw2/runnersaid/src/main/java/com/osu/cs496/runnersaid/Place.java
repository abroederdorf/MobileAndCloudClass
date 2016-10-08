package com.osu.cs496.runnersaid;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.lang.String;
import java.util.Date;
import java.util.List;

@Entity
public class Place{
	@Id public Long id;
	
	public String creatorId;
	public String creatorEmail;
	public Date createDate;
	public Date statusDate;
	
	public String type;
	public String name;
	public double latitude;
	public double longitude;
	public String status;
	public boolean favorite;
	
	//Simple constructor to set the dates
	public Place(){
		createDate = new Date();
		statusDate = new Date();
	}
	
	//Constructor for all required fields
	public Place(String type, String name, double latitude, double longitude, String status, boolean favorite){
		this(); //Calls simple constructor to set dates
		
		this.type = type;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;
		this.favorite = favorite;
	}
	
	//Constructor for all fields if user is signed in
	public Place(String type, String name, double latitude, double longitude, String status, boolean favorite, String creatorId, String creatorEmail){
		//Call req'd fields constructor
		this(type, name, latitude, longitude, status, favorite); 
		
		this.creatorId = creatorId;
		this.creatorEmail = creatorEmail;
	}
	
	//Setter methods
	public void setType(String type){
		this.type = type;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public void setFavorite(boolean fav){
		this.favorite = fav;
	}
	
	public void setStatusDate(Date date){
		this.statusDate = date;
	}
}