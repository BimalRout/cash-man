# simple-atm

### Overview
Simple test project, which simulates ATM work.


### Full technology stack
* Java 8
* Oracle 11g
* Spring Boot
* Spring Framework
* Spring MVC
* Spring Security
* Spring Data
* JSP
* Tomcat (Embedded)
* Logback
* Bootstrap 3
* HTML/CSS
* JavaScript/jQuery
* JUnit
* JMock

How to run

Cofigure oracle dtabase.
Import this project as Maven project to your eclipse.
Build the project. Right click, Run as spring Boot application
Default test data is configured, when 1st time running the application
Enter the card number/Account number, enter Pin then do necessary operations



### Running
* You will need a Oracle database to run this project. The dump for creating DB and user for it is there in main/resources/db/init.sql.
* Tables and test data are created automatically on the first application run (for test data see DefaultTestDataServiceImpl.java).
* Database connection is set up in main/resources/application.properties file.

Have fun!
