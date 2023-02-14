package com.example.apparty.persistence.repos;

import com.example.apparty.model.Event;
import com.example.apparty.persistence.room.daos.AddressDAO;
import com.example.apparty.persistence.room.daos.EventDAO;
import com.example.apparty.persistence.room.mappers.EventMapper;

import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    EventDAO dao;
    AddressDAO addressDAO;


    public EventRepositoryImpl(EventDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Event> getAllEvents() {
        return EventMapper.fromEntityList(dao.getAllEvents());
    }

    @Override
    public Event getEventById(int id) {
        return EventMapper.fromEntity(dao.getEvent(id));
    }

    @Override
    public void insertEvent(Event event) {
        dao.insertEvent(EventMapper.toEntity(event));
    }

    @Override
    public void deleteEvent(Event event) {
        dao.deleteEvent(EventMapper.toEntity(event));
    }


}
