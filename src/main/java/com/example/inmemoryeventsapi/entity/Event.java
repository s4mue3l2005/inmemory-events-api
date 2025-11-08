package com.example.inmemoryeventsapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String date;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
}