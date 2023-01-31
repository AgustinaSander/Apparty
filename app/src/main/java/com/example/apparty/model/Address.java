package com.example.apparty.model;

import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity (tableName = "address")
public class Address {
    private int id;
    private String address;
    private String country;
    private String province;
    private String localty;

}
