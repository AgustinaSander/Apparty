package com.example.apparty.model.relations;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;

import java.util.List;

public class EventStock {
    @Embedded public Event event;
    @Relation(parentColumn = "id_event", entityColumn = "idEvent", entity = Stock.class)
    List<Stock> stockList;

}
