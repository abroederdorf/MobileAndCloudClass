# Places: GET

## /places?id=

Returns details for specified place

~~~

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/places?id=5654313976201216
```

- **id** *(required)*: Database id of place

~~~

## Response

The response will include a JSON object of the place specified

- Place Object:
 - **id**: Id of place generated from database
 - **type**: Type of place, i.e. water fountain or bathroom
 - **name**: Short, descriptive name of place
 - **createDate**: Date the place object was created
 - **createdUserId**: Database id of user who created place
 - **latitude**: Latitude coordinate for location of place
 - **longitude**: Longitude coordinate for location of place
 - **status**: Status of location, open or closed
 - **statusDate**: Date for the most recent update of the status
 - **vote**: Integer representing the confidence vote of the status. This may be any interger - positive, negative, or zero

### Status
- 200: Ok
- 400: Bad Request
 - Place id invalid, it does not exist in database

### JSON Object

```
{
  "id": 5654313976201216,
  "type": "Bathroom",
  "name": "Greenlake Community Center",
  "createdDate": "Oct 23, 2016 10:36:47 PM",
  "createdUserId": 5722646637445120,
  "latitude": 47.680195,
  "longitude": -122.329071,
  "status": "Closed",
  "statusDate": "Oct 23, 2016 10:36:47 PM",
  "vote": 1
}

```