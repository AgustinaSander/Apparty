package com.example.apparty.persistence.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apparty.model.User;
import com.example.apparty.persistence.room.entities.UserEntity;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM user WHERE id_user = :id")
    UserEntity getUser(int id);

    @Insert
    void insertUser(UserEntity user);

    @Delete
    void deleteUser(UserEntity user);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    UserEntity getUserByEmailByPassword(String email, String password);

    @Query("SELECT * FROM user WHERE email = :email")
    List<UserEntity> getUsersByEmail(String email);
}
