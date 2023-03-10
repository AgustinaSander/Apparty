package com.example.apparty.gestores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Filter;
import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.repos.DressCodeRepositoryImpl;
import com.example.apparty.persistence.repos.EventRepositoryImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorEvent {
    private static GestorEvent gestorEvent;
    private DressCodeRepositoryImpl dresscodeRepository;
    private EventRepositoryImpl eventRepository;
    private List<Event> eventList;
    private List<DressCode> dressCodeList;
    private Event eventById;

    public GestorEvent(Context context){}

    public static GestorEvent getInstance(Context context){
        if(gestorEvent == null){
            gestorEvent = new GestorEvent(context);
            gestorEvent.eventRepository = new EventRepositoryImpl(context);
            gestorEvent.dresscodeRepository = new DressCodeRepositoryImpl(context);
        }
        return gestorEvent;
    }

    public void insertEvent (Event event){
        eventRepository.insertEvent(event);
    }

    public List<DressCode> getDressCodeList(){
        Thread hilo1 = new Thread( () -> {
            this.dressCodeList = dresscodeRepository.getAllDressCodes();

        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.dressCodeList;
    }

    public List<Event> getEventList(){
        Thread hilo1 = new Thread( () -> {
            this.eventList = eventRepository.getAllEvents();
        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.eventList;
    }

    public List<Event> getFilteredEvents(String wordsFilter, Filter filters){
        List<Event> events = getEventList();

        //Me quedo solo con los eventos que no pasaron
        events = events.stream().filter(e -> e.getDate().isEqual(LocalDate.now()) || e.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        if(filters != null) {
            if (filters.getDressCodeList().size() > 0) {
                for (Integer dc: filters.getDressCodeList()){
                    events = events.stream()
                            .filter(event -> dc == event.getDressCode().getId())
                            .collect(Collectors.toList());
                }
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
        }

        events = events.stream().filter(e -> e.getName().toLowerCase().contains(wordsFilter.toLowerCase()))
                .collect(Collectors.toList());

        return events;
    }

    public boolean hasMoreExpensiveTickets(List<Ticket> tickets, double minPrice) {
        Optional<Ticket> hasMoreExpensiveTickets = tickets.stream()
                .filter(t -> t.getPrice() >= minPrice && t.getAvailableQuantity() > 0)
                .findAny();
        return hasMoreExpensiveTickets.isPresent();
    }

    public boolean hasCheaperTickets(List<Ticket> tickets, double maxPrice) {
        Optional<Ticket> hasCheaperTickets = tickets.stream()
                .filter(t -> t.getPrice() <= maxPrice && t.getAvailableQuantity() > 0)
                .findAny();
        return hasCheaperTickets.isPresent();
    }

    public Event getEventById(int idEvent) {
        Thread hilo1 = new Thread( () -> {
            eventById = eventRepository.getEventById(idEvent);
        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return eventById;
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

    public List<Event> getEventsOrganizedByUser(int idUser){
        return getEventList().stream().filter(e -> e.getOrganizer().getId() == idUser).collect(Collectors.toList());
    }

    public List<Event> getMostRecentEvents() {
        List<Event> events = getAllEventsSorted();

        //Me quedo con los eventos que no pasaron
        events = events.stream().filter(e -> e.getDate().isEqual(LocalDate.now()) || e.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());

        //Obtengo los 5 primeros eventos mas proximos
        return events.stream().limit(5).collect(Collectors.toList());
    }

    private List<Event> getAllEventsSorted() {
        Thread hilo1 = new Thread( () -> {
            this.eventList = eventRepository.getAllEventsSorted();
        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.eventList;
    }

    public void addPicture(Bitmap selectedImageBitmap){
        Event event = getEventById(1);

        updateEvent(event);
        event = getEventById(1);
    }

    public void updateEvent(Event event) {
        Thread hilo1 = new Thread( () -> {
            eventRepository.updateEvent(event);
        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
