package com.example.apparty.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity (tableName = "stock")
public class Stock {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private int totalQuantity;
    private int availableQuantity;
    private double price;
    private int idEvent;

}
