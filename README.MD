# Taxi

This repository contains all aspects of a taxi service.
It contains a central dispatcher, an admin front-end and the taxi agents.

## Structure

The dispatcher has a REST api exposed which is consumed by the front-end.
At the same time it uses RabbitMQ for messaging with the agents.
The application currently relies on H2 as a database, this means that all data will be lost when the application is stopped.

## Building & Running

Every part has been dockerized, to make it easy to run.
To run the application, you first have to run the build script.

```
./build.sh
docker compose up
```

After doing this, all required docker containers will start to run a simulation.
The simulation automaticaly creates bookings that get accepted by the agents.
Note: the simulation does not keep state and restarting parts of it might cause issues.
Note: the front-end does not automatically update. You need to refresh the page in order to see the latest information.

## Resources

See the following URLs for good resources to see the application in action:
- database: http://localhost:8080/h2-console
- swagger-ui: http://localhost:8080/swagger-ui/index.html
- front-end: http://localhost:5173
- rabbitMQ: http://localhost:15672

## Challenges

Setting up RabbitMQ was rather cumbersome. Figuring out what the best type of exchanges was is up for debate, as it depends on the type of communication.
It would have been better to extract the config and types to a shared project such that it can be re-used.

