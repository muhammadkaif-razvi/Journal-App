# Journal App

A secure REST API for personal journaling, built with **Spring Boot**, **Spring Security** and **MongoDB**. Users sign up, log in with HTTP Basic auth and manage their own private journal entries. Admins can view and manage users.

## Features

- **User accounts:** public sign-up, then view, update or delete your own account
- **Private journal entries:** create, read, update and delete entries; each user sees only their own
- **Role-based access control:** `USER` and `ADMIN` roles enforced with Spring Security
- **Secure passwords:** hashed with BCrypt, never stored in plain text
- **Stateless auth:** HTTP Basic authentication with no server-side sessions
- **MongoDB persistence:** users and entries stored as separate collections, linked with `@DBRef`, with transactional writes
- **Tests:** unit and integration tests with JUnit 5, Mockito and `@SpringBootTest`
- **CI:** GitHub Actions workflow that builds with Maven and runs SonarQube code-quality analysis

## Tech stack

| Layer | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 2.7 (Web, Data MongoDB, Security) |
| Database | MongoDB |
| Security | Spring Security, BCrypt, HTTP Basic |
| Testing | JUnit 5, Mockito, Spring Boot Test |
| Build | Maven (wrapper included) |
| CI / quality | GitHub Actions, SonarQube (SonarCloud) |
| Other | Lombok, Logback |

## Project structure

```
src/main/java/net/Journal/App
├── config/        # Spring Security configuration
├── controller/    # REST controllers: public, user, journal, admin
├── entity/        # MongoDB documents: User, Entry
├── repository/    # Spring Data MongoDB repositories
└── service/       # Business logic and UserDetailsService
```

## API endpoints

All `/user/**` and `/journal/**` routes need HTTP Basic auth. `/admin/**` routes need the `ADMIN` role.

### Public

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/public/create-user` | Register a new user (role `USER`) |

### User (authenticated)

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/user` | Get the logged-in user's profile |
| PUT | `/user` | Update username and password |
| DELETE | `/user` | Delete the logged-in user's account |

### Journal entries (authenticated)

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/journal` | List all of your entries |
| POST | `/journal` | Create an entry |
| GET | `/journal/id/{id}` | Get one of your entries by ID |
| PUT | `/journal/{id}` | Update the title or content of an entry |
| DELETE | `/journal/{id}` | Delete an entry |

### Admin (role `ADMIN`)

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/admin/all-users` | List all users |
| POST | `/admin/create-admin-user` | Create a user with `ADMIN` and `USER` roles |

## Getting started

### Prerequisites

- Java 17
- MongoDB running locally or a MongoDB Atlas connection string

### 1. Clone the repo

```bash
git clone https://github.com/muhammadkaif-razvi/Journal-App.git
cd Journal-App
```

### 2. Configure MongoDB

Create `src/main/resources/application.yml` (it is git-ignored, so your credentials stay private):

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017
      database: journaldb
```

### 3. Run the app

```bash
./mvnw spring-boot:run        # macOS / Linux
mvnw.cmd spring-boot:run      # Windows
```

The API starts on `http://localhost:8080`.

### 4. Run the tests

```bash
./mvnw test
```

## Example requests

Register a user:

```bash
curl -X POST http://localhost:8080/public/create-user \
  -H "Content-Type: application/json" \
  -d '{"userName": "raza", "password": "secret123"}'
```

Create a journal entry:

```bash
curl -X POST http://localhost:8080/journal \
  -u raza:secret123 \
  -H "Content-Type: application/json" \
  -d '{"title": "First entry", "content": "Started my journal today."}'
```

List your entries:

```bash
curl -u raza:secret123 http://localhost:8080/journal
```

## Author

**Muhammad Kaif Razvi**, Java Full Stack Developer · [GitHub](https://github.com/muhammadkaif-razvi)
