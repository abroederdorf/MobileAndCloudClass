package com.osu.cs496.runnersaidapi;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
public class UserRA{
	@Id public Long id;
	@Index public String userId;
	public String email;
	public List<Long> favorite;
	
	//Simple constructor to set the dates and initial vote
	public UserRA(){
	}
	
	//Constructor for all required fields
	public UserRA(String userId, String email){
		this.userId = userId;
		this.email = email;
		this.favorite = new ArrayList<Long>();
		this.favorite.add(-100L);
	}
	
	//Constructor for checking favorites with list
	public UserRA(String userId, String email, List<Long> favs){
		this(userId, email);
		this.favorite.clear();
		this.setFavorite(favs);
	}
	
	//Setter & Getter methods
	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public List<Long> getFavorite(){
		return favorite;
	}
	
	public void setFavorite(long id){
		this.favorite.add(id);
	}
	
	public void setFavorite(List<Long> ids){
		this.favorite.addAll(ids);
	}
	
}