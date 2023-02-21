package com.example.apparty.persistence.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(tableName = "user")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_user")
    private int id;
    private String name;
    private String surname;
    private String identification;
    private String email;
    private String password;


}
