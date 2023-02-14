package com.example.apparty.persistence.room.mappers;

import com.example.apparty.model.User;
import com.example.apparty.persistence.room.entities.UserEntity;

public class UserMapper {

    private UserMapper() {}

    public static UserEntity toEntity (User user){
        return new UserEntity(
                user.getId(),
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
}
