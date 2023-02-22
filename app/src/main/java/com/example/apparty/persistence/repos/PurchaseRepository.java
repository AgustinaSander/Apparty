package com.example.apparty.persistence.repos;

import com.example.apparty.model.Purchase;

import java.util.List;

public interface PurchaseRepository {

    List<Purchase> getAllPurchases();
    Purchase getPurchaseById(int id);
    long insertPurchase(Purchase purchase);
    void deletePurchase(Purchase purchase);
    void updatePurchase(Purchase purchase);

}
