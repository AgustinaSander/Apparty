package com.example.apparty.gestores;

import android.content.Context;

import com.example.apparty.model.User;
import com.example.apparty.persistence.repos.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class GestorUser {

    private static GestorUser gestorUser;
    private UserRepositoryImpl userRepository;
    private User userById;
    private User userByEmailByPassword;
    private List<User> usersByEmail = new ArrayList<>();


    private GestorUser (Context context){
        this.userRepository = new UserRepositoryImpl(context);
    }


    public static GestorUser getInstance(Context context){
        if(gestorUser == null){
            gestorUser = new GestorUser(context);
        }
        return gestorUser;
    }

    public User getUserById(int idUser){
        Thread hilo1 = new Thread( () -> {
            userById = userRepository.getUserById(idUser);
        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userById;
    }

    public User getUserByEmailByPassword(String email, String password){
        Thread hilo1 = new Thread(() -> {
            this.userByEmailByPassword = userRepository.getUserByEmailByPassword(email, password);
        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.userByEmailByPassword;
    }

    public boolean userWithEmailExists(String email) {
        Thread hilo1 = new Thread(() -> {
            this.usersByEmail = userRepository.userWithEmailExists(email);
        });
        hilo1.start();

        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.usersByEmail.size() > 0;
    }

}
