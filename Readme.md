# Sample Spring boot application

This sample application implements a simple car offer domain model within
a Spring boot skeleton. The logic is mostly complete, but there is not HTTP-interface implemented, yet.

## Running

The application should run using the provided maven and JDK 17+:

```shell
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8888
```

You can use [sdkman](https://sdkman.io/install), if you don't have a working jdk at hand.
With sdkman is installed, in the project's root folder just run:

```shell
sdk env install
```

Lastly, if you don't want to use the application's default port `8080`, you can change it:

```shell
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8888
```

## Database

The application is by default configured to use an SQLite database, called `./springdemo.db`.
If you want to inspect it, you can use:

```shell
sqlite3 springdemo.db
```

Please, check how to install sqlite3 on your system.