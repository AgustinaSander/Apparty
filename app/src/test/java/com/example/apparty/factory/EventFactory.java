package com.example.apparty.factory;

import com.example.apparty.model.Event;
import com.example.apparty.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class EventFactory {
    private int maxQuantity = 3;
    private List<Event> eventList = new ArrayList<>();
    private List<Event> eventsWithOrganizer1 = new ArrayList<>();

    public EventFactory(){
        for(int i=1; i<maxQuantity; i++){
            Event event = new Event(i, "Evento "+i);
            event.setOrganizer(new UserFactory().getUser(1));
            eventsWithOrganizer1.add(event);
            eventList.add(event);
        }
        for(int i=maxQuantity; i<=maxQuantity*2; i++){
            Event event = new Event(i, "Evento "+i);
            event.setOrganizer(new UserFactory().getUser(2));
            eventList.add(event);
        }
    }

    public List<Event> getEventList(){
        return eventList;
    }

    public List<Event> getEventListWithOrganizer1(){
        return eventsWithOrganizer1;
    }
}
