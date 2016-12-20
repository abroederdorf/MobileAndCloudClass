package com.osu.cs496.mybusstop;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


@SuppressWarnings("serial")
public class BusStopServlet extends HttpServlet{

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {

		//Initialize variables
		long currentMillis = System.currentTimeMillis();
		long futureMillis, timeDiff;
		SimpleDateFormat formatterA = new SimpleDateFormat("hh:mm:ss a");
		formatterA.setTimeZone(TimeZone.getTimeZone("PST"));
		SimpleDateFormat formatterW = new SimpleDateFormat("mm:ss");
		int numberEvents;
		String route, arrival, wait, stopDirection;
		
		ArrayList<BusInfo> buses = new ArrayList<BusInfo>();
		ArrayList<BusStop> stops = new ArrayList<BusStop>();
		String[] busStops = {"1_7380", "1_6030", "1_6050", "1_2247", "1_2285", "1_6235", "1_6240", "1_590"}; //bus stop of interest
		//http://stackoverflow.com/questions/1005073/initialization-of-an-arraylist-in-one-line
		List<String> busRoutes = Arrays.asList("26", "62", "5", "28", "26E", "28E", "8"); //routes of interest
		
		
		for (int j = 0; j < busStops.length; j++){
			//Initialize variables
			buses.clear();
			futureMillis = currentMillis;
			
			//Fetch url and read response
			//API documentation: http://developer.onebusaway.org/modules/onebusaway-application-modules/current/api/where/index.html
			URL url = new URL("http://api.pugetsound.onebusaway.org/api/where/arrivals-and-departures-for-stop/" + busStops[j] + ".json?key=d20c6ed5-505d-480f-842d-1c2b26e2b976");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer json = new StringBuffer();
			String line, stopName = "";
			
			while ((line = reader.readLine()) != null) {
			  json.append(line);
			}
			reader.close();
			
			//Parse data
			JSONObject jo = new JSONObject(json.toString());
			numberEvents = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").length();
			for (int i = 0; i < numberEvents; i++)
			{
				//Get route
				route = "";
				route = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(i).getString("routeShortName");
				
				//Only add routes of interest
				if (busRoutes.contains(route)){
					//Get arrival time
					arrival = "Not available";
					if (jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(i).getLong("predictedArrivalTime") != 0)
					{
						futureMillis = jo.getJSONObject("data").getJSONObject("entry").getJSONArray("arrivalsAndDepartures").getJSONObject(i).getLong("predictedArrivalTime");
						Date dateA = new Date(futureMillis);
						arrival = formatterA.format(dateA);
					}
					
					//Get wait time
					wait = "";
					timeDiff = futureMillis - currentMillis > 0 ? futureMillis - currentMillis : 0;
					Date dateW = new Date(timeDiff);
					wait = formatterW.format(dateW);
					
					//Create and add object
					buses.add(new BusInfo(route, arrival, wait));
				}
			}
			
			//Get stop name 
			stopName = "";
			stopDirection = "";
			int numStops = jo.getJSONObject("data").getJSONObject("references").getJSONArray("stops").length();
			for (int k = 0; k < numStops; k++){
				if(busStops[j].equals(jo.getJSONObject("data").getJSONObject("references").getJSONArray("stops").getJSONObject(k).getString("id"))){
					stopName = jo.getJSONObject("data").getJSONObject("references").getJSONArray("stops").getJSONObject(k).getString("name");
					stopDirection = jo.getJSONObject("data").getJSONObject("references").getJSONArray("stops").getJSONObject(k).getString("direction");
				}
			}
			
			//Create bus stop object and add to list
			stops.add(new BusStop(buses, stopName, busStops[j], stopDirection));
		}
	
		//Return response
		req.setAttribute("stops", stops);
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}
}
