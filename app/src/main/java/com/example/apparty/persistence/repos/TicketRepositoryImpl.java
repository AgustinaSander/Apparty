package com.example.apparty.persistence.repos;

import android.content.Context;

import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.TicketDAO;
import com.example.apparty.persistence.room.entities.TicketEntity;
import com.example.apparty.persistence.room.mappers.TicketMapper;

import java.util.List;

public class TicketRepositoryImpl implements TicketRepository{

    TicketDAO ticketDAO;

    public TicketRepositoryImpl(Context context){
        RoomDB db = RoomDB.getInstance(context);
        this.ticketDAO = db.ticketDAO();
    }
    @Override
    public List<Ticket> getAllTickets() {
        return TicketMapper.fromEntityList(ticketDAO.getAllTickets());
    }

    @Override
    public Ticket getTicketById(int id) {
        return TicketMapper.fromEntity(ticketDAO.getTicket(id));
    }

    @Override
    public long insertTicket(Ticket ticket) {

        long idTicket = ticketDAO.insertTicket(TicketMapper.toEntity(ticket));
        return idTicket;
    }


    @Override
    public void deleteTicket(Ticket ticket) {
        TicketEntity ticketEntity = TicketMapper.toEntity(ticket);
        ticketEntity.setId(ticket.getId());
        RoomDB.EXECUTOR_DB.execute(
                () -> ticketDAO.deleteTicket(ticketEntity)
        );
    }

    @Override
    public void updateTicket(Ticket ticket) {
        TicketEntity ticketEntity = TicketMapper.toEntity(ticket);
        ticketEntity.setId(ticket.getId());
        RoomDB.EXECUTOR_DB.execute(
                () -> ticketDAO.updateTicket(ticketEntity)
        );
    }
}

