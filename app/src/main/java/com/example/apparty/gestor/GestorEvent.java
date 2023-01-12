package com.example.apparty.gestor;

import com.example.apparty.model.Event;
import com.example.apparty.repo.EventRepository;

import java.util.List;

public class GestorEvent {

    private static GestorEvent gestorEvent;
    private EventRepository eventRepository = new EventRepository();
    private List<Event> listEvents;

    private GestorEvent(){
        listEvents = eventRepository.listAllEvents();
    }

    public static GestorEvent getInstance(){
        if(gestorEvent == null){
            gestorEvent = new GestorEvent();
        }
        return gestorEvent;
    }

    public List<Event> getEvents(){
        return this.listEvents;
    }

}
