# Places: DELETE

## /places

Modify user's favorite list by adding place

---

## URL Query Example

```
http://runnersaidapp2.appspot.com/api/v1/places?id=
```

- **id** *(required)*: Database id of place

### Example

```
curl -X DELETE -H "Content-Type: application/x-www-form-urlencoded" -d '' 
"http://runnersaidapp2.appspot.com/api/v1/places?id=5715999101812736"
```

---

## Response

The response will include a text message: 

### Status
- 200: Ok
- 400: Bad request
 - No place id provided
 - Place id invalid
- 500: Internal server error
 - Place not successfully deleted


### Message

```
Place successfully deleted
```