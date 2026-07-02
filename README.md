# Institute ERP Backend

A comprehensive Enterprise Resource Planning (ERP) system for managing institute and software company operations. Built with Spring Boot 3.3.0 and Java 17.

## Features

### Core Modules
- **User Management**: Authentication, authorization, role-based access control
- **Student Management**: Student profiles, admissions, tracking
- **Course Management**: Course creation, scheduling, curriculum management
- **Batch Management**: Batch creation, enrollment management
- **Department Management**: Department organization and hierarchy
- **Attendance Tracking**: Student and staff attendance
- **Enrollment Management**: Course enrollment and management

## Technology Stack

- **Backend Framework**: Spring Boot 3.3.0
- **Language**: Java 17
- **Database**: PostgreSQL / MySQL
- **ORM**: Hibernate (Spring Data JPA)
- **Security**: Spring Security with JWT
- **API Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven
- **Mapping**: MapStruct
- **Lombok**: Code generation for boilerplate reduction

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ or PostgreSQL 12+
- Git

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/activesurya/institute-erp-backend.git
cd institute-erp-backend
```

### 2. Configure Database

Edit `src/main/resources/application.yml`:

**For MySQL:**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/institute_erp
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

**For PostgreSQL:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/institute_erp
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

## API Documentation

Once the application is running, access Swagger UI:
```
http://localhost:8080/api/swagger-ui.html
```

## Project Structure

```
src/main/
├── java/com/instituterp/
│   ├── controller/         # REST API endpoints
│   ├── service/            # Business logic layer
│   ├── repository/         # Data access layer
│   ├── entity/             # JPA entities
│   ├── dto/                # Data transfer objects
│   ├── security/           # Security components (JWT, etc.)
│   ├── exception/          # Custom exceptions
│   ├── util/               # Utility classes
│   └── InstituteErpApplication.java
└── resources/
    ├── application.yml     # Application configuration
    └── application-prod.yml # Production configuration
```

## Key Entities

### User
- Base user entity with authentication support
- Implements Spring Security's UserDetails interface
- Many-to-many relationship with roles

### Student
- Student profile information
- Links to user account
- Tracks admission and enrollment data

### Course
- Course information and details
- Belongs to department
- Has multiple batches

### Batch
- Specific offering of a course
- Contains multiple student enrollments
- Tracks start and end dates

### Department
- Organizational unit for courses
- Manages course offerings

## API Endpoints

### Authentication
- `POST /api/auth/login` - Login user
- `POST /api/auth/register` - Register new user
- `POST /api/auth/refresh-token` - Refresh JWT token

### Students
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `POST /api/students` - Create new student
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

### Courses
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `POST /api/courses` - Create new course
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course

## Configuration Files

### application.yml
- Database configuration
- JWT secret and expiration settings
- Logging levels
- Swagger UI settings

## Security

- JWT-based authentication
- Role-Based Access Control (RBAC)
- Spring Security integration
- Password encryption (BCrypt recommended)

## Next Steps

1. **Implement Service Layer**: Add business logic in service classes
2. **Add Controller Logic**: Implement CRUD operations in controllers
3. **Add Frontend**: Create React or Angular frontend
4. **Add Testing**: Write unit and integration tests
5. **Deploy**: Configure Docker and Kubernetes for deployment

## Contributing

Fork the repository and create a new branch for your feature:

```bash
git checkout -b feature/your-feature
git commit -am 'Add your feature'
git push origin feature/your-feature
```

## License

MIT License

## Support

For issues and questions, please create an issue on GitHub.

## Author

Developed by [activesurya](https://github.com/activesurya)
