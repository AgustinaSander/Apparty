package com.example.apparty.factory;

import com.example.apparty.model.User;

public class UserFactory {
    private User user1;
    private User user2;

    public UserFactory(){
        user1 = new User(1, "Nombre 1", "Apellido 1", "Id 1", "Email 1", "Pass 1");
        user2 = new User(2, "Nombre 2", "Apellido 2", "Id 2", "Email 2", "Pass 2");
    }

    public User getUser(int id){
        if(id == 1){
            return user1;
        }
        return user2;
    }
}
