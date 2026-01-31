# Blog App Backend

A robust Spring Boot backend for a personal blogging platform, featuring secure JWT authentication, role-based access control, and a clean, modular architecture.

## 🚀 Features

-   **User Authentication**: Secure Sign-up and Login using JWT (JSON Web Tokens).
-   **Blog Management**: CRUD operations for Posts, Categories, and Tags.
-   **Security**:
    -   Stateless session management.
    -   BCrypt password hashing.
    -   Centralized error handling.
    -   Custom JWT authentication filter.
-   **Database**: MySQL integration via Spring Data JPA.
-   **Mapper**: DTO to Entity mapping using MapStruct.

## 🛠️ Tech Stack

-   **Framework**: Spring Boot 3.5.3
-   **Language**: Java 21
-   **Build Tool**: Maven
-   **Security**: Spring Security 6, JJWT (0.12.5)
-   **Database**: MySQL
-   **Utilities**: Lombok, MapStruct

## 📂 Project Structure

The project follows a layered architecture for separation of concerns:

```
src/main/java/com/adityacode/Blog_App
├── config                  # Application configuration (Security, Data Seeding)
├── controllers             # REST Controllers (API Endpoints)
├── domain                  # Domain layer
│   ├── dtos                # Data Transfer Objects
│   └── entities            # JPA Entities
├── mapper                  # MapStruct Mappers
├── repository              # Spring Data JPA Repositories
├── security                # Security Components (JWT Utils, Filters, UserDetails)
└── services                # Business Logic Interfaces and Implementations
```

## 🔒 Security Implementation

The security layer is built using **Spring Security** and **JWT**. Here's how it works:

### 1. Configuration (`SecurityConfig.java`)
-   **Statelessness**: The session policy is set to `STATELESS` since we use tokens for each request.
-   **Endpoints**:
    -   Public: `/api/v1/auth/**` (Login/Signup), and `GET` requests for categories, posts, and tags.
    -   Protected: All other endpoints require authentication.
-   **Exception Handling**: Custom `JwtAuthenticationEntryPoint` handles 401 Unauthorized errors with a structured JSON response.

### 2. JWT Authentication Process
1.  **Login**: User sends credentials to `/api/v1/auth/login`.
2.  **Token Generation**: Upon valid credentials, `JwtUtils` generates a signed JWT containing the username and claims.
3.  **Request Filtering**:
    -   The `JwtAuthenticationFilter` intercepts every request.
    -   It checks for the `Authorization` header (`Bearer <token>`).
    -   It validates the token using `JwtUtils`.
    -   If valid, it extracts user details and sets the `Authentication` object in the `SecurityContext`.

### 3. Error Handling
-   **GlobalExceptionHandler**: A centralized controller advice that catches exceptions (like `BadCredentialsException`) across the application and returns a consistent `ApiErrorResponse` format.
-   **Logging**: SLF4J is used to log authentication failures and exceptions for debugging.

## ⚙️ Setup & Running

1.  **Prerequisites**:
    -   Java 21 SDK
    -   MySQL Server
    -   Maven

2.  **Configuration**:
    -   Update `src/main/resources/application.properties` with your database credentials:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/techblog
        spring.datasource.username=your_username
        spring.datasource.password=your_password
        ```
    -   Ensure `jwt.secret` is set to a secure 32+ character Base64 string.

3.  **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```

## 🔑 Key Classes

-   **`JwtUtils`**: Handles token creation, parsing, and validation.
-   **`JwtAuthenticationFilter`**: Intercepts requests to validate tokens.
-   **`SecurityConfig`**: Configures the security filter chain.
-   **`GlobalExceptionHandler`**: Manages API errors.

---
*Built with ❤️ by Aditya*
