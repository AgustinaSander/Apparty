package com.example.apparty.repositories;

import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;
import com.example.apparty.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    public EventRepository(){}
   /* private int id;
    private String name;
    private Address address;
    private List<Stock> tickets;
    private LocalDate date;
    private LocalTime time;
    private DressCode dressCode;
    private User organizer;
    private String comments;*/
    private List<Event> _EVENTS = List.of(
            new Event(1, "Fiesta en Salones del puerto", getAddress(), getStock(), LocalDate.of(2023, 1, 15), LocalTime.of(17,30), new DressCode(1,"Formal"), getUser(), getComments()),
            new Event(2, "Poolparty en ATE", getAddress(), getStock(), LocalDate.of(2023, 1, 20), LocalTime.of(17,30), new DressCode(1,"Formal"), getUser(), getComments()),
            new Event(3, "Fiesta en la playa", getAddress(), getStock(), LocalDate.of(2023, 1, 13), LocalTime.of(17,30), new DressCode(1,"Formal"), getUser(), getComments())
    );

    private User getUser() {
        return new User(1,"Agustina","Sander","42331387","asander00@hotmail.com","Hola");
    }

    private String getComments(){
        return "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas";
    }

    private List<Stock> getStock() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock(1, 10, 1500));

        return stocks;
    }

    private Address getAddress(){
        return new Address(1,"Juan Jose Castelli 1621", "Argentina", "Santa Fe", "Rosario");
    }

    public List<Event> getEvents(){
        return _EVENTS;
    }
}
