package com.example.apparty.gestores;

import android.text.method.TimeKeyListener;
import android.util.Log;

import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.repos.EventRepositoryImpl;
import com.example.apparty.repositories.DresscodeRepository;
import com.example.apparty.repositories.EventRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorEvent {
    private static GestorEvent gestorEvent;
    private DresscodeRepository dresscodeRepository = new DresscodeRepository();
    private com.example.apparty.repositories.EventRepository eventRepository = new EventRepository();


    private List<Event> eventList;
    private List<DressCode> dressCodeList;

    private GestorEvent(){
        eventList = eventRepository.getEvents();
        dressCodeList = dresscodeRepository.getDresscodes();

    }

    public static GestorEvent getInstance(){
        if(gestorEvent == null){
            gestorEvent = new GestorEvent();
        }

        return gestorEvent;
    }

    public List<DressCode> getDressCodeList(){
        return this.dressCodeList;
    }

    public List<Event> getEventList(){ return this.eventList; }

    public List<Event> getFilteredEvents(String wordsFilter, Filter filters){
        List<Event> events = new ArrayList<>();

        if(filters != null) {
            if (filters.getDressCodeList().size() > 0) {
                events = eventRepository.findByDresscodes(filters.getDressCodeList());
            }
            if (filters.getFromDate() != null && filters.getFromDate() != "" && filters.getFromDate().length() != 0) {
                LocalDate fromDate = LocalDate.parse(filters.getFromDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                events = events.stream()
                        .filter(e -> fromDate.isBefore(e.getDate()) || fromDate.isEqual(e.getDate()))
                        .collect(Collectors.toList());
            }
            if (filters.getToDate() != null && filters.getToDate() != ""&& filters.getToDate().length() != 0) {
                LocalDate toDate = LocalDate.parse(filters.getToDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                events = events.stream()
                        .filter(e -> toDate.isAfter(e.getDate()) || toDate.isEqual(e.getDate()))
                        .collect(Collectors.toList());
            }
            if (filters.getMinPrice() != 0) {
                events = events.stream().filter(e -> hasMoreExpensiveTickets(e.getTickets(), filters.getMinPrice()))
                        .collect(Collectors.toList());
            }
            if (filters.getMaxPrice() != 0) {
                events = events.stream().filter(e -> hasCheaperTickets(e.getTickets(), filters.getMaxPrice()))
                        .collect(Collectors.toList());
            }
        } else {
            events = getEventList();
        }

        events = events.stream().filter(e -> e.getName().toLowerCase().contains(wordsFilter.toLowerCase()))
                .collect(Collectors.toList());

        return events;
    }

    private boolean hasMoreExpensiveTickets(List<Ticket> tickets, double minPrice) {
        Optional<Ticket> hasMoreExpensiveTickets = tickets.stream()
                .filter(t -> t.getPrice() >= minPrice && t.getAvailableQuantity() > 0)
                .findAny();
        return hasMoreExpensiveTickets.isPresent();
    }

    private boolean hasCheaperTickets(List<Ticket> tickets, double maxPrice) {
        Optional<Ticket> hasCheaperTickets = tickets.stream()
                .filter(t -> t.getPrice() <= maxPrice && t.getAvailableQuantity() > 0)
                .findAny();
        return hasCheaperTickets.isPresent();
    }

    public Event getEventById(int idEvent) {
        List<Event> filteredEvents = getEventList().stream().filter(e -> e.getId() == idEvent).collect(Collectors.toList());
        return filteredEvents.size() > 0 ? filteredEvents.get(0) : null;
    }

    private List<Double> getPrices(){
        List<Event> events = getEventList();
        List<Ticket> ticket = new ArrayList<>();
        for(Event e: events){
            ticket.addAll(e.getTickets());
        }
        List<Double> prices = new ArrayList<>();
        ticket.stream().forEach(s -> prices.add(s.getPrice()));
        return prices;
    }

    public double getMinPrice() {
        return Collections.min(getPrices());
    }

    public double getMaxPrice() {
        return Collections.max(getPrices());
    }

    public Ticket getTicketByIdByEvent(int idTicket, int idEvent) {
        Event event = getEventById(idEvent);
        if(event != null){
            List<Ticket> ticketList = event.getTickets();
            List<Ticket> filteredTicket = ticketList.stream().filter(s -> s.getId()==idTicket).collect(Collectors.toList());
            return filteredTicket.size() > 0 ? filteredTicket.get(0) : null;
        }
        return null;
    }
}
