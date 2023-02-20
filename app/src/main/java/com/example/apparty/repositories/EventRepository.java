package com.example.apparty.repositories;

import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    DresscodeRepository dresscodeRepository = new DresscodeRepository();

    public EventRepository(){}

    private List<Event> _EVENTS = List.of(
            new Event(1, "Fiesta en Salones del puerto", getAddress(), getStock(), LocalDate.of(2023, 3, 15), LocalTime.of(17,30), dresscodeRepository.getDresscodes().get(0), getUser(), getComments()),
            new Event(2, "Poolparty en ATE", getAddress(), getStock(), LocalDate.of(2023, 3, 20), LocalTime.of(17,30), dresscodeRepository.getDresscodes().get(0), getUser(), getComments()),
            new Event(3, "Fiesta en la playa", getAddress(), getStock(), LocalDate.now(), LocalTime.of(17,30), dresscodeRepository.getDresscodes().get(1), getUser(), getComments())
    );

    private User getUser() {
        return new User(1,"Agustina","Sander","42331387","asander00@hotmail.com","Hola");
    }

    private String getComments(){
        return "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas";
    }

    private List<Ticket> getStock() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(1, "Entrada basica", 10, 10, 1500));
        tickets.add(new Ticket(2, "Entrada vip", 5, 5, 1800));
        tickets.add(new Ticket(3, "Entrada vip + consumicion", 2, 2, 2000));

        return tickets;
    }

    private Address getAddress(){
        return new Address(1,"Juan Jose Castelli 1621", "Argentina", "Santa Fe", "Rosario");
    }

    public List<Event> getEvents(){
        return _EVENTS;
    }

    public List<Event> findByDresscodes(List<Integer> dressCodeList) {
        List<Event> events = new ArrayList<>();
        getEvents().stream().forEach(e -> {
            if(dressCodeList.contains(e.getDressCode().getId())){
                events.add(e);
            }
        });
        return events;
    }
}
