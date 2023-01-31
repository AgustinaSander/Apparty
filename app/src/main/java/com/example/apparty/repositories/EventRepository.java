package com.example.apparty.repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.apparty.dao.EventDAO;
import com.example.apparty.db.RoomDB;
import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;
import com.example.apparty.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private static EventRepository _REPO=null;
    private EventDAO eventDAO;

    //Patron Singleton. Metodo constructor privado
    private EventRepository(Context ctx){
        RoomDB db = Room.databaseBuilder(ctx, RoomDB.class, "apparty_db").fallbackToDestructiveMigration().build();
        eventDAO = db.eventDAO();

    }

    public static EventRepository getInstance(Context ctx){
        if(_REPO==null) _REPO = new EventRepository(ctx);
        return _REPO;
    }

    public void createEvent(Event event){
        eventDAO.insertEvent(event);
    }

}
    /*public EventRepository(){
   private int id;
    private String name;
    private Address address;
    private List<Stock> tickets;
    private LocalDate date;
    private LocalTime time;
    private DressCode dressCode;
    private User organizer;
    private String comments;*/
    /*private List<Event> _EVENTS = List.of(
            new Event(1, "Fiesta en Salones del puerto", getAddress(), getStock(), LocalDate.of(2023, 1, 15), LocalTime.of(17,30), new DressCode(1,"Formal"), getUser(), getComments()),
            new Event(2, "Poolparty en ATE", getAddress(), getStock(), LocalDate.of(2023, 1, 20), LocalTime.of(17,30), new DressCode(1,"Formal"), getUser(), getComments()),
            new Event(3, "Fiesta en la playa", getAddress(), getStock(), LocalDate.of(2023, 1, 13), LocalTime.of(17,30), new DressCode(1,"Formal"), getUser(), getComments())
    );

    private User getUser() {
        return new User(1,"Agustina","Sander","42331387","asander00@hotmail.com","Hola");
    }

    private String getComments(){
        return "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas";
    }

    private List<Stock> getStock() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock(1, "Entrada basica", 10, 10, 1500));
        stocks.add(new Stock(2, "Entrada vip", 5, 5, 1800));
        stocks.add(new Stock(3, "Entrada vip + consumicion", 2, 2, 2000));

        return stocks;
    }

    private Address getAddress(){
        return new Address(1,"Juan Jose Castelli 1621", "Argentina", "Santa Fe", "Rosario");
    }

    public List<Event> getEvents(){
        return _EVENTS;
    }
}*/
