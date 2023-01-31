package com.example.apparty.model;

import androidx.room.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Filter {
    private double minPrice;
    private double maxPrice;
    private String fromDate;
    private String toDate;
    private List<Integer> dressCodeList = new ArrayList<>();
}
