# Authentication Service

Welcome to the Authentication Service repository! This service provides authentication and user management functionalities for the BookMySport application.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Setup Database](#setup-database)
  - [Configure Application Properties](#configure-application-properties)
  - [Build and Run](#build-and-run)

## Prerequisites

Before you begin, make sure you have the following installed on your machine:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Apache Maven](https://maven.apache.org/)
- [MySQL Database](https://www.mysql.com/) or another database of your choice
- [Git](https://git-scm.com/)

## Getting Started

Follow the steps below to set up the Authentication Service locally.

### Clone the Repository

```
git clone https://github.com/your-username/authentication-service.git
```
```
cd authentication-service
```
```
code . (Opens the folder in vscode
```
For database setup
```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
For email service
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${USERNAME1}
spring.mail.password=${PASSWORD1}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
## Go to Main SpringApplication File marked with @SpringBootApplication (Build and run)
```
src\main\java\com\bookmysport\authentication_service\AuthenticationServiceApplication.java
```
