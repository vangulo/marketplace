# MarketPlace

How to start the MarketPlace application
---
NOTE: java 8 recommended
1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/marketplace-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Swagger Endpoint
---
`http://localhost:8080/swagger`

Curl commands to test endpoints:
---

Create a project
```
curl -X POST 'http://localhost:8080/api/v1/projects' \
     -H 'Content-Type: application/json' \
     -d '{ "description": "This is a project",
           "maxBudget": "1000.00",
           "deadline":"2019-01-01 12:00:00"
         }'
```

Find a project by id
```
curl -X GET 'http://localhost:8080/api/v1/projects/1'
```

Add a bid to a project

```
 curl -X POST 'http://localhost:8080/api/v1/projects/1/bids' \
      -H 'Content-Type: application/json' \
      -d '{ "buyerName": "John",
            "price": "800.00"
          }' 
```



Design Choices
---
Dropwizard: Quick easy to bootstrap and run
Hashmap: simple, in memory
Notifications for validations: nice to get all errors at once
Service and DAO layers: for separation of concerns
ProjectDAO interface: so that we can switch out the data store to another implementations i.e. database
Path has versions: if we want to change the interface of the REST API