package com.example.apparty.gestores;

import android.content.Context;

import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.repos.TicketRepositoryImpl;

import java.util.List;

public class GestorTicket {

    private static GestorTicket gestorTicket;
    private TicketRepositoryImpl ticketRepository;
    private Ticket ticketById;
    private long idTicketInserted;
    private List<Ticket> ticketList;

    private GestorTicket (Context context){
        this.ticketRepository = new TicketRepositoryImpl(context);
    }


    public static GestorTicket getInstance(Context context){
        if(gestorTicket == null){
            gestorTicket = new GestorTicket(context);
        }
        return gestorTicket;
    }

    public Ticket saveTicket(Ticket ticket){

        Thread hilo1 = new Thread( () -> {
            idTicketInserted = ticketRepository.insertTicket(ticket);
        });
        hilo1.start();
        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ticket.setId((int) idTicketInserted);
        return ticket;
    }

    public List<Ticket> getAllTickets(){
        Thread hilo1 = new Thread( () -> {
            this.ticketList = ticketRepository.getAllTickets();

        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.ticketList;
    }

    public Ticket getTicketById(int idTicket){
        Thread hilo1 = new Thread( () -> {
            ticketById = ticketRepository.getTicketById(idTicket);
        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ticketById;
    }

    public void updateTicket(Ticket ticket) {

        ticketRepository.updateTicket(ticket);

    }
}
