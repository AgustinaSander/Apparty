package com.example.apparty.gestores;

import android.util.Pair;

import com.example.apparty.model.Event;
import com.example.apparty.model.Purchase;

import java.util.HashMap;
import java.util.List;

public class GestorPurchase {
    private static GestorPurchase gestorPurchase;
    //private GestorEvent gestorEvent = GestorEvent.getInstance();

    private GestorPurchase(){}

    public static GestorPurchase getInstance(){
        if(gestorPurchase == null){
            gestorPurchase = new GestorPurchase();
        }

        return gestorPurchase;
    }

    public Purchase savePurchase(Purchase purchase){
        Event event = purchase.getEvent();
        //Recorro los tickets comprados y disminuyo la cantidad
        List<Pair<Integer, Integer>> purchaseList = purchase.getPurchases();
        purchaseList.stream().forEach(p -> {

        });
        //Hay que agregar la purchase a la db

        return purchase;
    }

    public Purchase getPurchaseById(int id){
        //HACER!!!!!
        return new Purchase();
    }

    public boolean isPurchaseScannedById(int id){
        Purchase purchase = getPurchaseById(id);
        //BUSCAR PURCHASE Y DEVOLVER EL ATRIBUTO IS_SCANNED
        //SACAR ESTO DESPUES
        purchase.setScanned(false);

        return purchase.isScanned();
    }

    public void updateQrToScanned(int id) {
        Purchase purchase = getPurchaseById(id);
        purchase.setScanned(true);
        updatePurchase(purchase);
    }

    public void updatePurchase(Purchase purchase) {
        //HACER!!!!!
    }
}
