# Public Digital Library

Reactive Spring Boot API for managing users, books, and dashboard widgets in a digital library.

## Tech Stack

- Java 21
- Spring Boot 4.0.3
- Spring WebFlux
- Spring Data R2DBC
- H2 (default, in-memory)
- PostgreSQL drivers included (optional runtime target)
- Maven Wrapper (`mvnw`, `mvnw.cmd`)

## Features

- CRUD APIs for users, books, and dashboards
- Aggregated dashboard summary endpoint
- Global error handling with consistent error payload
- SQL schema and seed data loaded on startup

## Project Structure

- `src/main/java/com/library/controller` - REST controllers
- `src/main/java/com/library/service` - service interfaces
- `src/main/java/com/library/service/impl` - service implementations
- `src/main/java/com/library/repository` - reactive repositories
- `src/main/java/com/library/model` - domain models
- `src/main/java/com/library/exception` - custom exceptions and handler
- `src/main/resources/schema.sql` - table creation script
- `src/main/resources/data.sql` - seed data
- `src/main/resources/application.yaml` - runtime configuration

## Default Configuration

Application runs on port `8989` with H2 in-memory DB by default.

`src/main/resources/application.yaml`:

- `spring.r2dbc.url=r2dbc:h2:mem:///librarydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- `spring.datasource.url=jdbc:h2:mem:librarydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- `spring.sql.init.mode=always`
- `server.port=8989`

This means `schema.sql` and `data.sql` are executed at startup.

## Prerequisites

- JDK 21+
- No separate DB setup required for default mode (H2 in-memory)

## Run the App

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

macOS/Linux:

```bash
./mvnw spring-boot:run
```

App base URL:

```text
http://localhost:8989
```

## Build and Test

Compile:

```bash
./mvnw -DskipTests compile
```

Run tests:

```bash
./mvnw test
```

Package:

```bash
./mvnw clean package
```

## API Endpoints

Base path: `/api`

### Users

- `POST /api/users` - Create user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by id
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

Create/Update payload:

```json
{
  "fullName": "Jane Doe",
  "email": "jane.doe@example.com",
  "phone": "+1-555-0100",
  "active": true
}
```

### Books

- `POST /api/books` - Create book
- `GET /api/books` - Get all books
- `GET /api/books/{id}` - Get book by id
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book

Create/Update payload:

```json
{
  "title": "Clean Architecture",
  "author": "Robert C. Martin",
  "isbn": "ISBN-2001",
  "publishedYear": 2017,
  "available": true
}
```

### Dashboards

- `POST /api/dashboards` - Create dashboard widget
- `GET /api/dashboards` - Get all widgets
- `GET /api/dashboards/{id}` - Get widget by id
- `PUT /api/dashboards/{id}` - Update widget
- `DELETE /api/dashboards/{id}` - Delete widget
- `GET /api/dashboards/summary` - Get aggregated summary

Create/Update payload:

```json
{
  "widgetName": "User Activity",
  "widgetType": "LINE_CHART",
  "status": "ACTIVE",
  "displayOrder": 1
}
```

Summary response:

```json
{
  "totalUsers": 3,
  "totalBooks": 3,
  "totalDashboardWidgets": 3,
  "activeUsers": 2,
  "availableBooks": 2
}
```

## Error Response Format

All handled errors follow this shape:

```json
{
  "timestamp": "2026-03-03T16:37:25.9032011",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 100",
  "path": "/api/users/100"
}
```

## Seed Data

Startup seed inserts:

- 3 users
- 3 books
- 3 dashboard widgets

See:

- `src/main/resources/schema.sql`
- `src/main/resources/data.sql`

## API Collection

Bruno collection is available at:

- `src/main/resources/bruno/public-digital-library`

## Notes

- IDs are auto-generated.
- This project is reactive end-to-end (`Flux`/`Mono`).
- PostgreSQL dependencies are included; you can switch datasource config from H2 to PostgreSQL if needed.
