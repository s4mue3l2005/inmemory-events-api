package com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDTO {
    private Long id;

    @NotBlank(message = "El nombre del venue es requerido")
    private String name;

    @NotBlank(message = "La ubicación es requerida")
    private String location;

    @NotNull(message = "La capacidad es requerida")
    private Integer capacity;
}
