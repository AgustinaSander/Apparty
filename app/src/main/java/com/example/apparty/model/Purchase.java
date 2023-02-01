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
public class Purchase {
    private int id;
    // QR ???
    private Event event;
    private User user;
    //Lista tickets y cantidad asociada
    private List<Pair<Integer, Integer>> purchases;
    private double price;
}
