# Places: PUT

## /places

Modify details of place, including vote for status

---

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/places
```

## Request Body

- **id** *(required)*: Database id of place
 - **type** *(optional)*: Type of place, i.e. water fountain or bathroom
 - **name** *(optional)*: Short, descriptive name of place
 - **userId** *(optional)*: Database id of user who created place
 - **latitude** *(optional)*: Latitude coordinate for location of place
 - **longitude** *(optional)*: Longitude coordinate for location of place
 - **status** *(optional)*: Status of place, open or closed
 - **vote** *(optional)*: Value = up or down. To vote for status (up) or against status (down)

### Example

Update status for place:

```
curl -X PUT -H "Content-Type: application/x-www-form-urlencoded" 
-d 'id=5715999101812736&status=Closed' "http://runnersaidapp2.appspot.com/api/v1/places"
```

Vote up for place:

```
curl -X PUT -H "Content-Type: application/x-www-form-urlencoded" 
-d 'id=5715999101812736&vote=up' "http://runnersaidapp2.appspot.com/api/v1/places"
```

---

## Response

The response will include a JSON object of the updated place

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
- 400: Bad request
 - Id of place not provided 
 - Place id invalid
 - User id invalid
 - Place already exists
- 500: Internal server error
 - Place not successfully updated in database


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
  "status": "Closed",
  "statusDate": "Oct 24, 2016 12:58:35 AM",
  "vote": 2
}
```