# Users: GET

## /users

Returns list of details for all users

---

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/users
```

---

## Response

The response will include a JSON object with an array of all user objects

- User Object:
 - **id**: Id of user generated from database
 - **userId**: User id associated with Google login information
 - **email**: Email of user
 - **favorite**: List of place ids to signify favorite list of user. Note all user's contain invalid -100 place id.

### Status
- 200: Ok
- 204: No Content
 - There are no users in database

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