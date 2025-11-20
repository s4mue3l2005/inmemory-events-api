package com.example.inmemoryeventsapi.dominio.model;

/**
 * Modelo de dominio puro para Venue.
 * Sin dependencias de frameworks (JPA, Spring, etc.)
 */
public class Venue {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;

    public Venue() {
    }

    public Venue(Long id, String name, String location, Integer capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
