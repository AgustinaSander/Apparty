package com.example.apparty.gestores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;


import com.example.apparty.factory.EventFactory;
import com.example.apparty.factory.TicketFactory;
import com.example.apparty.factory.UserFactory;
import com.example.apparty.model.Event;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;


public class GestorEventTest {

    GestorEvent gestorEvent;

    @BeforeEach
    public void setup(){
        gestorEvent = Mockito.spy(new GestorEvent(null));
    }

    // METODO HasMoreExpensiveTickets(List<Ticket> tickets, double minPrice)

    @Test
    @DisplayName("It should return true because there are tickets which are available and more expensive than minPrice")
    public void testHasMoreExpensiveTickets(){
        List<Ticket> ticketList = new TicketFactory().getTicketList();
        double minPrice = 200;
        assertTrue(gestorEvent.hasMoreExpensiveTickets(ticketList, minPrice));
    }

    @Test
    @DisplayName("It should return false because there are not tickets available that are more expensive than minPrice")
    public void testNotAvailableMoreExpensiveTickets(){
        List<Ticket> ticketList = new TicketFactory().getTicketList();
        double minPrice = 1000;
        assertFalse(gestorEvent.hasMoreExpensiveTickets(ticketList, minPrice));
    }

    @Test
    @DisplayName("It should return false because there are not tickets which are more expensive than minPrice")
    public void testHasNotMoreExpensiveTickets(){
        List<Ticket> ticketList = new TicketFactory().getTicketList();
        double minPrice = 1100;
        assertFalse(gestorEvent.hasMoreExpensiveTickets(ticketList, minPrice));
    }

    //METODO GetEventsOrganizedByUser(int idUser)
    @Test
    @DisplayName("Returns all the events with same organizer")
    public void testGetEventsOrganizedByUser(){
        User user = new UserFactory().getUser(1);
        EventFactory eventFactory = new EventFactory();
        doReturn(eventFactory.getEventList()).when(gestorEvent).getEventList();

        List<Event> expectedEventList = eventFactory.getEventListWithOrganizer1();
        List<Event> actualEventList = gestorEvent.getEventsOrganizedByUser(user.getId());
        assertEquals(expectedEventList, actualEventList);
    }

}
