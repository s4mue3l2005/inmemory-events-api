package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository;

import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para VenueEntity.
 */
public interface VenueJpaRepository extends JpaRepository<VenueEntity, Long> {
}
