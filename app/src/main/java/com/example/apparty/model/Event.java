package com.example.apparty.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
    private List<Stock> tickets;
    private LocalDate date;
    private LocalTime time;
    private DressCode dressCode;
    private User organizer;
    private String comments;

    public Event(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Event(int id, String name, List<Stock> tickets, LocalDate date){
        this.id = id;
        this.name = name;
        this.tickets = tickets;
        this.date = date;
    }
}
