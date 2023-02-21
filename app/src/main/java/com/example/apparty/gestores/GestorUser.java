package com.example.apparty.gestores;

import android.content.Context;

import com.example.apparty.model.User;
import com.example.apparty.persistence.repos.UserRepositoryImpl;

public class GestorUser {

    private static GestorUser gestorUser;
    private UserRepositoryImpl userRepository;

    private GestorUser (Context context){
        this.userRepository = new UserRepositoryImpl(context);
    }


    public static GestorUser getInstance(Context context){
        if(gestorUser == null){
            gestorUser = new GestorUser(context);
        }
        return gestorUser;
    }

    public User getUserByEmailByPassword(String email, String password){
        //SEGUIR
        //userRepository.getUserByEmailByPassword();
        return null;
    }

}
