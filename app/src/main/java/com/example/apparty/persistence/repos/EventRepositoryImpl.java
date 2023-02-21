package com.example.apparty.persistence.repos;

import android.content.Context;

import com.example.apparty.model.Event;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.AddressDAO;
import com.example.apparty.persistence.room.daos.DressCodeDAO;
import com.example.apparty.persistence.room.daos.EventDAO;
import com.example.apparty.persistence.room.daos.TicketDAO;
import com.example.apparty.persistence.room.daos.UserDAO;
import com.example.apparty.persistence.room.entities.AddressEntity;
import com.example.apparty.persistence.room.entities.DressCodeEntity;
import com.example.apparty.persistence.room.entities.EventEntity;
import com.example.apparty.persistence.room.entities.TicketEntity;
import com.example.apparty.persistence.room.entities.UserEntity;
import com.example.apparty.persistence.room.mappers.EventMapper;

import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    private final EventDAO dao;
    private final AddressDAO addressDAO;
    private final TicketDAO ticketDAO;
    private final DressCodeDAO dressCodeDAO;
    private final UserDAO userDAO;

    public EventRepositoryImpl(Context context){

        RoomDB db = RoomDB.getInstance(context);
        this.dao = db.eventDAO();
        this.addressDAO = db.addressDAO();
        this.ticketDAO = db.ticketDAO();
        this.dressCodeDAO = db.dressCodeDAO();
        this.userDAO = db.userDAO();
    }
    @Override
    public List<Event> getAllEvents() {

        List<EventEntity> eventList = dao.getAllEvents();
        List<Event> events = new ArrayList<>();

        for (EventEntity e : eventList) {
            AddressEntity address = addressDAO.getAddress(e.getIdAddress());
            List<TicketEntity> tickets = new ArrayList<>();
            for (String t: e.getTickets()){
                tickets.add(ticketDAO.getTicket(Integer.parseInt(t)));
            }
            DressCodeEntity dressCode = dressCodeDAO.getDressCode(e.getIdDressCode());
            UserEntity user = userDAO.getUser(e.getIdUserOrganizer());
            events.add(EventMapper.fromEntity(e, address, tickets, dressCode, user));
        }

        return events;
    }

    @Override
    public Event getEventById(int id) {
        EventEntity event = dao.getEvent(id);
        AddressEntity address = addressDAO.getAddress(event.getIdAddress());
        List<TicketEntity> tickets = new ArrayList<>();
        for (String t: event.getTickets()){
            tickets.add(ticketDAO.getTicket(Integer.parseInt(t)));
        }
        DressCodeEntity dressCode = dressCodeDAO.getDressCode(event.getIdDressCode());
        UserEntity user = userDAO.getUser(event.getIdUserOrganizer());
        return EventMapper.fromEntity(event,address, tickets, dressCode, user);
    }

    @Override
    public void insertEvent(Event event) {

        RoomDB.EXECUTOR_DB.execute(

                () -> dao.insertEvent(EventMapper.toEntity(event))
        );
    }

    @Override
    public void deleteEvent(Event event) {

        RoomDB.EXECUTOR_DB.execute(

                () -> dao.deleteEvent(EventMapper.toEntity(event))
        );

    }

    public List<Event> findByDresscodes(List<Integer> dressCodeList){
        List<Event> eventList = new ArrayList<>();
        for (Integer dc : dressCodeList){

            List<EventEntity> eventEntities = dao.getEventByDressCode(dc);
            for (EventEntity e : eventEntities){
                AddressEntity address = addressDAO.getAddress(e.getIdAddress());
                DressCodeEntity dressCode = dressCodeDAO.getDressCode(e.getIdDressCode());
                List<TicketEntity> tickets = new ArrayList<>();
                for (String t: e.getTickets()){
                    tickets.add(ticketDAO.getTicket(Integer.parseInt(t)));
                }
                UserEntity user = userDAO.getUser(e.getIdUserOrganizer());
                eventList.add(EventMapper.fromEntity(e, address, tickets, dressCode, user));
            }
        }

        return eventList;
    }

}
