package com.example.apparty.persistence.repos;

import android.content.Context;

import androidx.room.Room;

import com.example.apparty.model.Event;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.AddressDAO;
import com.example.apparty.persistence.room.daos.EventDAO;
import com.example.apparty.persistence.room.mappers.EventMapper;

import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    EventDAO dao;
    AddressDAO addressDAO;


    public EventRepositoryImpl(Context context){
        RoomDB db = RoomDB.getInstance(context);
        this.dao = db.eventDAO();
    }
    /*
    public EventRepositoryImpl(EventDAO dao) {
        this.dao = dao;
    }*/

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

    public List<Event> findByDresscodes(List<Integer> dressCodeList){
        List<Event> eventList = new ArrayList<>();
        for (Integer dc : dressCodeList){
            eventList.addAll(EventMapper.fromEntityList(dao.getEventByDressCode(dc)));
        }
        return eventList;
    }
/*
    public List<Event> findByDresscodes(List<Integer> dressCodeList) {
        List<Event> events = new ArrayList<>();
        getEvents().stream().forEach(e -> {
            if(dressCodeList.contains(e.getDressCode().getId())){
                events.add(e);
            }
        });
        return events;
    }*/

}
