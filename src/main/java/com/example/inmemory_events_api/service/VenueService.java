package com.example.inmemory_events_api.service;

import com.example.inmemoryeventsapi.dto.VenueDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VenueService {
    private final List<VenueDTO> venues = new ArrayList<>();

    public List<VenueDTO> findAll() {
        return venues;
    }
}