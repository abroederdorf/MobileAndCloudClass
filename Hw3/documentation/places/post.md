# Places: POST

## /places

Adds new user to database

---

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/places
```

## Request Body

 - **type** *(required)*: Type of place, i.e. water fountain or bathroom
 - **name** *(required)*: Short, descriptive name of place
 - **userId** *(required)*: Database id of user who created place
 - **latitude** *(required)*: Latitude coordinate for location of place
 - **longitude** *(required)*: Longitude coordinate for location of place
 - **status** *(required)*: Status of location, open or closed


### Example

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'type=Water Fountain&name=Fred Meyer Ballard&latitude=47.660417&longitude=-122.368805&status=Open&userId=5707702298738688' "http://runnersaidapp2.appspot.com/api/v1/places"
```

---

## Response

The response will include a JSON object of the newly created place

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
- 400: Bad request
 - Request parameters were not all supplied
 - Place already exists
 - User id invalid
- 500: Internal server error
 - Place not successfully added to database


### JSON Object

```
{
  "id": 5715999101812736,
  "type": "Water Fountain",
  "name": "Fred Meyer Ballard",
  "createdDate": "Oct 24, 2016 12:33:43 AM",
  "createdUserId": 5707702298738688,
  "latitude": 47.660417,
  "longitude": -122.368805,
  "status": "Open",
  "statusDate": "Oct 24, 2016 12:33:43 AM",
  "vote": 1
}
```