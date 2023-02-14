package com.example.apparty.persistence.repos;

import com.example.apparty.model.Event;

import java.util.List;

public interface EventRepository {

    List<Event> getAllEvents();
    Event getEventById(int id);
    void insertEvent(Event event);
    void deleteEvent(Event event);
}