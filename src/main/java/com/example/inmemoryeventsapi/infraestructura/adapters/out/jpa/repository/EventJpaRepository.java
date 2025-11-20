package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository;

import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * Repositorio JPA para EventEntity.
 */
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    boolean existsByName(String name);

    @Query("SELECT e FROM EventEntity e WHERE " +
            "(:city IS NULL OR e.city = :city) AND " +
            "(:category IS NULL OR e.category = :category) AND " +
            "(:fechaInicio IS NULL OR e.date >= :fechaInicio)")
    Page<EventEntity> findByFilters(
            @Param("city") String city,
            @Param("category") String category,
            @Param("fechaInicio") LocalDate fechaInicio,
            Pageable pageable);
}
