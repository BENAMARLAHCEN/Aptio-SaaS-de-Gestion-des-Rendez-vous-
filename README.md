# 🚀 Aptio API - Appointment Management System

<div align="center">
  
  ![Aptio](https://img.shields.io/badge/Aptio-Appointment%20System-2196F3?style=for-the-badge)
  ![Version](https://img.shields.io/badge/Version-1.0.0-4CAF50?style=for-the-badge)
  ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.0-6DB33F?style=for-the-badge&logo=springboot)
  ![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
  ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-336791?style=for-the-badge&logo=postgresql&logoColor=white)
  
</div>

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Authentication](#-authentication)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Development](#-development)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [License](#-license)

## 🔍 Overview

Aptio API is a modern RESTful backend service for managing appointments, staff scheduling, services, and customers. Designed for businesses in the service industry, it provides a comprehensive solution for streamlining scheduling operations.

The system enables businesses to manage their service offerings, staff availability, customer records, and appointments efficiently through a robust set of APIs.

## ✨ Features

- 👤 **User Management** - Authentication, authorization, and user profile management
- 👥 **Customer Management** - Customer records with contact details, notes, and appointment history
- 🛎️ **Service Management** - Configurable service offerings with categories, durations, and pricing
- 👩‍💼 **Staff Management** - Staff profiles, schedules, and specialties
- 📅 **Appointment Scheduling** - Create, update, and manage appointments
- 📆 **Schedule Management** - Staff working hours, breaks, and time-off
- 🏢 **Business Settings** - Configurable business hours, days of operation, and scheduling rules
- 🔐 **Role-Based Access Control** - User, Staff, and Admin permission levels

## 🔧 Tech Stack

- **Backend Framework**: Spring Boot 3.1.0
- **Language**: Java 17
- **Database**: PostgreSQL 14
- **Authentication**: JWT (JSON Web Tokens)
- **API Documentation**: OpenAPI/Swagger
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito
- **Containerization**: Docker

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- PostgreSQL 14+
- Docker (optional)

### Installation

1. **Clone the repository**

```bash
git clone https://github.com/yourusername/aptio-api.git
cd aptio-api
```

2. **Configure the database**

Update the `application.yml` file with your database connection details:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/aptiodb
    username: postgres
    password: yourpassword
```

Alternatively, you can use the provided Docker Compose file:

```bash
docker-compose up -d
```

3. **Build the application**

```bash
mvn clean package
```

4. **Run the application**

```bash
java -jar target/aptio-api-1.0.0.jar
```

Or using Spring Boot Maven plugin:

```bash
mvn spring-boot:run
```

5. **Access the API**

The API will be available at: `http://localhost:8080/api/v1`

Swagger documentation: `http://localhost:8080/api/v1/swagger-ui`

## 📚 API Documentation

The API is documented using OpenAPI/Swagger. You can explore and test the endpoints by navigating to the Swagger UI after starting the application:

`http://localhost:8080/api/v1/swagger-ui`

You can also import the [Postman Collection](./postman/Aptio-API.postman_collection.json) for testing the API endpoints.

## 🔐 Authentication

The API uses JWT (JSON Web Tokens) for authentication. To access protected endpoints, you need to:

1. Register a user or use the default admin account:
   - Email: admin@aptio.com
   - Password: password

2. Authenticate to get a token:

```http
POST /api/v1/auth/authenticate
{
    "email": "admin@aptio.com",
    "password": "password"
}
```

3. Use the token in the `Authorization` header for subsequent requests:

```
Authorization: Bearer your_jwt_token
```

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/aptio/
│   │   ├── config/         # Configuration classes
│   │   ├── controller/     # REST API controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Custom exceptions and handlers
│   │   ├── model/          # Entity models
│   │   ├── repository/     # Data access interfaces
│   │   ├── service/        # Business logic
│   │   └── util/           # Utility classes
│   └── resources/
│       ├── application.yml # Application configuration
│       ├── data.sql        # Initial data
│       └── ...
└── test/                   # Unit and integration tests
```

## 🗃️ Database Schema

The application uses the following main entities:

- **User** - System users with roles
- **Customer** - Client information
- **Service** - Available services
- **ServiceCategory** - Service categorization
- **Staff** - Staff member profiles linked to users
- **Appointment** - Scheduled appointments
- **WorkHours** - Staff working hours
- **Resource** - Physical resources (rooms, equipment)
- **ScheduleEntry** - Calendar entries (appointments, breaks, time off)

## 💻 Development

### Adding a New Entity

1. Create an entity class in the `model` package
2. Create a repository interface in the `repository` package
3. Create DTO classes in the `dto` package
4. Create a service class in the `service` package
5. Create a controller in the `controller` package
6. Add necessary validation and error handling

### Code Style

This project follows the Google Java Style Guide. You can format your code using:

```bash
mvn spotless:apply
```

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ServiceEntityServiceTest
```

### Integration Tests

Integration tests use a test database configuration. You can run them with:

```bash
mvn verify
```

## 🚢 Deployment

### Building for Production

```bash
mvn clean package -P production
```

### Docker Deployment

1. Build the Docker image:

```bash
docker build -t aptio-api:latest .
```

2. Run the container:

```bash
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=production aptio-api:latest
```

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">
  <p>Made with ❤️ by Lahcen BEN AMAR</p>
</div>
