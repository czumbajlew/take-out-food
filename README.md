## TAKE OUT FOOD

Simple backend application for make food order to take out by registered client from registered restaurant.

### Start application

In dev-ops folder run <b>docker-compose.yaml</b> with
> <i>docker compose up -d </i>

In <b>application-</b>profile_name.yaml change in application.email.username and application.email.password for Your gmail credentials.  

To start application use spring profiles (VM options), to build db from docker use
> <i>-Dspring.profiles.active=develop</i>

### Start tests