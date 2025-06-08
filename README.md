# 🛒 Electronic Store Backend

A simple and clean backend for an **Electronic Store**, built with **Java Spring Boot**. It provides REST APIs for managing products, categories, users, carts, and orders. This project is designed as a learning project for building a full backend system.

---

## 📌 Features

- 📦 Product Management (Create, Update, Delete, Search, Sort, Filter)
- 📁 Category Management
- 👥 User Management
- 🛒 Cart APIs
- 📦 Order APIs
- 📄 Pagination & Sorting on API responses

---

## 🧰 Tech Stack

Layer        | Technology                      
Language     | Java 17                         
Framework    | Spring Boot, Spring Web          
ORM          | Hibernate, Spring Data JPA       
Database     | MySQL                            
Build Tool   | Maven                            
Others       | Lombok, ModelMapper

---

## 🗂 Project Structure

├── src/

│ └── main/

│ ├── java/com/electronicstore/ # Application Source Code

│ └── resources/(# ADD this file and Sub Files)

│ └── application.properties # Config File

├── pom.xml # Maven Project File

└── README.md # Project Documentation

---

## ⚙️ Setup & Run

### 1. Clone the repository
```bash
git clone https://github.com/Devpatel1944/Electronic_Store_Backend.git
cd Electronic_Store_Backend
```
### 2. Create MySQL Database

```bash
CREATE DATABASE electronic_store;
```
### 3. Update application.properties
Edit this file: src/main/resources/application.properties
```bash
spring.application.name=ElectronicStore

#Port Number

server.port=9090

#Database Configuration

spring.datasource.url=jdbc:mysql://localhost:3306/#DataBase_Name
spring.datasource.username=UserName
spring.datasource.password=Password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#Hibernate Configuration

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 4. Build and Run the Application
```bash
./mvnw clean install
./mvnw spring-boot:run
```
Server will start at: http://localhost:8080

---

### 🔗 API Endpoints

---

### 📚 Future Improvements

Swagger API Documentation

Dockerize application and database

Add integration tests

Convert to microservices (optional)

UI with React

---
### 👨‍💻 Author

Deven Patel


