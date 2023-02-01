package com.example.apparty.persistence.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apparty.persistence.room.entities.PurchaseEntity;

import java.util.List;

@Dao
public interface PurchaseDAO {
    @Query("SELECT * FROM purchase")
    List<PurchaseEntity> getAllPurchases();

    @Query("SELECT * FROM purchase WHERE id_purchase = :id")
    PurchaseEntity getPurchase(int id);

    @Insert
    void insertPurchase(PurchaseEntity purchase);

    @Delete
    void deletePurchase(PurchaseEntity purchase);
}
