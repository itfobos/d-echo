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
    
### Dev
`docker-compose -f docker-compose.yml -f docker-compose-dev.yml up`

`docker-compose.dev.yml` inherits `docker-compose.yml` with additional possibility to build images locally 
and expose all containers ports for convenient development.

### Prod
`docker-compose -f docker-compose.yml up`