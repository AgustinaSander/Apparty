package com.example.apparty.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(tableName = "event")
public class Event {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_event")
    private int id;
    private String name;
    //@Embedded private Address address;
    //@ColumnInfo(name = "address_id")
    private int idAddress;
    //private List<Stock> tickets;
    private LocalDate date;
    private LocalTime time;
    //private DressCode dressCode;
    private int idDressCode;
    //private User organizer;
    private int idUserOrganizer;
    private String comments;

    public Event(int id) {
        this.id = id;
    }

    public Event(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Event(String name, int idAddress, LocalDate date){
        this.name = name;
        this.idAddress = idAddress;
        this.date = date;
    }

}

