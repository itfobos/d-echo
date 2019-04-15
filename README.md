[![Build Status](https://travis-ci.com/itfobos/d-echo.svg?branch=master)](https://travis-ci.com/itfobos/d-echo)

# d-echo
Spring Cloud based, distributed echo server.

## Prerequisites
1. Java 11 
1. Docker
1. Docker compose
1. Gradle (4.0 or later)
1. Build the project: `gradle clean build`
   1. Or `./gradlew clean build`. In the case, required gradle version will be downloaded. 

## How to run 
- Make `.env` file in root project directory
- Put environment variable(with defined values) in the file: 
  - `CONFIG_SERVICE_PASSWORD`
  - `CONFIG_SERVICE_USER`
  - `DB_USER` 
  - `DB_PASSWORD`
  - `DB_NAME` 
  - `JWT_SIGNATURE_PRIVATE_KEY` 
  - `JWT_SIGNATURE_PUBLIC_KEY`

`JWT_SIGNATURE_PRIVATE_KEY` and `JWT_SIGNATURE_PUBLIC_KEY` is a valid pair of RS256 keys.
The keys can be generated with `generateKeyPair.sh` script, placed in the project root.


### Locally
All the services should have link to a Config service. The link should be passed as property:

`-Dspring.cloud.config.uri=http://localhost:8888`

Or the property(`spring.cloud.config.uri`) can be passed via your IDE. 

In Config service, there is shared configuration for _'dev'_ profile: `application-dev.yml`
The configuration points at local Discovery service instance `http://localhost:8761/eureka/`

To get the corresponding configuration, _'dev'_ profile should be activated for each service.
 
### With Docker Compose   
##### Dev
`docker-compose -f docker-compose.yml -f docker-compose-dev.yml up`

`docker-compose.dev.yml` inherits `docker-compose.yml` with additional possibility to build images locally 
and expose all containers ports for convenient development.

##### Prod
`docker-compose -f docker-compose.yml up`

### Request examples

##### Login
```http request
POST http://localhost:4000/auth/login
Accept: application/json, text/plain, */*
Accept-Encoding: gzip, deflate, br
Accept-Language: en-US,en;q=0.9,ru;q=0.8
Content-Type: application/json

{
"login": "test",
"password": "test"
}

###
```

Token will be generated as response. The token can be used in a next request.

##### Echo

```http request
GET http://localhost:4000/echos/echo/asdasd
Accept: application/json, text/plain, */*
Accept-Encoding: gzip, deflate, br
Accept-Language: en-US,en;q=0.9,ru;q=0.8
Content-Type: application/json
Authorization: Bearer -=token=-

###
```

The request made to Echo service, via Gateway(Zuul). 