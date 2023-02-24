package com.example.apparty.persistence.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public UserEntity(String name, String surname, String identification, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.identification = identification;
        this.email = email;
        this.password = password;
    }
}
