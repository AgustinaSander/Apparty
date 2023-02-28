package com.example.apparty.persistence.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
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
    private Set<String> tickets;
    private String date;
    private String time;
    @ColumnInfo(name = "id_dressCode")
    private int idDressCode;
    @ColumnInfo(name = "id_user")
    private int idUserOrganizer;
    private String comments;
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public EventEntity(String name, int idAddress, Set<String> tickets, String date, String time, int idDressCode, int idUserOrganizer, String comments, byte[] image) {
        this.name = name;
        this.idAddress = idAddress;
        this.tickets = tickets;
        this.date = date;
        this.time = time;
        this.idDressCode = idDressCode;
        this.idUserOrganizer = idUserOrganizer;
        this.comments = comments;
        this.image = image;
    }
}