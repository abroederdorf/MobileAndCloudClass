# Users: PUT

## /users

Modify details of user

---

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/users
```

## Request Body

- **id** *(required)*: Database id of user
- **userId** *(optional)*: String id of user associated with login information
- **email** *(optional)*: String of user email associated with login information 

### Example

Update email for user:

```
curl -X PUT -H "Content-Type: application/x-www-form-urlencoded" -d 'id=5664248772427776&email=jonest12@oregonstate.edu' "http://runnersaidapp2.appspot.com/api/v1/users"
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
 - Id of user not provided or not valid
 - User already exists
- 500: Internal server error
 - User not successfully updated in database


### JSON Object

```
{
  "id": 5664248772427776,
  "userId": "345987",
  "email": "jonest12@oregonstate.edu",
  "favorite": [
    -100
  ]
}
```