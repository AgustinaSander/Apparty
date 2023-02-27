package com.example.apparty.persistence.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apparty.persistence.room.entities.AddressEntity;

import java.util.List;

@Dao
public interface AddressDAO {
    @Query("SELECT * FROM address")
    List<AddressEntity> getAllAddresses();

    @Query("SELECT * FROM address WHERE id_address = :id")
    AddressEntity getAddress(int id);

    @Insert
    long insertAddress(AddressEntity address);

    @Delete
    void deleteAddress(AddressEntity address);
}
