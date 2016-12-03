# Runner's Aid API Documentation

This documentation describes the endpoints and provides examples of an expected response for the Runner's Aid API, http://runnersaidapi.appspot.com/api/v1.

## Places

### GET
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/places/getList.md): Returns list of details for all places
- [/places?id=](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/places/getPlace.md): Returns details for specified place

### POST
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/places/post.md): Adds new place to database

### PUT
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/places/put.md): Modify details of place, including vote for status

### DELETE
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/places/delete.md): Remove place from database.

## Users

### GET
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/users/get.md): Returns list of details for all users, details for specified user, favorite list of place objects or place ids for specified user, or user id given a username and password.

### POST
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/users/post.md): Adds new user to database

### PUT
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/users/putUser.md): Modify details of user
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/users/putFav.md): Modify user's favorite list by adding place

### DELETE
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/users/deleteFav.md): Remove place from user's favorite list

## Search

### GET
- [/search](https://github.com/abroederdorf/MobileAndCloudClass/blob/master/final/Documentation/search/get.md): Search places using criteria of status, type, location, and/or user id for favorite list