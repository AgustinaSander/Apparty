package com.example.apparty.persistence.room.mappers;

import android.util.Log;

import com.example.apparty.model.Event;
import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.room.entities.AddressEntity;
import com.example.apparty.persistence.room.entities.DressCodeEntity;
import com.example.apparty.persistence.room.entities.EventEntity;
import com.example.apparty.persistence.room.entities.TicketEntity;
import com.example.apparty.persistence.room.entities.UserEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventMapper {

    private EventMapper () {}

    public static EventEntity toEntity (Event event){
        Set<String> tickets = new HashSet<>();
        for (Ticket t: event.getTickets()){
            tickets.add(Integer.toString(t.getId()));
        }
        return new EventEntity(
                event.getName(),
                event.getAddress().getId(),
                tickets,
                event.getDate().toString(),
                event.getTime().toString(),
                event.getDressCode().getId(),
                event.getOrganizer().getId(),
                event.getComments()
        );
    }

    public static Event fromEntity (EventEntity event, AddressEntity address, List<TicketEntity> tickets, DressCodeEntity dressCode, UserEntity user){
        Log.i("Event List", event.getDate());
        LocalDate localDate = LocalDate.parse(event.getDate());
        LocalTime localTime = LocalTime.parse(event.getTime());

        return new Event(
                event.getId(),
                event.getName(),
                AddressMapper.fromEntity(address),
                TicketMapper.fromEntityList(tickets),
                localDate,
                localTime,
                DressCodeMapper.fromEntity(dressCode),
                UserMapper.fromEntity(user),
                event.getComments()
        );
    }

    public static List<EventEntity> toEntityList (List<Event> events){

        List<EventEntity> eventList = new ArrayList<>();

        for (Event e : events) {
            eventList.add(EventMapper.toEntity(e));
        }
        return eventList;
    }
}