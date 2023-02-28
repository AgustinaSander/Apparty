package com.example.apparty.gestores;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.example.apparty.model.Purchase;
import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.repos.EventRepositoryImpl;
import com.example.apparty.persistence.repos.PurchaseRepositoryImpl;
import com.example.apparty.persistence.repos.TicketRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class GestorPurchase {
    private static GestorPurchase gestorPurchase;
    private GestorEvent gestorEvent;
    private GestorTicket gestorTicket;
    private PurchaseRepositoryImpl purchaseRepository;
    private EventRepositoryImpl eventRepository;
    private TicketRepositoryImpl ticketRepository;
    private Purchase purchaseById;
    private List<Purchase> purchasesByIdUser = new ArrayList<>();
    private long idPurchaseInserted;


    private GestorPurchase(Context context){
        this.purchaseRepository = new PurchaseRepositoryImpl(context);
        this.eventRepository = new EventRepositoryImpl(context);
        this.ticketRepository = new TicketRepositoryImpl(context);
        this.gestorTicket = GestorTicket.getInstance(context);
    }

    public static GestorPurchase getInstance(Context context){
        if(gestorPurchase == null){
            gestorPurchase = new GestorPurchase(context);
        }
        return gestorPurchase;
    }

    public Purchase savePurchase(Purchase purchase){
        Thread hilo1 = new Thread( () -> {
            idPurchaseInserted = purchaseRepository.insertPurchase(purchase);
        });
        hilo1.start();
        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Recorro los tickets comprados y disminuyo la cantidad
        List<Pair<Integer, Integer>> purchaseList = purchase.getPurchases();
        purchaseList.stream().forEach(p -> {
            Ticket ticket = gestorTicket.getTicketById(p.first);
            int quantity = ticket.getAvailableQuantity() - p.second;
            ticket.setAvailableQuantity(quantity);
            gestorTicket.updateTicket(ticket);
        });
        purchase.setId((int) idPurchaseInserted);

        return purchase;
    }

    public Purchase getPurchaseById(int id){

        Thread hilo1 = new Thread( () -> {
            purchaseById = purchaseRepository.getPurchaseById(id);
        });
        hilo1.start();
        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return purchaseById;
    }

    public boolean isPurchaseScannedById(int id){
        Purchase purchase = getPurchaseById(id);
        return purchase.isScanned();
    }

    public void updateQrToScanned(int id) {
        Purchase purchase = getPurchaseById(id);
        purchase.setScanned(true);

        updatePurchase(purchase);
    }

    public void updatePurchase(Purchase purchase) {
        purchaseRepository.updatePurchase(purchase);
    }

    public List<Purchase> getPurchasesByIdUser(int idUser) {
        Thread hilo1 = new Thread( () -> {
            purchasesByIdUser = purchaseRepository.getPurchasesByIdUser(idUser);
        });
        hilo1.start();
        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return purchasesByIdUser;
    }
}
