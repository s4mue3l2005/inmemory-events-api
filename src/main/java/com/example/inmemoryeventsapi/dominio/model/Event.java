package com.example.inmemoryeventsapi.dominio.model;

import java.time.LocalDate;

/**
 * Modelo de dominio puro para Evento.
 * Sin dependencias de frameworks (JPA, Spring, etc.)
 */
public class Event {
    private Long id;
    private String name;
    private LocalDate date;
    private Long venueId;
    private Integer capacity;
    private Double price;
    private String category;
    private String city;

    public Event() {
    }

    public Event(Long id, String name, LocalDate date, Long venueId, Integer capacity,
            Double price, String category, String city) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.venueId = venueId;
        this.capacity = capacity;
        this.price = price;
        this.category = category;
        this.city = city;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
