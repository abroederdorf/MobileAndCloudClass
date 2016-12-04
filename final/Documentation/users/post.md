# Users: POST

## /users

Adds new user to database

---

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/users
```

## Request Body

- **username** *(required)*: String for user's username
- **password** *(required)*: String for user's password 

### Example

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'username=jonest@oregonstate.edu&password=Password1' 
"http://runnersaidapp2.appspot.com/api/v1/users"
```

---

## Response

The response will include a JSON object of the newly created user

- User Object:
 - **id**: Id of user generated from database
 - **username**: Username of user
 - **password**: Password of user
 - **favorite**: List of place ids to signify favorite list of user. Note all user's contain invalid -100 place id.

### Status
- 200: Ok
- 400: Bad request
 - Request parameters were not all supplied
 - User already exists
- 500: Internal server error
 - User not successfully added to database


### JSON Object

```
{
  "id": 5664248772427776,
  "username": "jonest@oregonstate.edu",
  "password": "Password1",
  "favorite": [
    -100
  ]
}
```