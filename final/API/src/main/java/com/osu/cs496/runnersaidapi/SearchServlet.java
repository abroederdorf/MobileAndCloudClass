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


public class SearchServlet extends HttpServlet {

  // Process the http GET Request
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	final HttpServletResponse response = resp;
    final PrintWriter respMsg = resp.getWriter();
	
	//Get parameters
	boolean booStatus = false, booType = false, booLocation = false, booUser = false; 
	String status = req.getParameter("status");
	String type = req.getParameter("type");
	String radStr = req.getParameter("radius");
	String latStr = req.getParameter("latitude");
	String longStr = req.getParameter("longitude");
	
	String userIdStr = req.getParameter("user"); 
	long userId = -1;
	if (userIdStr != "" && userIdStr != null)
		userId = Long.parseLong(userIdStr);
	
	//Get list for parameter provided
	List<Place> statusPl = new ArrayList<Place>();
	if (status != null && status != ""){
		booStatus = true;
		statusPl.addAll(ObjectifyService.ofy().load().type(Place.class).filter("status",status).list());
	}
	List<Place> typePl = new ArrayList<Place>();
	if (type != null && type != ""){
		booType = true;
		typePl.addAll(ObjectifyService.ofy().load().type(Place.class).filter("type",type).list());
	}	
	List<Place> userPl = new ArrayList<Place>();
	if (userId != -1){
		booUser = true;
		UserRA user = ObjectifyService.ofy().load().type(UserRA.class).id(userId).now();
		List<Long> favs = user.getFavorite();
		if (favs.indexOf(-100L) != -1 && favs.indexOf(-100L) < favs.size())
			favs.remove(favs.indexOf(-100L));
		if (user != null){
			for (int i = 0; i < favs.size(); i++){
				if (favs.get(i) != -100)
					userPl.add(ObjectifyService.ofy().load().type(Place.class).id(favs.get(i)).now());
			}
		}	
	}
	if (radStr != null && radStr != "" &&
		latStr != null && latStr != "" &&
		longStr != null && longStr != "")
			booLocation = true;

	List<Place> filtPlace = new ArrayList<Place>();
	//Get list of common elements between parameters
	if (booType && !booStatus && !booUser){
		filtPlace.addAll(typePl);
	}
	else if (!booType && booStatus && !booUser){
		filtPlace.addAll(statusPl);
	}
	else if (!booType && !booStatus && booUser){
		filtPlace.addAll(userPl);
	}
	else if (booType && booStatus && !booUser){
		for (Place place : typePl){
			if (statusPl.contains(place))
				filtPlace.add(place);
		}
	}
	else if (booType && !booStatus && booUser){
		for (Place place : typePl){
			System.out.println("Place: " );
			System.out.println(place);
			System.out.println(userPl);
			if (userPl.contains(place))
				filtPlace.add(place);
		}
	}
	else if (!booType && booStatus && booUser){
		for (Place place : userPl){
			if (statusPl.contains(place))
				filtPlace.add(place);
		}
	}
	else if (booType && booStatus && booUser){
		for (Place place : typePl){
			if (statusPl.contains(place) && userPl.contains(place))
				filtPlace.add(place);
		}
	}
	else{
		filtPlace.addAll(ObjectifyService.ofy().load().type(Place.class).list());
	}
	
	//Filter based on location
	List<Place> listPlaces = new ArrayList<Place>(filtPlace);
	if (booLocation){
		double radius = Double.parseDouble(radStr);
		double latitude = Double.parseDouble(latStr);
		double longitude = Double.parseDouble(longStr);
		for (int i = 0; i < filtPlace.size(); i++){
			if (filtPlace.get(i).getDistance(latitude, longitude) > radius)
				listPlaces.remove(filtPlace.get(i));
		}
	}

	//Return response
	if (!listPlaces.isEmpty()){
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		String dataJSON = new Gson().toJson(listPlaces);
		respMsg.println(dataJSON);
		respMsg.flush();
		respMsg.close();
	}
	else{
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			respMsg.println("No places returned for specified search criteria");
			respMsg.flush();
			respMsg.close();
	}
  }
}