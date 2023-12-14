## Introduction

This is small spring MVC application named as User Management Project which simplifies the task of managing user information. It is built using Java, Spring Boot, and Thymeleaf, providing a robust and scalable solution for user data management.

## Features

- **Create User:** Add new users to the system.
- **Edit User:** Modify existing user details.
- **Delete User:** Remove a user from the system.
- **Filter Users:** Filter the list of users based on attributes.
- **Page Hits Counter:** Real-time display of page hits.

## Getting Started

### Prerequisites

Before you start, ensure you have the following installed:

- Java Development Kit (JDK)
- Maven
- Your preferred IDE (IntelliJ, Eclipse, etc.)

### Installation

Follow these steps to set up and run the User Management System:

```bash
# Clone the repository
git clone https://github.com/your-username/user-management-system.git
cd user-management-system

# Build the project
mvn clean install

# Run the application
java -jar target/user-management-system-1.0.jar

### Project Structure

user-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── ca.lambton.project/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── service/
│   │   │   │   └── ...
│   │   │   └── ...
│   │   └── resources/
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       ├── java/
│       │   ├── ca.lambton.project/
│       │   │   └── ...
│       │   │   
│       └── resources/
└── ...


### Technologies Used
Java: Programming language for the backend.
Spring Boot: Framework for building Java-based enterprise applications.
Thymeleaf: Template engine for server-side rendering.
Maven: Build automation tool.

### Requirements covered
This project is built as a part of college project requirements and below are the files with the requirements

1. A page with a form where users have to input information  
    --> /src/main/resources/templates/CreateUser.html
2. must have atleast 3 fields for the user to fill out
    --> /src/main/resources/templates/CreateUser.html
    --> /src/main/java/ca/lambton/project/model/User.java
    --> /src/main/java/ca/lambton/project/dto/UserDto.java
3. form must be validated on the server side (for every field)
    --> /src/main/java/ca/lambton/project/dto/UserDto.java(validations are added using annotations)
4. contents from the form should be persisted if it passes validation (saved into a database)
    --> /src/main/java/ca/lambton/project/serviceImpl/UserServiceImpl.java
    --> /src/main/java/ca/lambton/project/repository/UserRepository.java
5. A page that users can go to that lists the items created from the form in requirement 1 using Templates/Thymeleaf
    --> /src/main/java/ca/lambton/project/controller/UserController.java
    --> /src/main/resources/templates/UserList.html
6. must take an optional get param to filter the list by an attribute
    --> /src/main/java/ca/lambton/project/controller/UserController.java
    --> /src/main/resources/templates/UserList.html ( a filter field is added which use jpa specification to search in all the 4 fields and give results for the matching user list)
7. There must be an API that returns the number of page hits since the server was online
    --> /src/main/java/ca/lambton/project/controller/PageHitsController.java
8. This api should be called asynchronously every 3 seconds and the results displayed on every page
    --> /src/main/resources/templates/common/layout.html (it has javascript function that makes ajax call to api for getting the number of page hits in each 3 seconds)
9. There must be at-least 1 dependency injected into two different locations in the project 
    --> UserService, userRepository, modelMapper dependencies are injected in userController.java, userServiceImpl.java and test files(userServiceImplTest.java)
10.  use of lombok in data classes
    -->  /src/main/java/ca/lambton/project/dto/UserDto.java
    -->  /src/main/java/ca/lambton/project/model/User.java

Bonus

implement 2 spring or java library not covered in class
    --> I used additional libraries like appache commons lang3 library, modelMapper library, spring buildtools library.

    Reason of using these libraries
        - apache.commons.lang3 gives the several utiltiy methods like checking wherether String or objects are empty or null etc
        - spring buildtools watches the applciation and helps a lot during development. It restarts the application whenever it sees any code changes
        - modelMapper is very useful in mappring the dto files to model files and vice versa and reduces lots of mapping codes .

Also, Bootstrap is used on the html pages in Thymeleaf template to make look and feel of the application good.

### Below are the urls:
1. userlist page->  http://localhost:8080/user/list (GET)
2. create user page->  http://localhostL8080/user   (GET)
3. Create/Update user ->  http://localhost:8080/user/save (POST)
4. delete user ->  http://localhost:8080/user/delete/{id} (GET)
5. filter user list -> http://localhost:8080/user/list?filterParam=amrit (GET)
