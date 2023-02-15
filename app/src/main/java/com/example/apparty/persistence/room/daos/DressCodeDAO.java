package com.example.apparty.persistence.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apparty.persistence.room.entities.DressCodeEntity;

import java.util.List;

@Dao
public interface DressCodeDAO {
    @Query("SELECT * FROM dressCode")
    List<DressCodeEntity> getAllDressCodes();

    @Query("SELECT * FROM dressCode WHERE id_dressCode = :id")
    DressCodeEntity getDressCode(int id);

    @Insert
    void insertDressCode(DressCodeEntity dressCode);

    @Delete
    void deleteDressCode(DressCodeEntity dressCode);
}
