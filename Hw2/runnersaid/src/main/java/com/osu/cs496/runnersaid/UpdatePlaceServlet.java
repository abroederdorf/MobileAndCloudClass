package com.osu.cs496.runnersaid;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

//Add place form data
public class UpdatePlaceServlet extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	//Get parameters
	String type = req.getParameter("placeType");
    String name = req.getParameter("placeName");
    double longitude = Double.parseDouble(req.getParameter("longitude"));
	double latitude = Double.parseDouble(req.getParameter("latitude"));
	String status = req.getParameter("placeStatus");
	boolean favorite = Boolean.parseBoolean(req.getParameter("placeFav"));
	Date statusDate = new Date();
	long id = Long.parseLong(req.getParameter("placeId"));
	
	//Get entity
	List<Place> places = ObjectifyService.ofy().load().type(Place.class).list();
	int count = 0, index= 0;
	for (Place place : places){
		if (place.id == id)
			index = count;
		count++;
	}
	Place thisPlace = places.get(index);
	
	//Update with parameters
	thisPlace.setName(name);
	thisPlace.setType(type);
	thisPlace.setLatitude(latitude);
	thisPlace.setLongitude(longitude);
	thisPlace.setStatus(status);
	thisPlace.setFavorite(favorite);
	thisPlace.setStatusDate(statusDate);
	

    // Use Objectify to save the place and now() is used to make the call synchronously as we
    // will immediately get a new page using redirect and we want the data to be present.
    ObjectifyService.ofy().save().entity(thisPlace).now(); /******put()******/

    resp.sendRedirect("/viewPlace.jsp");
  }
}