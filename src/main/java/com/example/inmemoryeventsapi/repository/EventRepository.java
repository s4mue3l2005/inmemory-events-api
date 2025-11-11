package com.example.inmemoryeventsapi.repository;

import com.example.inmemoryeventsapi.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT e FROM Event e WHERE " +
           "(:city IS NULL OR e.city = :city) AND " +
           "(:category IS NULL OR e.category = :category) AND " +
           "(:fechaInicio IS NULL OR e.date >= :fechaInicio)")
    Page<Event> findByFilters(
            @Param("city") String city,
            @Param("category") String category,
            @Param("fechaInicio") LocalDate fechaInicio,
            Pageable pageable
    );
}