package com.example.apparty.persistence.room.mappers;

import com.example.apparty.model.User;
import com.example.apparty.persistence.room.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    private UserMapper() {}

    public static UserEntity toEntity (User user){
        return new UserEntity(
                user.getName(),
                user.getSurname(),
                user.getIdentification(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public static User fromEntity (UserEntity user){
        return new User(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getIdentification(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public static List<User> fromEntityList (List<UserEntity> users){

        List<User> userList = new ArrayList<>();

        for (UserEntity u : users) {
            userList.add(UserMapper.fromEntity(u));
        }
        return userList;
    }

    public static List<UserEntity> toEntityList (List<User> users){

        List<UserEntity> userList = new ArrayList<>();

        for (User u : users) {
            userList.add(UserMapper.toEntity(u));
        }
        return userList;
    }

}
