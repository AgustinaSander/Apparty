package com.example.apparty.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.apparty.model.Address;
import com.example.apparty.model.Event;

import java.util.List;

public class AddressWithEvents {
    @Embedded public Address address;
    @Relation(parentColumn = "id", entityColumn = "idAddress", entity = Event.class)
    List<Event> eventList;
}
