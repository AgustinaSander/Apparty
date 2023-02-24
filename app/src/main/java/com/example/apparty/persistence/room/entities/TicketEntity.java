package com.example.apparty.persistence.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity (tableName = "ticket")
public class TicketEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_ticket")
    private int id;
    private String type;
    private int totalQuantity;
    private int availableQuantity;
    private double price;

    public TicketEntity(String type, int totalQuantity, int availableQuantity, double price) {
        this.type = type;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }
}
