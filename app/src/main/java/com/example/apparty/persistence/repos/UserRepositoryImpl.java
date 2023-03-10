package com.example.apparty.persistence.repos;

import android.content.Context;

import com.example.apparty.model.User;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.UserDAO;
import com.example.apparty.persistence.room.entities.UserEntity;
import com.example.apparty.persistence.room.mappers.UserMapper;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    UserDAO dao;

    public UserRepositoryImpl(Context context){
        RoomDB db = RoomDB.getInstance(context);
        this.dao = db.userDAO();
    }

    @Override
    public List<User> getAllUsers() {
        return UserMapper.fromEntityList(dao.getAllUsers());
    }

    @Override
    public User getUserById(int id) {
        return UserMapper.fromEntity(dao.getUser(id));
    }

    @Override
    public void insertUser(User user) {

        RoomDB.EXECUTOR_DB.execute(
                () -> dao.insertUser(UserMapper.toEntity(user))
        );

    }

    @Override
    public void deleteUser(User user) {
        RoomDB.EXECUTOR_DB.execute(

                () -> dao.deleteUser(UserMapper.toEntity(user))
        );
    }

    @Override
    public User getUserByEmailByPassword(String email, String password) {
        UserEntity userEntity = dao.getUserByEmailByPassword(email, password);
        return userEntity == null ? null : UserMapper.fromEntity(userEntity);
    }

    @Override
    public List<User> userWithEmailExists(String email) {
        List<UserEntity> userEntities = dao.getUsersByEmail(email);
        return UserMapper.fromEntityList(userEntities);
    }
}
