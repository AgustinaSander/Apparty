package com.example.apparty.gestores;

import android.util.Pair;

import com.example.apparty.model.Event;
import com.example.apparty.model.Purchase;
import com.example.apparty.model.Ticket;

import java.util.List;

public class GestorPurchase {
    private static GestorPurchase gestorPurchase;
    private GestorEvent gestorEvent = GestorEvent.getInstance();

    private GestorPurchase(){}

    public static GestorPurchase getInstance(){
        if(gestorPurchase == null){
            gestorPurchase = new GestorPurchase();
        }

        return gestorPurchase;
    }

    public void savePurchase(Purchase purchase){
        Event event = purchase.getEvent();
        //Recorro los tickets comprados y disminuyo la cantidad
        List<Pair<Integer, Integer>> purchaseList = purchase.getPurchases();
        purchaseList.stream().forEach(p -> {

        });
        //Hay que agregar la purchase a la db
    }

    public boolean isPurchaseScannedById(int id){
        //BUSCAR PURCHASE Y DEVOLVER EL ATRIBUTO IS_SCANNED
        return false;
    }
}
