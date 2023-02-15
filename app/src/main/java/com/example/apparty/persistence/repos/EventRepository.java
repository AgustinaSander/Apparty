package com.example.apparty.persistence.repos;

import android.content.Context;

import com.example.apparty.model.Event;
import com.example.apparty.persistence.room.RoomDB;

import java.util.List;

public interface EventRepository {

    List<Event> getAllEvents();
    Event getEventById(int id);
    void insertEvent(Event event);
    void deleteEvent(Event event);
    List<Event> findByDresscodes(List<Integer> dressCodeList);


}
