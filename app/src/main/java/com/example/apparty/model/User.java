package com.example.apparty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;
    private String name;
    private String surname;
    private String identification;
    private String email;
    private String password;

    public User(String name, String surname, String identification, String email, String password){
        this.name = name;
        this.surname = surname;
        this.identification = identification;
        this.email = email;
        this.password = password;
    }

}
