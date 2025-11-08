package com.example.inmemoryeventsapi.repository;


import com.example.inmemoryeventsapi.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}