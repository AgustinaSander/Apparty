package com.example.apparty.model;

import android.util.Pair;

import java.time.LocalDate;
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
public class PurchaseInfoQR {
    private int id;
    private int idEvent;
    private String username;
    private List<Pair<Integer, Integer>> purchases;
}
