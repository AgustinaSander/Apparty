package com.example.apparty.model.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.apparty.model.Event;
import com.example.apparty.model.User;

import java.util.List;

public class UserWithEvents {
    @Embedded public User user;
    @Relation(parentColumn = "id", entityColumn = "idUserOrganizer", entity = Event.class)
    List<Event> eventList;
}
