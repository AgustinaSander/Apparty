package com.example.apparty.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;
import com.example.apparty.model.User;

import java.util.List;

@Dao
public interface EventDAO {

    @Query("SELECT * FROM event")
    public List<Event> getAllEvents();

    @Query("SELECT * FROM event WHERE id_event = :id")
    public Event getEvent(int id);

    @Insert
    public void insertEvent(List<Event> eventList);

    @Delete
    public void deleteEvent(Event event);

    @Insert
    public void insertAddress(Address address);

    @Transaction
    @Query("SELECT * FROM address WHERE id = :id_address")
    public void getEventsInAddress(int id_address);

    @Insert
    public void insertStock(Stock stock);

    @Transaction
    @Query("SELECT * FROM event WHERE id_event = :id_event")
    public void getStockEvent(int id_event);

    @Insert
    public void insertDressCode(DressCode dressCode);

    @Transaction
    @Query("SELECT * FROM dressCode WHERE id = :id_dress_code")
    public void getEventsFromDressCode(int id_dress_code);

    @Insert
    public void insertUser(User user);

    @Transaction
    @Query("SELECT * FROM user WHERE id = :id_user")
    public void getEventsFromUser(int id_user);
}
