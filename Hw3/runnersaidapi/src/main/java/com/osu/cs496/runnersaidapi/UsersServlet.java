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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.System.out;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Key;

public class UsersServlet extends HttpServlet {

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
	
	String fieldStr = req.getParameter("fields");
	String fields = "dne";
	if (fieldStr != null && fieldStr != "")
		fields = fieldStr.toLowerCase();
	
	String gidStr = req.getParameter("gid");
	
	//Get specified user by id but not favorites list
	if (id != -1 && fields.equals("dne")){
		//Get user
		UserRA thisUser = ObjectifyService.ofy().load().type(UserRA.class).id(id).now();
		
		if (thisUser != null){
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			String dataJSON = new Gson().toJson(thisUser);
			respMsg.println(dataJSON);
			respMsg.flush();
			respMsg.close();
		}
		else{
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("User id not valid");
			respMsg.flush();
			respMsg.close();
		}
	}
	//Return favorites list of user
	else if (id != 1 && fields.equals("favorite")){
		//Get user
		UserRA thisUser = ObjectifyService.ofy().load().type(UserRA.class).id(id).now();
		
		if (thisUser != null){
			List<Long> favs = thisUser.getFavorite();
			if (favs.indexOf(-100L) != -1 && favs.indexOf(-100L) < favs.size())
				favs.remove(favs.indexOf(-100L));
			
			List<Place> favPlaces = new ArrayList<Place>();
			for (long placeId : favs){
				favPlaces.add(ObjectifyService.ofy().load().type(Place.class).id(placeId).now());
			}
			
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			String dataJSON = new Gson().toJson(favPlaces);
			respMsg.println(dataJSON);
			respMsg.flush();
			respMsg.close();
		}
		else{
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("User id not valid");
			respMsg.flush();
			respMsg.close();
		}
	}
	//If id but incorrect field, return error
	else if (id != -1 && !fields.equals("favorite")){
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		respMsg.println("Incorrect url, only valid field parameter is favorite");
		respMsg.flush();
		respMsg.close();
	}
	//Return user id from provided google id
	else if (gidStr != "" && gidStr != null){
		long gusers = -1;
		List<UserRA> gUser = ObjectifyService.ofy().load().type(UserRA.class).filter("userId",gidStr).list();
		gusers = gUser.get(0).id;
		
		if (gusers != -1){
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			String dataJSON = new Gson().toJson(gusers);
			respMsg.println(dataJSON);
			respMsg.flush();
			respMsg.close();
		}
		else{
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			respMsg.println("No users returned for that Google id");
			respMsg.flush();
			respMsg.close();
		}
	}
	//No id provided, return entire list
	else{
		List<UserRA> users = ObjectifyService.ofy().load().type(UserRA.class).list();
		
		if (users.isEmpty()){
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			respMsg.println("No users returned");
			respMsg.flush();
			respMsg.close();
		}
		else{
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			String dataJSON = new Gson().toJson(users);
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
	String email = req.getParameter("email");
	String userId = req.getParameter("userId");
		
	//Check that all parameters are provided
	boolean allProvided = true;
	if (email == "" || email == null || userId == "" || userId == null){
			allProvided = false;
		}
	
	if(allProvided){
		
		//Check that user does not already exist
		boolean exists = false;
		List<UserRA> users = ObjectifyService.ofy().load().type(UserRA.class).list();
		if (!users.isEmpty()){
			for (UserRA thisUser : users){
				if (thisUser.email.equals(email) &&
					thisUser.userId.equals(userId)){
						exists = true;
				}
			}
		}
		
		//Create and add user
		if (!exists){
			UserRA newUser = new UserRA(userId, email);
			if (newUser != null)
				ObjectifyService.ofy().save().entity(newUser).now();
			
			
			if (newUser.id != null){
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				String dataJSON = new Gson().toJson(newUser);
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
		else{
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("User already exists");
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
	String email = req.getParameter("email");
	String userId = req.getParameter("userId");
		
	String idStr = req.getParameter("id");
	long id = -1;
	if (idStr != "" && idStr != null)
		id = Long.parseLong(idStr);
	
	String favStr = req.getParameter("favorite");
	long fav = -1;
	if (favStr != "" && favStr != null){
		fav = Long.parseLong(favStr);
	}
	
	//Check that updated place does not already exist
	boolean exists = false;
	List<UserRA> usersTmp = ObjectifyService.ofy().load().type(UserRA.class).list();
	if (!usersTmp.isEmpty()){
		for (UserRA thisUser : usersTmp){
			if (thisUser.email.equals(email) &&
				(thisUser.userId.equals(userId))&&
				(thisUser.id != id)){
					exists = true;
			}
		}
	}
	
	//If entity does not exist, has a valid id, and isn't adding favorites, update.
	if (!exists && (id != -1) && (fav == -1)){
		//Get entity
		UserRA thisUser = ObjectifyService.ofy().load().type(UserRA.class).id(id).now();

		//Update with parameters
		if (email != null && email != "")
			thisUser.setEmail(email);	
		if (userId != null && userId != "")
			thisUser.setUserId(userId);

		//Update and respond
		UserRA checkUser = new UserRA(thisUser.userId, thisUser.email);
		ObjectifyService.ofy().save().entity(thisUser).now(); 
		
		if (checkUser.userId == thisUser.userId &&
			checkUser.email == thisUser.email){
				
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			String dataJSON = new Gson().toJson(thisUser);
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
	//Add favorite
	else if ((id != -1) && (fav != -1)){
		
		//Get user and place
		UserRA thisUser = ObjectifyService.ofy().load().type(UserRA.class).id(id).now();
		Place thisPlace = ObjectifyService.ofy().load().type(Place.class).id(fav).now();
		if (thisPlace == null){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("Place id not valid");
			respMsg.flush();
			respMsg.close();
		}
		else if (thisUser == null){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("User id not valid");
			respMsg.flush();
			respMsg.close();
		}
		else if (thisUser.favorite.contains(fav)){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("Place already included in user's favorites");
			respMsg.flush();
			respMsg.close();
		}
		else{
			thisUser.setFavorite(fav);
			UserRA checkUser = new UserRA(thisUser.userId, thisUser.email, thisUser.favorite);
			ObjectifyService.ofy().save().entity(thisUser).now(); 

			
			if (checkUser.userId.equals(thisUser.userId) &&
				checkUser.email.equals(thisUser.email) && 
				thisUser.favorite.contains(fav)){
				
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				String dataJSON = new Gson().toJson(thisUser);
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
		
	}
	else if (id == -1){
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		respMsg.println("No id of user provided");
		respMsg.flush();
		respMsg.close();
	}
	else{
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		respMsg.println("User already exists");
		respMsg.flush();
		respMsg.close();
	}
	
  }

	// Process the http DELETE Request
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		final HttpServletResponse response = resp;
		final PrintWriter respMsg = resp.getWriter();
		
		//Get parameters
		long userId = -1, placeId = -1;
		boolean urlCorrect = true;
		
		//Parse url
		StringBuffer url = req.getRequestURL();
		String urlStr = url.toString();
		String[] urlSplit = urlStr.split("/users/");
		if (urlSplit.length == 2){
			String[] idSplit = urlSplit[1].split("/");
			if (idSplit.length == 2){
				userId = Long.parseLong(idSplit[0]);
				urlCorrect = idSplit[1].equals("place") ? true : false;
			}
			else
				urlCorrect = false;
		}
		else
			urlCorrect = false;
		
		//Get place id from query string
		String query = req.getQueryString();
		if (query != null){
			String[] params = query.split("&");
			for (String param : params){
				String[] keyVal = param.split("=");
				if (keyVal[0].equals("id"))
					placeId = Long.parseLong(keyVal[1]);
			}
		}
		
		//Ids not provided
		if (userId == -1){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("No id provided");
			respMsg.flush();
			respMsg.close();
		}
		else if (!urlCorrect){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("Bad request, url should be /users/id/place?id=");
			respMsg.flush();
			respMsg.close();
		}
		else if (placeId == -1){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respMsg.println("Place id not provided");
			respMsg.flush();
			respMsg.close();
		}
		else{
			//Determine if ids exists
			UserRA thisUser = ObjectifyService.ofy().load().type(UserRA.class).id(userId).now();
			boolean userExists = (thisUser != null) ? true : false;
			
			if (userExists){
				boolean placeExists = thisUser.favorite.contains(placeId) ? true : false;
				
				if (placeExists){
					thisUser.favorite.remove(placeId);
					ObjectifyService.ofy().save().entity(thisUser).now(); 
					placeExists = thisUser.favorite.contains(placeId) ? true : false;
					
					if (placeExists){
						resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						respMsg.println("Delete unsuccessful");
						respMsg.flush();
						respMsg.close();
					}
					else{
						resp.setStatus(HttpServletResponse.SC_OK);
						resp.setContentType("application/json");
						respMsg.println("Place successfully deleted from user's favorites");
						respMsg.flush();
						respMsg.close();
					}
				}
				else{
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					respMsg.println("Place is not part of user's favorites");
					respMsg.flush();
					respMsg.close();
				}
			}
			//Id not valid
			else{
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				respMsg.println("User id not valid");
				respMsg.flush();
				respMsg.close();
			}
		}
	}
}