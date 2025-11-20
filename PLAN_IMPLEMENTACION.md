# Plan de Implementación - Catálogo Persistente con Validación y Paginación

## ✅ Implementación Completada

Todas las tareas han sido implementadas. A continuación se detalla la estructura de ramas y commits sugerida:

---

## Estructura de Ramas y Commits

### TASK 1: Persistencia con JPA
**Rama:** `task1/jpa-persistence`

**Commits:**
1. `feat: actualizar entidades EventEntity y VenueEntity con campos completos y constraints`
   - Agregados campos: capacity, price, category, city en Event
   - Agregado constraint único para nombre de evento
   - Actualizado tipo de fecha a LocalDate
   - Agregadas anotaciones @Column con nullable

2. `feat: migrar EventService para usar EventRepository con JPA`
   - Reemplazado ArrayList por EventRepository
   - Implementados métodos toEntity() y toDTO()
   - Agregada validación de duplicados por nombre
   - Agregado soporte para conversión de fechas

3. `feat: migrar VenueService para usar VenueRepository con JPA`
   - Reemplazado ArrayList por VenueRepository
   - Implementados métodos toEntity() y toDTO()
   - Agregado manejo de transacciones

4. `feat: configurar H2 database en application.properties`
   - Configurada URL de H2 en memoria
   - Habilitada consola H2 para desarrollo
   - Configurado JPA con ddl-auto=update

5. `feat: agregar métodos de búsqueda en EventRepository`
   - Agregado findByName() y existsByName()

---

### TASK 2: Validaciones
**Rama:** `task2/validations`

**Commits:**
1. `feat: agregar validaciones @Valid, @NotBlank, @Size en DTOs`
   - EventDTO: @NotBlank, @Size para name
   - EventDTO: @Min para capacity, @DecimalMin para price
   - VenueDTO: @NotBlank, @Size, @NotNull, @Min

2. `feat: agregar validación de fecha futura en servicio`
   - Implementada validación de fecha futura en parseDate()
   - Mensaje de error descriptivo

3. `feat: actualizar controladores para usar @Valid`
   - Agregado @Valid en métodos POST y PUT
   - Removida validación manual en controladores

4. `feat: agregar mensajes de error descriptivos en payloads`
   - Todos los mensajes de validación en español
   - Mensajes claros y específicos por campo

---

### TASK 3: Paginación y Filtros
**Rama:** `task3/pagination-filters`

**Commits:**
1. `feat: implementar paginación en GET /events con Pageable`
   - Agregado método findAll(Pageable) en EventService
   - Actualizado EventController para aceptar Pageable
   - Configurado @PageableDefault con size=10, sort=date

2. `feat: agregar filtros opcionales por ciudad, categoría y fechaInicio`
   - Agregado método findByFilters() en EventRepository con @Query
   - Implementado findByFilters() en EventService
   - Actualizado EventController para aceptar parámetros de filtro

3. `feat: agregar soporte para ordenamiento (sort) en endpoints`
   - Ordenamiento por defecto por fecha
   - Soporte para sort personalizado mediante Pageable

---

### TASK 4: Manejo de Errores
**Rama:** `task4/error-handling`

**Commits:**
1. `feat: crear ConflictException para manejar errores 409`
   - Creada clase ConflictException
   - Actualizado EventService para usar ConflictException en duplicados

2. `feat: mejorar GlobalExceptionHandler con manejo de ValidationException`
   - Agregado handler para MethodArgumentNotValidException
   - Respuesta con detalles de errores de validación por campo

3. `feat: agregar manejo de errores 400, 404 y 409 con mensajes claros`
   - Handler para NotFoundException (404)
   - Handler para BadRequestException (400)
   - Handler para ConflictException (409)
   - Todos con estructura JSON consistente

---

## Resumen de Cambios Implementados

### Archivos Modificados:

**Entidades:**
- `Event.java` - Agregados campos y constraints
- `Venue.java` - Mejorada estructura con anotaciones JPA

**Repositorios:**
- `EventRepository.java` - Agregados métodos de búsqueda y filtros con paginación
- `VenueRepository.java` - Sin cambios (ya extendía JpaRepository)

**Servicios:**
- `EventService.java` - Migrado completamente a JPA con paginación y filtros
- `VenueService.java` - Migrado completamente a JPA

**DTOs:**
- `EventDTO.java` - Agregadas validaciones y campos category, city
- `VenueDTO.java` - Agregadas validaciones

**Controladores:**
- `EventController.java` - Agregada paginación, filtros y @Valid
- `VenueController.java` - Agregado @Valid

**Excepciones:**
- `ConflictException.java` - Nueva excepción para errores 409
- `GlobalExceptionHandler.java` - Mejorado con handlers para validación y conflictos

**Configuración:**
- `application.properties` - Configuración completa de H2 y JPA

---

## Comandos Git Sugeridos

```bash
# TASK 1
git checkout -b task1/jpa-persistence
git add src/main/java/com/example/inmemoryeventsapi/entity/
git commit -m "feat: actualizar entidades EventEntity y VenueEntity con campos completos y constraints"
git add src/main/java/com/example/inmemoryeventsapi/service/EventService.java
git commit -m "feat: migrar EventService para usar EventRepository con JPA"
git add src/main/java/com/example/inmemoryeventsapi/service/VenueService.java
git commit -m "feat: migrar VenueService para usar VenueRepository con JPA"
git add src/main/resources/application.properties
git commit -m "feat: configurar H2 database en application.properties"
git add src/main/java/com/example/inmemoryeventsapi/repository/EventRepository.java
git commit -m "feat: agregar métodos de búsqueda en EventRepository"

# TASK 2
git checkout -b task2/validations
git add src/main/java/com/example/inmemoryeventsapi/dto/
git commit -m "feat: agregar validaciones @Valid, @NotBlank, @Size en DTOs"
git add src/main/java/com/example/inmemoryeventsapi/service/EventService.java
git commit -m "feat: agregar validación de fecha futura en servicio"
git add src/main/java/com/example/inmemoryeventsapi/controller/
git commit -m "feat: actualizar controladores para usar @Valid"

# TASK 3
git checkout -b task3/pagination-filters
git add src/main/java/com/example/inmemoryeventsapi/repository/EventRepository.java
git commit -m "feat: agregar método findByFilters con @Query en EventRepository"
git add src/main/java/com/example/inmemoryeventsapi/service/EventService.java
git commit -m "feat: implementar paginación y filtros en EventService"
git add src/main/java/com/example/inmemoryeventsapi/controller/EventController.java
git commit -m "feat: agregar paginación y filtros en GET /events"

# TASK 4
git checkout -b task4/error-handling
git add src/main/java/com/example/inmemoryeventsapi/exception/ConflictException.java
git commit -m "feat: crear ConflictException para manejar errores 409"
git add src/main/java/com/example/inmemoryeventsapi/service/EventService.java
git commit -m "feat: usar ConflictException para duplicados en EventService"
git add src/main/java/com/example/inmemoryeventsapi/exception/GlobalExceptionHandler.java
git commit -m "feat: mejorar GlobalExceptionHandler con manejo de ValidationException y ConflictException"
```

---

## Pruebas Recomendadas

1. **TASK 1**: Verificar que los datos se persisten en H2
2. **TASK 2**: Probar validaciones con datos inválidos
3. **TASK 3**: Probar paginación y filtros en GET /events
4. **TASK 4**: Probar respuestas de error 400, 404, 409

---

## Notas Importantes

- La consola H2 está disponible en: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:eventsdb`
- Usuario: `sa`
- Contraseña: (vacía)
- El ordenamiento por defecto es por fecha (`sort=date`)
- Los filtros son opcionales y se pueden combinar

