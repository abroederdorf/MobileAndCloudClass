package com.osu.cs496.runnersaidapi;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.System.out;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Key;


public class PlacesServlet extends HttpServlet {

  // Process the http GET Request
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	final HttpServletResponse response = resp;
    final PrintWriter respMsg = resp.getWriter();
	
	//Get parameters
	String idStr = req.getParameter("id");
	long id = -1;
	if (idStr != "" && idStr != null)
		id = Long.parseLong(idStr);
	
	//Get specified place by id
	if (id != -1){
		//Get place
		Place thisPlace = ObjectifyService.ofy().load().type(Place.class).id(id).now();
		
		if (thisPlace != null){
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			String dataJSON = new Gson().toJson(thisPlace);
			respMsg.println(dataJSON);
			respMsg.flush();
			respMsg.close();
		}
		else{
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("Place id not valid");
			respMsg.flush();
			respMsg.close();
		}
	}
	//No id provided, return entire list
	else{
		List<Place> places = ObjectifyService.ofy().load().type(Place.class).list();
		
		if (places.isEmpty()){
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			respMsg.println("No places returned");
			respMsg.flush();
			respMsg.close();
		}
		else{
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			String dataJSON = new Gson().toJson(places);
			respMsg.println(dataJSON);
			respMsg.flush();
			respMsg.close();
		}
	}	
  }
  
  // Process the http POST Request
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
	final HttpServletResponse response = resp;
    final PrintWriter respMsg = resp.getWriter();
	
	//Get parameters from request
	String type = req.getParameter("type");
	String name = req.getParameter("name");
	String longStr = req.getParameter("longitude");
	String latStr = req.getParameter("latitude");
	String status = req.getParameter("status");
	String userStr = req.getParameter("userId");
		
	//Check that all parameters are provided
	boolean allProvided = true;
	if (type == "" || type == null || name == "" || name == null ||
		latStr == "" || latStr == null || longStr == "" ||
		longStr == null || status == "" || status == null ||
		userStr == "" || userStr == null){
			allProvided = false;
		}
	
	if(allProvided){
		double longitude = Double.parseDouble(longStr);
		double latitude = Double.parseDouble(latStr);
		long userId = Long.parseLong(userStr);
		
		//Check that user id is valid
		List<UserRA> users = ObjectifyService.ofy().load().type(UserRA.class).list();
		boolean valid = false;
		if (!users.isEmpty()){
			for (UserRA thisUser : users){
				if (thisUser.id == userId)
					valid = true;
			}
		}
		
		//Check that place does not already exist
		boolean exists = false;
		List<Place> places = ObjectifyService.ofy().load().type(Place.class).list();
		if (!places.isEmpty()){
			for (Place thisPlace : places){
				if (thisPlace.type.equals(type) &&
					(thisPlace.name.equals(name)) &&
					(thisPlace.latitude == latitude) &&
					(thisPlace.longitude == longitude) &&
					(thisPlace.createdUserId == userId)){
						exists = true;
				}
			}
		}
		
		//Create and add place
		if (!exists && valid){
			Place newPlace = new Place(type, name, latitude, longitude, status, userId);
			if (newPlace != null)
				ObjectifyService.ofy().save().entity(newPlace).now();
			
			
			if (newPlace.id != null){
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				String dataJSON = new Gson().toJson(newPlace);
				respMsg.println(dataJSON);
				respMsg.flush();
				respMsg.close();
			}
			else{
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				respMsg.println("Add was not successful");
				respMsg.flush();
				respMsg.close();
			}
		}
		else if (!valid){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("User id not valid");
			respMsg.flush();
			respMsg.close();
		}
		else{
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("Place already exists");
			respMsg.flush();
			respMsg.close();
		}
	}
	else{
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		respMsg.println("Parameters not all defined");
		respMsg.flush();
		respMsg.close();
	}
  }
  
  // Process the http PUT Request
  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
	
	final HttpServletResponse response = resp;
    final PrintWriter respMsg = resp.getWriter();
	
	//Get parameters from request
	String type = req.getParameter("type");
    String name = req.getParameter("name");
	String status = req.getParameter("status");
	
	String vote = req.getParameter("vote");
	
	String longStr = req.getParameter("longitude");
	double longitude = -1.0; //In ocean
	if (longStr != null && longStr != "")
		longitude = Double.parseDouble(longStr);
	
	String latStr = req.getParameter("latitude");
	double latitude = -1.0; //In ocean
	if (latStr != "" && latStr != null)
		latitude = Double.parseDouble(latStr);
	
	String userIdStr = req.getParameter("userId");
	long userId = -1; 
	if (userIdStr != "" && userIdStr != null)
		userId = Long.parseLong(userIdStr);
		
	String idStr = req.getParameter("id");
	long id = -1;
	if (idStr != "" && idStr != null)
		id = Long.parseLong(idStr);
	
	//Check that place does not already exist
	boolean exists = false;
	List<Place> placesTmp = ObjectifyService.ofy().load().type(Place.class).list();
	if (!placesTmp.isEmpty()){
		for (Place thisPlace : placesTmp){
			if (thisPlace.type.equals(type) &&
				(thisPlace.name.equals(name)) &&
				(thisPlace.latitude == latitude) &&
				(thisPlace.longitude == longitude) &&
				(thisPlace.createdUserId == userId) &&
				(thisPlace.id != id)){
					exists = true;
			}
		}
	}
	
	//Check that user id is valid
	List<UserRA> users = ObjectifyService.ofy().load().type(UserRA.class).list();
	boolean valid = false;
	if (!users.isEmpty()){
		for (UserRA thisUser : users){
			if (thisUser.id == userId)
				valid = true;
		}
	}
	
	//If entity does not exist, an id is provided, and a valid user id is used - update. 
	if (!exists && (id != -1) && valid){
		//Get entity
		Place thisPlace = ObjectifyService.ofy().load().type(Place.class).id(id).now();

		//Update with parameters
		if (name != null && name != "")
			thisPlace.setName(name);
		if (type != null && type != "")
			thisPlace.setType(type);
		if (latitude != -1)
			thisPlace.setLatitude(latitude);
		if (longitude != -1)
			thisPlace.setLongitude(longitude);
		if (vote != null && vote != ""){
			String voteUpr = vote.toUpperCase();
			if (voteUpr.equals("UP"))
				thisPlace.incVote(1);
			else if (voteUpr.equals("DOWN"))
				thisPlace.incVote(-1);
		}	
		if (status != null && status != ""){
			if (!thisPlace.status.equals(status))
			{
				thisPlace.setStatusDate(new Date());
				thisPlace.setVote(1);
			}
			thisPlace.setStatus(status);
		}	
		if (userId != -1)
			thisPlace.setCreatedUserId(userId);

		//Update and respond
		Place checkPlace = new Place(thisPlace.type, thisPlace.name, thisPlace.latitude, thisPlace.longitude, thisPlace.status, thisPlace.createdUserId, thisPlace.vote);
		ObjectifyService.ofy().save().entity(thisPlace).now(); 
		
		if (checkPlace.name == thisPlace.name &&
			checkPlace.type == thisPlace.type &&
			checkPlace.status == thisPlace.status &&
			checkPlace.latitude == thisPlace.latitude &&
			checkPlace.longitude == thisPlace.longitude &&
			checkPlace.createdUserId == thisPlace.createdUserId &&
			checkPlace.vote == thisPlace.vote){
				
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			String dataJSON = new Gson().toJson(thisPlace);
			respMsg.println(dataJSON);
			respMsg.flush();
			respMsg.close();
		}
		else{
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			respMsg.println("Update was not successful");
			respMsg.flush();
			respMsg.close();
		}
		
	}
	else if (id == -1){
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		respMsg.println("No id of place provided");
		respMsg.flush();
		respMsg.close();
	}
	else if (!valid){
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		respMsg.println("User id not valid");
		respMsg.flush();
		respMsg.close();
	}
	else{
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		respMsg.println("Place already exists");
		respMsg.flush();
		respMsg.close();
	}
	
  }

	// Process the http DELETE Request
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		final HttpServletResponse response = resp;
		final PrintWriter respMsg = resp.getWriter();
		
		//Get parameter
		String idStr = req.getParameter("id");
		long id = -1;
		if (idStr != "" && idStr != null)
			id = Long.parseLong(idStr);
		
		//Determine if id exists
		Place thisPlace = ObjectifyService.ofy().load().type(Place.class).id(id).now();
		boolean exists = (thisPlace != null) ? true : false;
		
		//Id not provided
		if (id == -1){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("No id provided");
			respMsg.flush();
			respMsg.close();
		}
		//Delete
		else if (exists){
			ObjectifyService.ofy().delete().type(Place.class).id(id).now();
			
			//Check that place is deleted
			Place placeTmp = ObjectifyService.ofy().load().type(Place.class).id(id).now();
			exists = (placeTmp != null) ? true : false;
			
			//Delete id from users' favorite list
			List<UserRA> users = ObjectifyService.ofy().load().type(UserRA.class).list();
			for (UserRA thisUser : users){
				if (thisUser.favorite.contains(id)){
					thisUser.favorite.remove(id);
					ObjectifyService.ofy().save().entity(thisUser).now(); 
				}
			}
			
			if (exists){
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				respMsg.println("Delete unsuccessful");
				respMsg.flush();
				respMsg.close();
			}
			else{
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				respMsg.println("Place successfully deleted");
				respMsg.flush();
				respMsg.close();
			}
		}
		//Id not valid
		else{
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("Id not valid");
			respMsg.flush();
			respMsg.close();
		}
	}
}