# dashery-complete [![Build Status](https://travis-ci.org/Cowbacca/dashery-complete.svg?branch=master)](https://travis-ci.org/Cowbacca/dashery-complete) [![codecov.io](https://codecov.io/github/Cowbacca/dashery-complete/coverage.svg?branch=master)](https://codecov.io/github/Cowbacca/dashery-complete?branch=master)
Monolithic version of Dashery.  For cheaper deployment to Heroku until microservices are actually needed.

Staging site: https://dashery-complete-staging.herokuapp.com/

Live site: http://www.dashery.co.uk/

## Local Setup

Run `npm install`.  This will install bower, and run bower install as a postinstall script.  Then run the spring boot app either from the IDE or via `mvn spring-boot:run`.

This project uses Lombok.  To add support for your IDE of choice, see this link: https://projectlombok.org/download.html

## Heroku Setup

Heroku needs to be set up to run npm install first.

```bash
heroku buildpacks:add --index 1 heroku/nodejs
heroku buildpacks:add --index 2 heroku/java
```

## Deployment

Standard Spring Boot deployment.  Requires the following properties to be present in the environment, whether via yml/properties file or environment variable:

```
spring:
  cloud:
    config:
      profile: #e.g. will get values from the dashery-autocomplete-{profile}.properties file from the config server.
      uri: #the uri of the config server, e.g.  http://dashery-config-server.herokuapp.com
      username: #username to access the config server.
      password: #password to access the config server.
```
