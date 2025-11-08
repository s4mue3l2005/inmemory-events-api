package com.example.inmemoryeventsapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private int capacity;
}
