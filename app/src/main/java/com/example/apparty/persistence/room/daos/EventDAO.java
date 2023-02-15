package com.example.apparty.persistence.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apparty.persistence.room.entities.EventEntity;

import java.util.List;

@Dao
public interface EventDAO {
    @Query("SELECT * FROM event")
    List<EventEntity> getAllEvents();

    @Query("SELECT * FROM event WHERE id_event = :id")
    EventEntity getEvent(int id);

    @Query("SELECT * FROM event WHERE id_dressCode = :id")
    List<EventEntity> getEventByDressCode(int id);


    @Insert
    void insertEvent(EventEntity event);

    @Delete
    void deleteEvent(EventEntity event);

}
