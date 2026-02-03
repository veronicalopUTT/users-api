# Users API

REST API for user management built with **Spring Boot**.

## Features

* Create users with validations
* RFC (taxId) validation
* Phone and email validation
* Global exception handling
* Dockerized application

## Requirements

* Java 17
* Maven 3.6+
* Docker (optional)

## Run locally

Clone the repository and run:

```bash
mvn spring-boot:run
```

The API will be available at:

```
http://localhost:8082
```

## Build JAR

```bash
mvn clean package
```

The JAR will be generated in:

```
target/users-api-0.0.1-SNAPSHOT.jar
```

## Run with Docker

### Build image

```bash
docker build -t users-api .
```

### Run container

```bash
docker run -p 8082:8082 users-api
```

## API Endpoints

### Create user

**POST** `/users`

Example request:

```json
{
  "email": "raul@mail.com",
  "name": "Raul",
  "phone": "+525512345678",
  "password": "1234567",
  "taxId": "ABC900101AAA"
}
```

### Validation errors response

```json
{
  "timestamp": "2026-02-03T11:13:53",
  "status": 400,
  "error": "Bad Request",
  "messages": {
    "taxId": "Invalid RFC format",
    "phone": "Invalid phone format"
  }
}
```

## Technologies

* Java 17
* Spring Boot 3
* Maven
* Docker

## Author

Rony Lopez
