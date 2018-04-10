# service-mocks
Spring Boot service to demonstrate mocking

## Deploying locally

```
mvn clean install
```

### Run application

Run main class `ServiceMocksApplication.java`.

To run using stub responses add `--spring.profiles.active=local`

### Run application via Maven

Run normally `mvn spring-boot:run`

To run using stub responses add `mvn spring-boot:run -Dspring-boot.run.profiles=local`

## Docker

### Build a Docker image

Build JAR and confirm application works locally

```
./mvnw package
java -jar target/service-mocks-<version>-SNAPSHOT.jar
```

To build a Docker image, make sure you are running on a machine where Docker is installed.

```
./mvnw install dockerfile:build
```

Check image:
```
docker image ls
```
Should be listed as `springio/service-mocks`.

### Run in a Docker container

Run in container:
```
docker run -p 4000:8080 springio/service-mocks
```

Internally the service runs on port `8080`. The above command maps port `4000` of the docker machine to the spring boot service.

This allows us to access the application on:

```
http://<docker-machine-ip>:4000/api/movies/health
```

You can obtain the docker machine's IP using:
```
docker-machine ip
```

You can now run any number of containers of this service on this machine as long as each is on a different port (e.g. `4000`, `4001`, `4002`, etc...).

To stop a container:
```
docker container ls
docker container stop <Container NAME or ID>
```

This guide was based on: https://spring.io/guides/gs/spring-boot-docker/



