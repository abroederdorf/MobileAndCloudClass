# Users: GET

## /users

Depending on parameters included in the query this endpoint may return a list of all users, the details for a specified user, the favorite list for a specified user, or the user id given the Google account user id.

---

## List:


### URL Query Examples
```
http://runnersaidapp2.appspot.com/api/v1/users
```

### Response

The response will include a JSON object with an array of all user objects

- User Object:
 - **id**: Id of user generated from database
 - **userId**: User id associated with Google login information
 - **email**: Email of user
 - **favorite**: List of place ids to signify favorite list of user. Note all user's contain invalid -100 place id.

#### Status
- 200: Ok
- 204: No Content
 - There are no users in database

#### JSON Object

```
[
  {
    "id": 5644406560391168,
    "userId": "67890",
    "email": "blackjoe111@oregonstate.edu",
    "favorite": [
      -100,
      5654313976201216,
      5629499534213120
    ]
  },
  {
    "id": 5707702298738688,
    "userId": "13579",
    "email": "smithjane111@oregonstate.edu",
    "favorite": [
      -100,
      5629499534213120
    ]
  }
]
```

---

## One User:

### URL Query Examples
```
http://runnersaidapp2.appspot.com/api/v1/users?id=5644406560391168
```

- **id** *(required)*: Database id of user

### Response

The response will include a JSON object of the user specified

- User Object:
 - **id**: Id of user generated from database
 - **userId**: User id associated with Google login information
 - **email**: Email of user
 - **favorite**: List of place ids to signify favorite list of user. Note all user's contain invalid -100 place id.

### Status
- 200: Ok
- 400: Bad Request
 - User id invalid, it does not exist in database

#### JSON Object

```
[
  {
    "id": 5644406560391168,
    "userId": "67890",
    "email": "blackjoe111@oregonstate.edu",
    "favorite": [
      -100,
      5654313976201216,
      5629499534213120
    ]
  }
]
```

---

## Favorite List: 

### URL Query Examples
```
http://runnersaidapp2.appspot.com/api/v1/users?id=123456789&fields=favorite
```

- **id** *(required)*: Database id of user
- **fields** *(required)*: "favorite", this indicates to return the favorite list for the specified user

### Response

The response will include a JSON object with an array of place objects specified as favorites of the specified user

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
 - User id invalid, it does not exist in database
 - URL invalid, only valid fields value is "favorite"

#### JSON Object

```
[
  {
    "id": 5692462144159744,
    "type": "Water Fountain",
    "name": "Greenlake Public Theater",
    "createdDate": "Oct 23, 2016 10:36:18 PM",
    "createdUserId": 5644406560391168,
    "latitude": 47.681865,
    "longitude": -122.339981,
    "status": "Closed",
    "statusDate": "Oct 23, 2016 10:36:18 PM",
    "vote": 1
  },
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
  },
  {
    "id": 5629499534213120,
    "type": "Bathroom",
    "name": "Greenlake Small Craft Center",
    "createdDate": "Oct 22, 2016 12:24:09 AM",
    "createdUserId": 5722646637445120,
    "latitude": 47.671899,
    "longitude": -122.343369,
    "status": "Open",
    "statusDate": "Oct 22, 2016 12:25:42 AM",
    "vote": 1
  }
]
```

---

## User Id:

### URL Query Examples
```
http://runnersaidapp2.appspot.com/api/v1/users?gid=67890
```

- **gid** *(required)*: Google account user id

### Response

The response will include the database user id as a long number

 - **id**: Id of user generated from database

#### Status
- 200: Ok
- 400: Bad Request
 - No users returned for that Google id

#### JSON Object

```
5644406560391168
```