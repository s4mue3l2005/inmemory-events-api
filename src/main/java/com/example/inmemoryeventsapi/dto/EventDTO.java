package com.example.inmemoryeventsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String date;
    private Long venueId;
    private Integer capacity;
    private Double price;
}