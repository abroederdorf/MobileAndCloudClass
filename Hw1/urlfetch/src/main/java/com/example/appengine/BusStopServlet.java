/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


@SuppressWarnings("serial")
public class BusStopServlet extends HttpServlet{

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {

// [START example]
    URL url = new URL("http://api.pugetsound.onebusaway.org/api/where/arrivals-and-departures-for-stop/1_7380.json?key=d20c6ed5-505d-480f-842d-1c2b26e2b976");
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    StringBuffer json = new StringBuffer();
    String line;
	
    while ((line = reader.readLine()) != null) {
      json.append(line);
    }
    reader.close();
// [END example]

	//Get Times
	/*int numberEvents = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length();
	long currentMillis = System.currentTimeMillis();
	long futureMillis, timeDiff;
	SimpleDateFormat formatterA = new SimpleDateFormat("hh:mm:ss a");
	formatterA.setTimeZone(TimeZone.getTimeZone("PST"));
	SimpleDateFormat formatterW = new SimpleDateFormat("mm:ss");
	String[] arrival = new String[numberEvents];
	String[] route = new String[numberEvents];
	String[] wait = new String[numberEvents];
	
	for (int i = 0; i < numberEvents; i++)
	{
		//Get route
		route[i] = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(i).getString("routeShortName");
		
		//Get arrival time
		futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(i).getLong("predictedArrivalTime");
		Date dateA = new Date(futureMillis);
		arrival[i] = formatterA.format(dateA);
		
		//Get wait time
		timeDiff = futureMillis - currentMillis;
		Date dateW = new Date(timeDiff);
		wait[i] = formatterW.format(dateW);
	}*/
	JSONObject jo = new JSONObject(json.toString());
	
	long currentMillis = System.currentTimeMillis();
	long futureMillis = currentMillis, timeDiff;
	SimpleDateFormat formatterA = new SimpleDateFormat("hh:mm:ss a");
	formatterA.setTimeZone(TimeZone.getTimeZone("PST"));
	SimpleDateFormat formatterW = new SimpleDateFormat("mm:ss");
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 1)
	{
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);		
		
		req.setAttribute("arrival7380_0", dateFormattedA);
		req.setAttribute("wait7380_0", dateFormattedW);
		req.setAttribute("route7380_0", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getString("routeShortName"));
	}
	
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 2)
	{
		futureMillis = currentMillis;
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);
		
		req.setAttribute("arrival7380_1", dateFormattedA);
		req.setAttribute("wait7380_1", dateFormattedW);
		req.setAttribute("route7380_1", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getString("routeShortName"));
	}
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 3)
	{
		futureMillis = currentMillis;
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(2).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(2).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);
	
		req.setAttribute("arrival7380_2", dateFormattedA);
		req.setAttribute("wait7380_2", dateFormattedW);
		req.setAttribute("route7380_2", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(2).getString("routeShortName"));
	}
	
	
	//Stop 6030
	url = new URL("http://api.pugetsound.onebusaway.org/api/where/arrivals-and-departures-for-stop/1_6030.json?key=d20c6ed5-505d-480f-842d-1c2b26e2b976");
    reader = new BufferedReader(new InputStreamReader(url.openStream()));
	json = new StringBuffer();
	
    while ((line = reader.readLine()) != null) {
      json.append(line);
    }
    reader.close();

    jo = new JSONObject(json.toString());
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 1)
	{
		futureMillis = currentMillis;
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);
	
		req.setAttribute("arrival7380_3", dateFormattedA);
		req.setAttribute("wait7380_3", dateFormattedW);
		req.setAttribute("route7380_3", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getString("routeShortName"));
	}
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 2)
	{
		futureMillis = currentMillis;
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);
	
		req.setAttribute("arrival7380_4", dateFormattedA);
		req.setAttribute("wait7380_4", dateFormattedW);
		req.setAttribute("route7380_4", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getString("routeShortName"));
	}
	
	//Stop 6050
	url = new URL("http://api.pugetsound.onebusaway.org/api/where/arrivals-and-departures-for-stop/1_6050.json?key=d20c6ed5-505d-480f-842d-1c2b26e2b976");
    reader = new BufferedReader(new InputStreamReader(url.openStream()));
	json = new StringBuffer();
	
    while ((line = reader.readLine()) != null) {
      json.append(line);
    }
    reader.close();

    jo = new JSONObject(json.toString());
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 1)
	{
		futureMillis = currentMillis;
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);
	
		req.setAttribute("arrival7380_5", dateFormattedA);
		req.setAttribute("wait7380_5", dateFormattedW);
		req.setAttribute("route7380_5", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(0).getString("routeShortName"));
	}
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 2)
	{
		futureMillis = currentMillis;
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);
	
		req.setAttribute("arrival7380_6", dateFormattedA);
		req.setAttribute("wait7380_6", dateFormattedW);
		req.setAttribute("route7380_6", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(1).getString("routeShortName"));
	}
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 3)
	{
		futureMillis = currentMillis;
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(2).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(2).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);
	
		req.setAttribute("arrival7380_7", dateFormattedA);
		req.setAttribute("wait7380_7", dateFormattedW);
		req.setAttribute("route7380_7", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(2).getString("routeShortName"));
	}
	
	if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length() >= 4)
	{
		futureMillis = currentMillis;
		String dateFormattedA = "Not available";
		if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(3).getLong("predictedArrivalTime") != 0)
		{
			futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(3).getLong("predictedArrivalTime");
			Date dateA = new Date(futureMillis);
			dateFormattedA = formatterA.format(dateA);
		}
		
		timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
		Date dateW = new Date(timeDiff);
		String dateFormattedW = formatterW.format(dateW);
	
		req.setAttribute("arrival7380_8", dateFormattedA);
		req.setAttribute("wait7380_8", dateFormattedW);
		req.setAttribute("route7380_8", jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(3).getString("routeShortName"));
	}
	
	/*req.setAttribute("arrivals", arrival);
	req.setAttribute("routes", route);
	req.setAttribute("waits", wait);
	req.setAttribute("length", numberEvents);*/
	
	//Return response
    req.getRequestDispatcher("/main.jsp").forward(req, resp);
  }

  /*@Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {

    String id = req.getParameter("id");
    String text = req.getParameter("text");

    if (id == null || text == null || id == "" || text == "") {
      req.setAttribute("error", "invalid input");
      req.getRequestDispatcher("/main.jsp").forward(req, resp);
      return;
    }

    JSONObject jsonObj = new JSONObject()
        .put("userId", 33)
        .put("id", id)
        .put("title", text)
        .put("body", text);

    // [START complex]
    URL url = new URL("http://jsonplaceholder.typicode.com/posts/" + id);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("PUT");

    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
    writer.write(URLEncoder.encode(jsonObj.toString(), "UTF-8"));
    writer.close();

    int respCode = conn.getResponseCode();  // New items get NOT_FOUND on PUT
    if (respCode == HttpURLConnection.HTTP_OK || respCode == HttpURLConnection.HTTP_NOT_FOUND) {
      req.setAttribute("error", "");
      StringBuffer response = new StringBuffer();
      String line;

      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      reader.close();
      req.setAttribute("response", response.toString());
    } else {
      req.setAttribute("error", conn.getResponseCode() + " " + conn.getResponseMessage());
    }
    // [END complex]
    req.getRequestDispatcher("/main.jsp").forward(req, resp);
  }*/

}
