package com.example.apparty.factory;

import com.example.apparty.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketFactory {
    private int maxQuantity = 10;
    private List<Ticket> ticketList = new ArrayList<>();

    public TicketFactory(){
        for(int i=1; i<=maxQuantity; i++){
            Ticket ticket = new Ticket(i,"Ticket tipo "+i, maxQuantity, i%maxQuantity, 100*i);
            ticketList.add(ticket);
        }
    }

    public List<Ticket> getTicketList(){
        return ticketList;
    }
}
