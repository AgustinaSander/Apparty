package com.example.apparty.repo;

import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;
import com.example.apparty.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventRepository {

    public EventRepository(){

    }
    private List<Event> _EVENTS = List.of(
      new Event(1, "PoolParty en ATE"),
      new Event(2, "Fiesta en Salones del Puerto")
    );

    public List<Event> listAllEvents(){
        return _EVENTS;
    }
}
/*
    private int id;
    private String name;
    private Address address;
    private List<Stock> tickets;
    private LocalDate date;
    private LocalTime time;
    private DressCode dressCode;
    private User organizer;
    private String comments;*/