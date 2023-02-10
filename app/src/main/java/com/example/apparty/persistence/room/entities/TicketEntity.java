package com.example.apparty.persistence.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity (tableName = "ticket")
public class TicketEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_ticket")
    private int id;
    private String type;
    private int totalQuantity;
    private int availableQuantity;
    private double price;
}
