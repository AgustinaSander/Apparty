package com.example.apparty.persistence.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apparty.persistence.room.entities.TicketEntity;

import java.util.List;

@Dao
public interface TicketDAO {
    @Query("SELECT * FROM ticket")
    List<TicketEntity> getAllTickets();

    @Query("SELECT * FROM ticket WHERE id_ticket = :id")
    TicketEntity getEvent(int id);

    @Insert
    void insertTicket(TicketEntity ticket);

    @Delete
    void deleteTicket(TicketEntity ticket);
}
