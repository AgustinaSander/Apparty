package com.example.apparty.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.PropertyKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity (tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String surname;
    private String identification;
    private String email;
    private String password;

}
