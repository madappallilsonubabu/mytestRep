# Store Locator API

This is the Store Locator Application used for Creating/Updating/Deleting/Searching details of store using a json file as storage.
The store details are saved in a json file inside the resource/data directory of spring boot application.The application also supports filtering of stores on the basis of location,country,stores within a defined radius and stores currently open.
 If authentication is enabled, when calling the APIs use api-key:testapikey to authenticate

## Functionalities

* API that create a or multiple store(s) with store details 

        URL := http://localhost:9900/storelocator/stores
		
        Method  := POST
		
       
* API that can update an existing store 

      URL := http://localhost:9900/storelocator/stores/<storeId>
	  
      Method  := PUT  
     
  
* API that can delete a store

      URL := http://localhost:9900/storelocator/stores/<storeId>
	  
      Method  := DELETE
  
* API that can list all stores 

      URL := http://localhost:9900/storelocator/stores
	  
      Method  := GET
      
* API that can list all stores with following filters

      o	in a location (city) - 
	  
                              URL := http://localhost:9900/storelocator/stores
							  
                              Method  := GET
							  
                              Request Param := city : <value>  
							  
      o	in a country -
	  
                              URL := http://localhost:9900/storelocator/stores
							  
                              Method  := GET
							  
                              Request Param := country : <value>
  
      o	stores within a given radius with uom (miles or kms) -
	  
                              URL := http://localhost:9900/storelocator/stores
							  
                              Method  := GET
							  
                              Request Param := radius : <value>
							  
                                               location : <value>  OR     latitude : <value>,longitude : <value>
											   
                                               uom : kms or miles (optional)
                              
      o	stores that are open at the current time and date -
	  
                              URL := http://localhost:9900/storelocator/stores
							  
                              Method  := GET
							  
                              Request Param := current : true
                                               

## Security
Optional API Key based security which is disabled by default and can be enabled from application.yaml file 

store:

	authentication:
	
      enabled: true

"api-key" : "testapikey"

## API Documentation

Documentation for the StoreLocator API can be found in "store-locator-swagger.yaml" file.

## Requirements

* [Java](https://www.oracle.com/java/)
* [Maven](https://maven.apache.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [jUnit](https://junit.org/)
* [Docker](https://www.docker.com/)

## Installation

Use the maven command line interface [Maven](https://maven.apache.org/) to install the dependencies

```bash
mvn install -DSkipTests
```
## Test
Use the maven command line interface [Maven](https://maven.apache.org/) to perform unit test
```bash
mvn test
```
## Docker build
```bash
docker build --rm -f "Dockerfile" -t store-locator:latest "."
```

### How the docker image is build
* Base image for the docker container is `adoptopenjdk/openjdk11-openj9:alpine-slim`
* The dependency libraries under `/target/dependency/lib` are copied to the `/app/lib` in the image
* Resources files (for e.g. configuration files , properties files etc.) under `/target/dependency/resources` are copied to `/app` in the image
* Compiled class files under `/target/classes` are copied to `/app` in the image

### Branches
* `master`  - main branch


