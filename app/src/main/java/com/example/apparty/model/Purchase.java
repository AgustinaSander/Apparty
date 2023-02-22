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
public class Purchase {
    private int id;
    private String qr;
    private Event event;
    private User user;
    //Lista tickets y cantidad asociada
    private List<Pair<Integer, Integer>> purchases;
    private double price;
    private boolean isScanned;

    public Purchase(String qr, Event event, User user, List<Pair<Integer, Integer>> purchases, double price, boolean isScanned) {
        this.qr = qr;
        this.event = event;
        this.user = user;
        this.purchases = purchases;
        this.price = price;
        this.isScanned = isScanned;
    }
}


