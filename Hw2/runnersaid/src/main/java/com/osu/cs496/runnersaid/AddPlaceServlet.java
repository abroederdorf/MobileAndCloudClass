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

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();  // Find out who the user is.

	String type = req.getParameter("placeType");
    String name = req.getParameter("placeName");
    double longitude = Double.parseDouble(req.getParameter("longitude"));
	double latitude = Double.parseDouble(req.getParameter("latitude"));
	String status = req.getParameter("placeStatus");
	boolean favorite = Boolean.parseBoolean(req.getParameter("placeFav"));
	
    if (user != null) {
      place = new Place(type, name, latitude, longitude, status, favorite, user.getUserId(), user.getEmail());
    } else {
      place = new Place(type, name, latitude, longitude, status, favorite);
    }

    // Use Objectify to save the place and now() is used to make the call synchronously as we
    // will immediately get a new page using redirect and we want the data to be present.
    ObjectifyService.ofy().save().entity(place).now();

    resp.sendRedirect("/addPlace.jsp");
  }
}