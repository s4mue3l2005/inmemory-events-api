package com.example.inmemory_events_api.service;

import com.example.inmemoryeventsapi.dto.EventDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final List<EventDTO> events = new ArrayList<>();

    public List<EventDTO> findAll() {
        return events;
    }
}
