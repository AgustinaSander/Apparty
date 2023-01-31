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
    // QR ???
    private Event event;
    private User user;
    private List<Pair<Integer, Integer>> tickets;
    private double price;
}
