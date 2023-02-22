package com.example.apparty.gestores;

import android.content.Context;

import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.repos.TicketRepositoryImpl;

public class GestorTicket {

    private static GestorTicket gestorTicket;
    private TicketRepositoryImpl ticketRepository;
    private Ticket ticketById;

    private GestorTicket (Context context){
        this.ticketRepository = new TicketRepositoryImpl(context);
    }


    public static GestorTicket getInstance(Context context){
        if(gestorTicket == null){
            gestorTicket = new GestorTicket(context);
        }
        return gestorTicket;
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
