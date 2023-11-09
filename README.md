# TR Group Project - Pomodoro app

## Documentation

The documentation of Pomodoro app includes:

- [Overview](#overview)
- [Planning](#planning)
- [Testing](#testing)
- [Contribution Guide](Documentation/CONTRIBUTING.md)

<br />

## Installation & Run Instructions

**1. Clone the repository:**
git clone https://github.com/TRGroupProject/trgroupproject

**2. Configure the database:**
Edit the contents of the application.properties file to attach to a database,
we have created a Postgres database that is in Google Cloud.
This is the configuaration information you will require:

spring.datasource.url=jdbc:postgresql://34.142.37.243:5432/pomodoro
spring.datasource.username=postgres
spring.datasource.password=
#spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

**3. Build the application from the IDE:**
mvn clean install

**4. Run the application:**
From the IDE, select Run TRGroupProject (from TRGroupProject.java) or Shift f10

The application is now available to explore using the following Endpoints

**5. Application Endpoints:**
All endpoints for our bespoke Tasks API are shown from: **hpps://localhost:8080**

**Welcome:** GET /api/v1/welcome
[Welcome](http://localhost:8080/api/v1/welcome)

**GetTaskList:** PATCH /api/v1/?taskid}
[GetTaskList](http://localhost:8080/api/v1/)

**HealthStatus:** GET /api/v1/album/health
[HealthStatus](http://localhost:8080/api/v1/health)

All endpoints for the Google Calendar API are shown below:

## Overview

[Project Board](https://github.com/orgs/TRGroupProject/projects/1/views/1?filterQuery=)
The Pomodoro application is a program designed to allow a user to work on tasks for a set amount of time, in this case 25 minutes.
A Countdown timer will be displayed indicating a countdown of those minutes. Once the 25 minutes is up, the user should then take a five minute break away from their computer to go get a drink, go for a breath of fresh air etc. This will allow the user to recuperate energy and, by the time they return to the computer, they will be able to proceed for a further 25 mins.

Users wil interact with the API using a command-line interface or an API platform such as [Postman](https://www.postman.com/), [Swagger](https://swagger.io/) or [curl](https://curl.se/).

<br />

## Planning

### Assumptions

- The user will not switch to a new task until the one they are currently working on is complete
- With that in mind, the user can pause the timer but the task will not be considered as ended until the user presses the end button
- At that stage, PomodoroEndDateTime will be updated, which is our criteria to ajudge that a task is completed.

### Approach

- Apply a TDD
- Design using OOP principals
- Create the User stories and the UML document
- Create the MVP, and if time add enhancements to the project
- Create a shared GitHub repository that all can work from
- Use branches for adding new features
- Use a GitHub feature for listing and assigning tasks and keeping track of progress
- Prepare sample dataset that can be populated into the database
- Use a Postgres database, attached to a Google cloud instance, to view and update the contents of the Database
