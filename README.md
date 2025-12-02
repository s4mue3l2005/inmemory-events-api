# In-Memory Events API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

REST API for event and venue management implemented with **Hexagonal Architecture (Ports & Adapters)**.

## 📋 Description

This project is an event and venue catalog that demonstrates the implementation of Hexagonal Architecture, completely separating the business core from external frameworks (Spring, JPA, etc.), achieving technological independence and facilitating unit testing.

## ✨ Features

-   **Hexagonal Architecture**: Clear separation between Domain, Application, and Infrastructure.
-   **RESTful API**: Complete CRUD for Events and Venues.
-   **In-Memory Database**: Uses H2 for easy setup and testing.
-   **DTO Mapping**: Efficient mapping using MapStruct.
-   **OpenAPI Documentation**: Integrated Swagger UI.
-   **Pagination**: Domain-centric pagination implementation.

## 🛠️ Tech Stack

-   **Java 17**
-   **Spring Boot 3.5.7**
-   **Spring Data JPA**
-   **H2 Database** (in-memory)
-   **MapStruct 1.5.5**
-   **Lombok**
-   **SpringDoc OpenAPI**

## 🏗️ Architecture

The project is organized following the Hexagonal Architecture (Ports & Adapters) principles:

### Package Structure

```
com.example.inmemoryeventsapi/
├── dominio/                          # Business Core (No external dependencies)
│   ├── model/                        # Pure Domain Entities
│   ├── ports/
│   │   ├── in/                       # Input Ports (Use Cases)
│   │   └── out/                      # Output Ports (Repositories)
│   └── exception/                    # Domain Exceptions
│
├── aplicacion/                       # Application Layer
│   └── usecase/                      # Use Case Implementations
│
└── infraestructura/                  # Adapters (External Frameworks)
    ├── adapters/
    │   ├── in/                       # Input Adapters (REST)
    │   └── out/                      # Output Adapters (JPA)
    └── config/                       # Spring Configuration
```

### Key Principles

1.  **Technological Independence**: The domain has no dependencies on Spring, JPA, or other frameworks.
2.  **Separation of Concerns**: Each layer has a clear responsibility.
3.  **Dependency Inversion**: The domain defines interfaces (ports) that are implemented by the infrastructure.

### Data Flow

```
HTTP Client
    ↓
EventoRestAdapter (Input Adapter)
    ↓
EventoUseCaseImpl (Use Case)
    ↓
EventoRepositoryPort (Port)
    ↓
EventoJpaAdapter (Output Adapter)
    ↓
EventJpaRepository (JPA)
    ↓
Database
```

## � Getting Started

### Prerequisites

-   Java 17 or higher
-   Maven 3.6+

### Installation & Running

1.  **Clone the repository** (if applicable) or navigate to the project directory.

2.  **Build the project**:
    ```bash
    mvn clean install
    ```

3.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

The application will be available at: `http://localhost:8080`

## 📖 API Documentation

Once the application is running, you can access the Swagger documentation at:

-   **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
-   **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Key Endpoints

#### Events
-   `GET /events` - List events (with pagination)
-   `GET /events/{id}` - Get event by ID
-   `POST /events` - Create event
-   `PUT /events/{id}` - Update event
-   `DELETE /events/{id}` - Delete event

#### Venues
-   `GET /venues` - List venues
-   `GET /venues/{id}` - Get venue by ID
-   `POST /venues` - Create venue
-   `PUT /venues/{id}` - Update venue
-   `DELETE /venues/{id}` - Delete venue

## 🧪 Testing

To run the unit and integration tests:

```bash
mvn test
```

## � Implementation Notes

### MapStruct Mapping
The project uses MapStruct to convert between:
1.  **DTO ↔ Domain**: In REST adapters.
2.  **Entity ↔ Domain**: In JPA adapters.

This ensures that:
-   The domain remains pure (no JPA annotations).
-   Conversions are type-safe and efficient.

### Domain Pagination
To keep the domain free of Spring dependencies, custom `Page` and `Pageable` classes are used in the domain layer, which are mapped to Spring Data's equivalents in the adapters.

### Exception Handling
Domain exceptions (`NotFoundException`, `BadRequestException`, `ConflictException`) are caught by the `GlobalExceptionHandler` in the web infrastructure layer and converted into appropriate HTTP responses.

## � Author

Project developed as part of a Hexagonal Architecture implementation with functional equivalence.
