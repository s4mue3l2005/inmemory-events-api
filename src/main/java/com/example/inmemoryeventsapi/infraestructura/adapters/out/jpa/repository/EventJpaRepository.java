package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository;

import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * Repositorio JPA para EventEntity.
 * 
 * Spring Data JPA proporciona automáticamente los métodos básicos al extender
 * JpaRepository:
 * - save(EventEntity), findById(Long), findAll(), existsById(Long),
 * deleteById(Long)
 * 
 * Este repositorio define métodos personalizados porque requiere lógica
 * específica:
 * - existsByName(String): Verificar existencia por nombre (para validar
 * duplicados)
 * - findByFilters(...): Búsqueda con filtros opcionales (ciudad, categoría,
 * fecha)
 */
public interface EventJpaRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {

        boolean existsByName(String name);

        @EntityGraph(attributePaths = "venue")
        @Query("SELECT e FROM EventEntity e WHERE " +
                        "(:city IS NULL OR e.city = :city) AND " +
                        "(:category IS NULL OR e.category = :category) AND " +
                        "(:fechaInicio IS NULL OR e.date >= :fechaInicio)")
        Page<EventEntity> findByFilters(
                        @Param("city") String city,
                        @Param("category") String category,
                        @Param("fechaInicio") LocalDate fechaInicio,
                        Pageable pageable);

        @Override
        @EntityGraph(attributePaths = "venue")
        Page<EventEntity> findAll(Pageable pageable);

        @Override
        @EntityGraph(attributePaths = "venue")
        Page<EventEntity> findAll(org.springframework.data.jpa.domain.Specification<EventEntity> spec,
                        Pageable pageable);
}
