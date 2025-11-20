package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events", uniqueConstraints = {
        @UniqueConstraint(name = "uk_event_name", columnNames = "name")
})
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private VenueEntity venue;

    private Integer capacity;
    private Double price;

    private String category;
    private String city;
}
