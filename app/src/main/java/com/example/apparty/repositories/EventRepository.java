package com.example.apparty.repositories;

import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    public EventRepository(){}

    private List<Event> _EVENTS = List.of(
            new Event(1, "Fiesta en Salones del puerto", getStock(), LocalDate.of(2023, 1, 15)),
            new Event(2, "Poolparty en ATE", getStock(), LocalDate.of(2023, 1, 20)),
            new Event(3, "Fiesta en la playa", getStock(), LocalDate.of(2023, 1, 13))
    );

    private List<Stock> getStock() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock(1, 10, 1500));

        return stocks;
    }

    public List<Event> getEvents(){
        return _EVENTS;
    }
}
