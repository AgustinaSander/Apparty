package com.example.apparty.persistence.repos;

import android.content.Context;

import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.TicketDAO;
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
    public void insertTicket(Ticket ticket) {
        RoomDB.EXECUTOR_DB.execute(
                () -> ticketDAO.insertTicket(TicketMapper.toEntity(ticket))
        );
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        RoomDB.EXECUTOR_DB.execute(
                () -> ticketDAO.deleteTicket(TicketMapper.toEntity(ticket))
        );
    }

    @Override
    public void updateTicket(Ticket ticket) {
        RoomDB.EXECUTOR_DB.execute(
                () -> ticketDAO.updateTicket(TicketMapper.toEntity(ticket))
        );
    }
}

