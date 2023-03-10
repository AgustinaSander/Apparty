package com.example.apparty.model;

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
public class Address {
    private int id;
    private String address;
    private String country;
    private String province;
    private String localty;

    public Address(String address, String country, String province, String localty) {
        this.address = address;
        this.country = country;
        this.province = province;
        this.localty = localty;
    }
}
