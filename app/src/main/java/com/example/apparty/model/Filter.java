package com.example.apparty.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private double minPrice;
    private double maxPrice;
    private Date fromDate;
    private Date toDate;
    private List<Integer> dressCodeList = new ArrayList<>();
}
