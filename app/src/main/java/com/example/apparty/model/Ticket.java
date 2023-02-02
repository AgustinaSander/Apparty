package com.example.apparty.model;

import android.util.Pair;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private int id;
    private String type;
    private int totalQuantity;
    private int availableQuantity;
    private double price;

}
