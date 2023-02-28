package com.example.apparty.persistence.repos;

import com.example.apparty.model.Event;

import java.util.List;

public interface EventRepository {

    List<Event> getAllEvents();

    List<Event> getAllEventsSorted();

    Event getEventById(int id);
    void insertEvent(Event event);
    void deleteEvent(Event event);
    void updateEvent(Event event);
    List<Event> findByDresscodes(List<Integer> dressCodeList);


}
