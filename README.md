[![Build Status](https://travis-ci.com/itfobos/d-echo.svg?branch=master)](https://travis-ci.com/itfobos/d-echo)

# d-echo
Spring Cloud based, distributed echo server.

## Prerequisites
1. Docker
1. Docker compose
1. Gradle (4.0 or later)
1. Build the project: `gradle clean build`

## How to run 
- Make `.env` file in root project directory
- Put `CONFIG_SERVICE_PASSWORD`, `NOTIFICATION_SERVICE_PASSWORD`, 
    `STATISTICS_SERVICE_PASSWORD`, `ACCOUNT_SERVICE_PASSWORD`, 
    `MONGODB_PASSWORD` environment variable(with defined values) in the file.

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