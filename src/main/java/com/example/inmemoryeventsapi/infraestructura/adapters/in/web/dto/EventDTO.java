package com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;

    @NotBlank(message = "El nombre del evento es requerido")
    private String name;

    @NotNull(message = "La fecha del evento es requerida")
    private String date; // formato: yyyy-MM-dd

    private Long venueId;
    private Integer capacity;
    private Double price;
    private String category;
    private String city;
}
