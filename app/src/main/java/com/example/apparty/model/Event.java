package com.example.apparty.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {
    private int id;
    private String name;
    private Address address;
    private List<Ticket> tickets;
    private LocalDate date;
    private LocalTime time;
    private DressCode dressCode;
    private User organizer;
    private String comments;


    public Event(int id, String name, Address address, Date date, Time time, DressCode dressCode, User user, String comments){
        this.id = id;
        this.name = name;
    }

    public Event(int id, String name, List<Ticket> tickets, LocalDate date){
        this.id = id;
        this.name = name;
        this.tickets = tickets;
        this.date = date;
    }

    public Event(String name, Address address, List<Ticket> tickets, LocalDate date, LocalTime time, DressCode dressCode, User organizer, String comments) {
        this.name = name;
        this.address = address;
        this.tickets = tickets;
        this.date = date;
        this.time = time;
        this.dressCode = dressCode;
        this.organizer = organizer;
        this.comments = comments;
    }
}

