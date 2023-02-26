package com.example.apparty.model;

import android.util.Pair;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket {
    private int id;
    private String type;
    private int totalQuantity;
    private int availableQuantity;
    private double price;

    public Ticket(String type, int totalQuantity, int availableQuantity, double price) {
        this.type = type;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }
}
