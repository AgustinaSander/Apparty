package com.example.apparty.gestores;

import android.util.Log;

import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Stock;
import com.example.apparty.repositories.DresscodeRepository;
import com.example.apparty.repositories.EventRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorEvent {
    private static GestorEvent gestorEvent;
    private DresscodeRepository dresscodeRepository = new DresscodeRepository();
    private EventRepository eventRepository = new EventRepository();
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
        List<Event> events = getEventList();

        events = events.stream().filter(e -> e.getName().toLowerCase().contains(wordsFilter.toLowerCase()))
                .collect(Collectors.toList());

        //PROBAR FILTROS
        if(filters.getDressCodeList().size() > 0){
            List<DressCode> dresscodes = dresscodeRepository.findAllByIds(filters.getDressCodeList());
            events = events.stream().filter(e -> dresscodes.contains(e.getDressCode()))
                    .collect(Collectors.toList());;
        }
        if(filters.getFromDate() != null){
            events = events.stream()
                    .filter(e -> filters.getFromDate().isBefore(e.getDate()) || filters.getFromDate().isEqual(e.getDate()))
                    .collect(Collectors.toList());
        }
        if(filters.getToDate() != null){
            events = events.stream()
                    .filter(e -> filters.getToDate().isAfter(e.getDate()) || filters.getToDate().isEqual(e.getDate()))
                    .collect(Collectors.toList());
        }
        if(filters.getMinPrice() != 0){
            events = events.stream().filter(e -> hasMoreExpensiveTickets(e.getTickets(), filters.getMinPrice()))
                    .collect(Collectors.toList());
        }
        if(filters.getMaxPrice() != 0){
            events = events.stream().filter(e -> hasCheaperTickets(e.getTickets(), filters.getMaxPrice()))
                    .collect(Collectors.toList());
        }
        return events;
    }

    private boolean hasMoreExpensiveTickets(List<Stock> tickets, double minPrice) {
        Optional<Stock> hasMoreExpensiveTickets = tickets.stream()
                .filter(t -> t.getPrice() >= minPrice && t.getAvailableQuantity() > 0)
                .findAny();
        return hasMoreExpensiveTickets.isPresent();
    }

    private boolean hasCheaperTickets(List<Stock> tickets, double maxPrice) {
        Optional<Stock> hasCheaperTickets = tickets.stream()
                .filter(t -> t.getPrice() <= maxPrice && t.getAvailableQuantity() > 0)
                .findAny();
        return hasCheaperTickets.isPresent();
    }
}
