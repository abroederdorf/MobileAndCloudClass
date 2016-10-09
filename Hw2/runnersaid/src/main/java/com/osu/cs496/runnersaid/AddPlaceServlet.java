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
public class AddPlaceServlet extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Place place;

	//Get details from form to create Place entity
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();  // Find out who the user is.

	String type = req.getParameter("placeType");
    String name = req.getParameter("placeName");
    double longitude = Double.parseDouble(req.getParameter("longitude"));
	double latitude = Double.parseDouble(req.getParameter("latitude"));
	String status = req.getParameter("placeStatus");
	boolean favorite = Boolean.parseBoolean(req.getParameter("placeFav"));
	
	//Check that place does not exist
	boolean exists = false;
	List<Place> places = ObjectifyService.ofy().load().type(Place.class).list();
	for (Place thisPlace : places){
		if (thisPlace.type.equals(type) &&
			(thisPlace.name.equals(name)) &&
			(thisPlace.latitude == latitude) &&
			(thisPlace.longitude == longitude)){
				exists = true;
			}
	}
	
	//If place doesn't exist create it, otherwise display error
	if (!exists){
		if (user != null) {
		  place = new Place(type, name, latitude, longitude, status, favorite, user.getUserId(), user.getEmail());
		} else {
		  place = new Place(type, name, latitude, longitude, status, favorite);
		}

		// Use Objectify to save the place and now() is used to make the call synchronously as we
		// will immediately get a new page using redirect and we want the data to be present.
		ObjectifyService.ofy().save().entity(place).now();

		resp.sendRedirect("/viewPlace.jsp");
	}
	else {
		resp.sendRedirect("/existingPlace.jsp");
	}
    
  }
}