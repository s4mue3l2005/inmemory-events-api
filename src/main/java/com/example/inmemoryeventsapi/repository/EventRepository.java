package com.example.inmemoryeventsapi.repository;

import com.example.inmemoryeventsapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}