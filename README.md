# Voting for restaurants

A Java backend project with registration/authorization and role-based access rights (USER, ADMINISTRATOR). The administrator can create/edit/delete users, restaurants and restaurant dishes (dishes are updated every day). Users can manage their profile and vote for the restaurant (choose where they want to have lunch today). The user can also change his vote for today if the time is before 11:00.

## Tools

- JDK 21
- Spring Boot 3.2.5
- Lombok
- H2
- Caffeine Cache
- Swagger/OpenAPI 3.0

## Technical requirement

Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote for a restaurant they want to have lunch at today
- Only one vote counted per user
- If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and 
couple curl commands to test it (better - link to Swagger).

-------------------------------------------------------------

P.S.: Make sure everything works with the latest version that is on github :)
P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

## Run

`mvn spring-boot:run` in root directory.

-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui/index.html)  
Credentials:
```
Admin: admin@gmail.com / admin
User:  user@yandex.ru / password
User2: user2@gmail.com / user2 (he didn't vote today)
```
