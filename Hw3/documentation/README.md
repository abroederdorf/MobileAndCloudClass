# Runner's Aid API Documentation

This documentation describes the endpoints and provides examples of an expected response for the Runner's Aid API, [http://runnersaidapp2.appspot.com/api/v1](http://runnersaidapp2.appspot.com/api/v1).

## Places

### GET
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/places/getList.md): Returns list of details for all places
- [/places?id=]](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/places/getPlace.md): Returns details for specified place

### POST
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/places/post.md): Adds new place to database

### PUT
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/places/put.md): Modify details of place, including vote for status

### DELETE
- [/places](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/places/delete.md): Remove place from database.

## Users

### GET
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/users/getList.md): Returns list of details for all users
- [/users?id=](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/users/getUser.md): Returns details for specified user
- [/users?id=&fields=favorite](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/users/getFavs.md): Returns list of favorite places for specified user

### POST
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/users/post.md): Adds new user to database

### PUT
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/users/putUser.md): Modify details of user
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/users/putFav.md): Modify user's favorite list by adding place

### DELETE
- [/users](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/users/deleteFav.md): Remove place from user's favorite list

## Search

### GET
- [/search](https://github.com/abroederdorf/MobileAndCloudClass/tree/master/Hw3/runnersaidapi/documentation/search/get.md): Search places using criteria of status, type, and/or location