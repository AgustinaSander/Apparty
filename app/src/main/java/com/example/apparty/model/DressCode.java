package com.example.apparty.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
@Entity (tableName = "dressCode")
public class DressCode {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String dressCode;
}
