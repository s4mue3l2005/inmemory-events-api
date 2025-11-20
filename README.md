# In-Memory Events API

API REST para gestión de eventos y venues implementada con **Arquitectura Hexagonal (Ports & Adapters)**.

## 📋 Descripción

Este proyecto es un catálogo de eventos y venues que demuestra la implementación de la arquitectura hexagonal, separando completamente el núcleo de negocio de los frameworks externos (Spring, JPA, etc.), logrando independencia tecnológica y facilitando las pruebas unitarias.

## 🏗️ Arquitectura Hexagonal

El proyecto está organizado siguiendo los principios de la arquitectura hexagonal (Ports & Adapters):

### Estructura de Paquetes

```
com.example.inmemoryeventsapi/
├── dominio/                          # Núcleo de negocio (sin dependencias externas)
│   ├── model/                        # Entidades de dominio puras
│   │   ├── Event.java
│   │   ├── Venue.java
│   │   ├── Page.java                 # Clase de dominio para paginación
│   │   └── Pageable.java             # Clase de dominio para parámetros de paginación
│   ├── ports/
│   │   ├── in/                       # Puertos de entrada (casos de uso)
│   │   │   ├── CrearEventoUseCase.java
│   │   │   ├── ActualizarEventoUseCase.java
│   │   │   ├── EliminarEventoUseCase.java
│   │   │   ├── ObtenerEventoUseCase.java
│   │   │   └── ListarEventosUseCase.java
│   │   └── out/                      # Puertos de salida (repositorios)
│   │       ├── EventoRepositoryPort.java
│   │       └── VenueRepositoryPort.java
│   └── exception/                     # Excepciones de dominio
│       ├── BadRequestException.java
│       ├── ConflictException.java
│       └── NotFoundException.java
│
├── aplicacion/                       # Capa de aplicación
│   └── usecase/                      # Implementación de casos de uso
│       ├── EventoUseCaseImpl.java
│       └── VenueUseCaseImpl.java
│
└── infraestructura/                  # Adaptadores (frameworks externos)
    ├── adapters/
    │   ├── in/                       # Adaptadores de entrada
    │   │   └── web/                  # Adaptador REST
    │   │       ├── EventoRestAdapter.java
    │   │       ├── VenueRestAdapter.java
    │   │       ├── GlobalExceptionHandler.java
    │   │       ├── dto/               # DTOs para la API
    │   │       └── mapper/            # Mappers DTO ↔ Dominio
    │   └── out/                      # Adaptadores de salida
    │       └── jpa/                  # Adaptador JPA
    │           ├── EventoJpaAdapter.java
    │           ├── VenueJpaAdapter.java
    │           ├── entity/            # Entidades JPA
    │           ├── repository/        # Repositorios JPA
    │           └── mapper/            # Mappers Entity ↔ Dominio
    └── config/                       # Configuración de Spring
        └── BeanConfiguration.java
```

### Principios Aplicados

1. **Independencia Tecnológica**: El dominio no tiene dependencias de Spring, JPA u otros frameworks.
2. **Separación de Responsabilidades**: Cada capa tiene una responsabilidad clara.
3. **Inversión de Dependencias**: El dominio define interfaces (puertos) que son implementadas por la infraestructura.
4. **Equivalencia Funcional**: La funcionalidad se mantiene igual antes y después del refactor.

### Flujo de Datos

```
Cliente HTTP
    ↓
EventoRestAdapter (Adaptador de Entrada)
    ↓
EventoUseCaseImpl (Caso de Uso)
    ↓
EventoRepositoryPort (Puerto)
    ↓
EventoJpaAdapter (Adaptador de Salida)
    ↓
EventJpaRepository (JPA)
    ↓
Base de Datos
```

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **MapStruct 1.5.5** (mapeo entre capas)
- **Lombok**
- **SpringDoc OpenAPI** (documentación de API)

## 📦 Dependencias Principales

- `spring-boot-starter-web`: Framework web
- `spring-boot-starter-data-jpa`: Persistencia JPA
- `mapstruct`: Mapeo entre objetos
- `lombok`: Reducción de boilerplate
- `h2`: Base de datos en memoria
- `springdoc-openapi`: Documentación Swagger/OpenAPI

## 🚀 Ejecución

### Requisitos

- Java 17 o superior
- Maven 3.6+

### Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Documentación de API

Una vez ejecutada la aplicación, la documentación Swagger está disponible en:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 📡 Endpoints

### Eventos

- `GET /events` - Listar eventos (con paginación y filtros)
- `GET /events/{id}` - Obtener evento por ID
- `POST /events` - Crear evento
- `PUT /events/{id}` - Actualizar evento
- `DELETE /events/{id}` - Eliminar evento

### Venues

- `GET /venues` - Listar venues
- `GET /venues/{id}` - Obtener venue por ID
- `POST /venues` - Crear venue
- `PUT /venues/{id}` - Actualizar venue
- `DELETE /venues/{id}` - Eliminar venue

## 🔄 Mapeo con MapStruct

El proyecto utiliza MapStruct para realizar la conversión entre:

1. **DTO ↔ Dominio**: En los adaptadores REST (`EventoDTOMapper`, `VenueDTOMapper`)
2. **Entity ↔ Dominio**: En los adaptadores JPA (`EventoMapper`, `VenueMapper`)

Esto garantiza que:
- El dominio permanece puro (sin anotaciones JPA)
- Las conversiones son type-safe y eficientes
- El código de mapeo se genera en tiempo de compilación

## ✅ Criterios de Aceptación Cumplidos

- ✅ La aplicación mantiene el mismo comportamiento funcional que antes del refactor
- ✅ El dominio está completamente desacoplado de frameworks o tecnología de persistencia
- ✅ Se evidencia el uso correcto de puertos y adaptadores
- ✅ MapStruct realiza la conversión entre entidad y dominio
- ✅ La API REST continúa funcionando sin ruptura de endpoints
- ✅ La documentación del proyecto refleja la nueva arquitectura

## 🧪 Pruebas

```bash
# Ejecutar todas las pruebas
mvn test
```

## 📝 Notas de Implementación

### Paginación Independiente

Para mantener el dominio libre de dependencias de Spring, se crearon clases de dominio para paginación:
- `dominio.model.Page<T>`: Representa una página de resultados
- `dominio.model.Pageable`: Representa parámetros de paginación

Los adaptadores (REST y JPA) se encargan de convertir entre estas clases de dominio y las clases de Spring Data (`org.springframework.data.domain.Page` y `Pageable`).

### Manejo de Excepciones

Las excepciones de dominio (`NotFoundException`, `BadRequestException`, `ConflictException`) son capturadas por el `GlobalExceptionHandler` en la capa de infraestructura web y convertidas a respuestas HTTP apropiadas.

## 📚 Referencias

- [Arquitectura Hexagonal (Alistair Cockburn)](https://alistair.cockburn.us/hexagonal-architecture/)
- [Ports & Adapters Pattern](https://www.hexagonalarchitecture.net/)
- [MapStruct Documentation](https://mapstruct.org/)

## 👥 Autor

Proyecto desarrollado como parte de la implementación de arquitectura hexagonal con equivalencia funcional.
