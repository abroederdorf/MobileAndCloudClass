# Users: DELETE

## /users

Modify user's favorite list by adding place

---

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/users/{id1}/place?id=
```

- **id1** *(required)*: Database id of user
- **id** *(required)*: Database id of place

### Example

```
curl -X DELETE -H "Content-Type: application/x-www-form-urlencoded" 
"http://runnersaidapp2.appspot.com/api/v1/users/5664248772427776/place?id=5654313976201216"
```

---

## Response

The response will include a text message: 

### Status
- 200: Ok
- 400: Bad request
 - No user id provided
 - User id invalid
 - No place id provided
 - Bad URL, should be users/id/place?id=
 - Place not part of user's favorite list
- 500: Internal server error
 - Place not successfully deleted from list


### Message

```
Place successfully deleted from user's favorites
```