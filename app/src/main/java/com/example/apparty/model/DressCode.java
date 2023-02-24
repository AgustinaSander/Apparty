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
public class DressCode {
    private int id;
    private String dressCode;

    public DressCode(String dressCode) {
        this.dressCode = dressCode;
    }
}
