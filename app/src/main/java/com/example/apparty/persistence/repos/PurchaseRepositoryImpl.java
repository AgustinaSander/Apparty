package com.example.apparty.persistence.repos;

import static com.example.apparty.persistence.room.RoomDB.EXECUTOR_DB;

import android.content.Context;
import android.util.Log;

import com.example.apparty.model.Event;
import com.example.apparty.model.Purchase;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.PurchaseDAO;
import com.example.apparty.persistence.room.daos.UserDAO;
import com.example.apparty.persistence.room.entities.PurchaseEntity;
import com.example.apparty.persistence.room.entities.UserEntity;
import com.example.apparty.persistence.room.mappers.PurchaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class PurchaseRepositoryImpl implements PurchaseRepository{

    private final PurchaseDAO purchaseDAO;
    private final UserDAO userDAO;
    private final EventRepositoryImpl eventRepository;
    private ExecutorService executorService;
    private long idPurchaseInserted;


    public PurchaseRepositoryImpl(Context context){
        RoomDB db = RoomDB.getInstance(context);
        this.purchaseDAO = db.purchaseDAO();
        this.userDAO = db.userDAO();
        this.eventRepository = new EventRepositoryImpl(context);
    }

    @Override
    public List<Purchase> getAllPurchases() {

        List<PurchaseEntity> purchaseEntities = purchaseDAO.getAllPurchases();
        List<Purchase> purchases = new ArrayList<>();

        for(PurchaseEntity p: purchaseEntities){
            Event event = eventRepository.getEventById(p.getIdEvent());
            UserEntity user = userDAO.getUser(p.getIdUser());
            purchases.add(PurchaseMapper.fromEntity(p, event, user));
        }
        return purchases;
    }

    @Override
    public Purchase getPurchaseById(int id) {

        PurchaseEntity purchaseEntity = purchaseDAO.getPurchase(id);
        Log.i("PurchaseEntity", "Purchase Entity: "+purchaseEntity);
        Event event = eventRepository.getEventById(purchaseEntity.getIdEvent());
        UserEntity user = userDAO.getUser(purchaseEntity.getIdUser());

        return PurchaseMapper.fromEntity(purchaseEntity, event, user);
    }

    @Override
    public long insertPurchase(Purchase purchase) {

        long idPurchase = purchaseDAO.insertPurchase(PurchaseMapper.toEntity(purchase));

        return idPurchase;
    }

    @Override
    public void deletePurchase(Purchase purchase) {
        EXECUTOR_DB.execute(
                () -> purchaseDAO.deletePurchase(PurchaseMapper.toEntity(purchase))
        );
    }

    @Override
    public void updatePurchase(Purchase purchase) {
        Log.i("PURCHASE EN EVENT DETAIL update", purchase.toString());
        EXECUTOR_DB.execute(
                () -> purchaseDAO.updatePurchase(PurchaseMapper.toEntity(purchase))
        );
    }

    @Override
    public List<Purchase> getPurchasesByIdUser(int idUser) {
        List<PurchaseEntity> purchaseEntities = purchaseDAO.getPurchasesByIdUser(idUser);
        List<Purchase> purchases = new ArrayList<>();

        for(PurchaseEntity p: purchaseEntities){
            Event event = eventRepository.getEventById(p.getIdEvent());
            UserEntity user = userDAO.getUser(idUser);
            purchases.add(PurchaseMapper.fromEntity(p, event, user));
        }
        return purchases;
    }
}
