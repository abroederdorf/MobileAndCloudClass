# Search: GET

## /search

Search places using criteria of status, type, and/or location

---

## URL Query Example

```
> http://runnersaidapp2.appspot.com/api/v1/search?status=Closed&type=Bathroom&radius=6&latitude=47.65&longitude=-122.3
```

- **type** *(optional)*: Type of place, i.e. water fountain or bathroom
- **status** *(optional)*: Status of palce, open or closed
- **radius** *(optional)*: Radius for search, in miles
 - **latitude** *(required if radius is used)*: Latitude coordinate of position to extend the search radius from
 - **longitude** *(required if radius is used)*: Longitude coordinate of position to extend the search radius from

---

## Response

The response will include a JSON object with an array of all place objects that meet search criteria

- Place Object:
 - **id**: Id of place generated from database
 - **type**: Type of place, i.e. water fountain or bathroom
 - **name**: Short, descriptive name of place
 - **createDate**: Date the place object was created
 - **createdUserId**: Database id of user who created place
 - **latitude**: Latitude coordinate for location of place
 - **longitude**: Longitude coordinate for location of place
 - **status**: Status of place, open or closed
 - **statusDate**: Date for the most recent update of the status
 - **vote**: Integer representing the confidence vote of the status. This may be any interger - positive, negative, or zero

### Status
- 200: Ok
- 204: No Content
 - There are no places that meet the search criteria

### JSON Object

```
[
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
]
```