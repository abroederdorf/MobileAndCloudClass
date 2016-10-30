# Users: PUT

## /users

Modify user's favorite list by adding place

---

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/users
```

## Request Body

- **id** *(required)*: Database id of user
- **favorite** *(required)*: Database id of place to add to user's favorite list. Note, only one id can be provided in this field

### Example

Add place to user's favorite list:

```
> curl -X PUT -H "Content-Type: application/x-www-form-urlencoded" -d 'id=5664248772427776&favorite=5692462144159744' "http://runnersaidapp2.appspot.com/api/v1/users"
```

---

## Response

The response will include a JSON object of the updated user

- User Object:
 - **id**: Id of user generated from database
 - **userId**: User id associated with Google login information
 - **email**: Email of user
 - **favorite**: List of place ids to signify favorite list of user. Note all user's contain invalid -100 place id.

### Status
- 200: Ok
- 400: Bad request
 - Id of place not valid
 - Id of user not valid
 - Place already in user's favorite list
- 500: Internal server error
 - Place not successfully added in database


### JSON Object

```
{
  "id": 5664248772427776,
  "userId": "345987",
  "email": "jonest12@oregonstate.edu",
  "favorite": [
    -100,
    5692462144159744
  ]
}
```