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
	@Index public String username;
	public String password;
	public List<Long> favorite;
	
	//Simple constructor to set the dates and initial vote
	public UserRA(){
	}
	
	//Constructor for all required fields
	public UserRA(String username, String password){
		this.username = username;
		this.password = password;
		this.favorite = new ArrayList<Long>();
		this.favorite.add(-100L);
	}
	
	//Constructor for checking favorites with list
	public UserRA(String username, String password, List<Long> favs){
		this(username, password);
		this.favorite.clear();
		this.setFavorite(favs);
	}
	
	//Setter & Getter methods
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
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