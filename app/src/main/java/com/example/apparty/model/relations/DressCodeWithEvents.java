package com.example.apparty.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;

import java.util.List;

public class DressCodeWithEvents {
    @Embedded public DressCode dressCode;
    @Relation(parentColumn = "id", entityColumn = "idDressCode", entity = Event.class)
    List<Event> eventList;
}
