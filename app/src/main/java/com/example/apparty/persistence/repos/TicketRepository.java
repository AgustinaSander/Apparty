package com.example.apparty.persistence.repos;

import com.example.apparty.model.Ticket;

import java.util.List;

public interface TicketRepository {

    List<Ticket> getAllTickets();
    Ticket getTicketById(int id);
    void insertTicket(Ticket ticket);
    void deleteTicket(Ticket ticket);
    void updateTicket(Ticket ticket);

}
