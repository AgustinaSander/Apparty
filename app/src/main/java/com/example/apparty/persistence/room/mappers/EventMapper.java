package com.example.apparty.persistence.room.mappers;

import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.persistence.room.daos.AddressDAO;
import com.example.apparty.persistence.room.daos.DressCodeDAO;
import com.example.apparty.persistence.room.daos.UserDAO;
import com.example.apparty.persistence.room.entities.AddressEntity;
import com.example.apparty.persistence.room.entities.DressCodeEntity;
import com.example.apparty.persistence.room.entities.EventEntity;
import com.example.apparty.persistence.room.entities.UserEntity;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EventMapper {

    private EventMapper () {}

    public static EventEntity toEntity (Event event){
        return new EventEntity(
                event.getId(),
                event.getName(),
                event.getAddress().getId(),
                event.getDate(),
                event.getTime(),
                event.getDressCode().getId(),
                event.getOrganizer().getId(),
                event.getComments()
        );
    }

    public static Event fromEntity (EventEntity event, AddressEntity address, DressCodeEntity dressCode, UserEntity user){


        return new Event(
                event.getId(),
                event.getName(),
                AddressMapper.fromEntity(address),
                new Date(event.getDate()),
                new Time(event.getTime()),
                DressCodeMapper.fromEntity(dressCode),
                UserMapper.fromEntity(user),
                event.getComments()
        );
    }

    public static List<Event> fromEntityList (List<EventEntity> events){

        List<Event> eventList = new ArrayList<>();

        for (EventEntity e : events) {
            eventList.add(EventMapper.fromEntity(e));
        }
        return eventList;
    }

    public static List<EventEntity> toEntityList (List<Event> events){

        List<EventEntity> eventList = new ArrayList<>();

        for (Event e : events) {
            eventList.add(EventMapper.toEntity(e));
        }
        return eventList;
    }
}


/*
    Entity
    private int id;
    private String name;
    @ColumnInfo(name = "id_address")
    private int idAddress;
    private Date date;
    private Long time;
    @ColumnInfo(name = "id_dressCode")
    private int idDressCode;
    @ColumnInfo(name = "id_user")
    private int idUserOrganizer;
    private String comments;

    Model
 */