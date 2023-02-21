package com.example.apparty.persistence.room.mappers;

import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.room.entities.TicketEntity;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Ticket> fromEntityList (List<TicketEntity> ticketEntities){
        List<Ticket> ticketList = new ArrayList<>();

        for (TicketEntity t : ticketEntities){
            ticketList.add(TicketMapper.fromEntity(t));
        }
        return ticketList;
    }
    /*
    public static List<Address> fromEntityList (List<AddressEntity> address){

        List<Address> addressList = new ArrayList<>();

        for (AddressEntity a : address) {
            addressList.add(AddressMapper.fromEntity(a));
        }
        return addressList;
    }
     */
}
