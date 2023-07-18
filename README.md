# IBK Challenge
Multiple microservices to manage banking products.

## Requirements
- Java 11
- Maven 3
- Node 16
- MySQL/MariaDB
- Postman
- Docker

## How to use
Clone this repository and run the following commands:

### For Spring Boot microservices:
```bash
mvn clean install
mvn spring-boot:run
```
Run in the following order:
- ms-config-server
- ms-eureka-server
- ms-identity-server
- ms-products
- ms-cusomers

To run tests:
```bash
mvn test
```

-------------------
### For NestJS microservices:
```bash
npm install
npm run start:dev
```
Run in the following order:
- ms-bff-web

To run tests:
```bash
npm run test
```

## Next steps
Import in Postman the collection `IBK Challenge.postman_collection.json` to test the endpoints.

## Swagger
Visit the following URL to see the Swagger documentation:
- http://localhost:3000/api