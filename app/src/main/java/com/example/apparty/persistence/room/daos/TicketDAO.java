package com.example.apparty.persistence.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.apparty.persistence.room.entities.TicketEntity;

import java.util.List;

@Dao
public interface TicketDAO {
    @Query("SELECT * FROM ticket")
    List<TicketEntity> getAllTickets();

    @Query("SELECT * FROM ticket WHERE id_ticket = :id")
    TicketEntity getTicket(int id);

    @Insert
    long insertTicket(TicketEntity ticket);

    @Delete
    void deleteTicket(TicketEntity ticket);

    @Update
    void updateTicket(TicketEntity ticket);
}
