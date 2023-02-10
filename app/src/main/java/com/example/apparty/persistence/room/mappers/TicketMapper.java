package com.example.apparty.persistence.room.mappers;

import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.room.entities.TicketEntity;

public class TicketMapper {

    private TicketMapper () {}

    public static TicketEntity toEntity (Ticket ticket){
        return new TicketEntity(
                ticket.getId(),
                ticket.getType(),
                ticket.getTotalQuantity(),
                ticket.getAvailableQuantity(),
                ticket.getPrice()
        );
    }

    public static Ticket fromEntity (TicketEntity ticket){
        return new Ticket(
                ticket.getId(),
                ticket.getType(),
                ticket.getTotalQuantity(),
                ticket.getAvailableQuantity(),
                ticket.getPrice()
        );
    }
}
