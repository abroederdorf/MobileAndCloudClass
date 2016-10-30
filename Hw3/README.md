# Runner's Aid API Documentation

This documentation describes the endpoints and provides examples of an expected response for the Runner's Aid API, [http://runnersaidapp2.appspot.com/api/v1](http://runnersaidapp2.appspot.com/api/v1).

## Places

### GET
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/places/getList.md): Returns list of details for all places
- [/places?id=](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/places/getPlace.md): Returns details for specified place

### POST
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/places/post.md): Adds new place to database

### PUT
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/places/put.md): Modify details of place, including vote for status

### DELETE
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/places/delete.md): Remove place from database.

## Users

### GET
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/users/getList.md): Returns list of details for all users
- [/users?id=](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/users/getUser.md): Returns details for specified user
- [/users?id=&fields=favorite](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/users/getFavs.md): Returns list of favorite places for specified user

### POST
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/users/post.md): Adds new user to database

### PUT
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/users/putUser.md): Modify details of user
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/users/putFav.md): Modify user's favorite list by adding place

### DELETE
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/users/deleteFav.md): Remove place from user's favorite list

## Search

### GET
- [/search](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/documentation/search/get.md): Search places using criteria of status, type, and/or location