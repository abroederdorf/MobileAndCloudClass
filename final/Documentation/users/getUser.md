# Users: GET

## /users?id=

Returns details for specified user

~~~

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/users?id=5644406560391168
```

- **id** *(required)*: Database id of user

~~~

## Response

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

### JSON Object

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