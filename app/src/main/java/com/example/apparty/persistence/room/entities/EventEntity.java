package com.example.apparty.persistence.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity(tableName = "event",
        indices = @Index(value = {"id_address", "id_user", "id_dressCode"}),
        foreignKeys = {
        @ForeignKey(entity = AddressEntity.class,
        parentColumns = "id_address", childColumns = "id_address"),
        @ForeignKey(entity = UserEntity.class,
        parentColumns = "id_user", childColumns = "id_user"),
        @ForeignKey(entity = DressCodeEntity.class,
        parentColumns = "id_dressCode", childColumns = "id_dressCode")})
public class EventEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_event")
    private int id;
    private String name;
    @ColumnInfo(name = "id_address")
    private int idAddress;
    //private Set<Integer> tickets;
    private Long date;
    private Long time;
    @ColumnInfo(name = "id_dressCode")
    private int idDressCode;
    @ColumnInfo(name = "id_user")
    private int idUserOrganizer;
    private String comments;
}
/*    private int id;
    private String name;
    private Address address;
    private List<Ticket> tickets; -- Esta relación la hago en la entity Ticket, con referencia al evento.
                                     Después en la consulta, junto las tablas y obtengo la lista de tickets del evento?
    private LocalDate date;       -- Cómo convierto la fecha a long?
    private LocalTime time;
    private DressCode dressCode;
    private User organizer;
    private String comments;
*/