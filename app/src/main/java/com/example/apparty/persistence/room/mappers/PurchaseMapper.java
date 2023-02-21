package com.example.apparty.persistence.room.mappers;

import android.util.Pair;

import com.example.apparty.model.Event;
import com.example.apparty.model.Purchase;
import com.example.apparty.persistence.room.daos.EventDAO;
import com.example.apparty.persistence.room.daos.UserDAO;
import com.example.apparty.persistence.room.entities.PurchaseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseMapper {

    private static EventDAO eventDAO;
    private static UserDAO userDAO;

    private PurchaseMapper() {
    }

    public static PurchaseEntity toEntity(Purchase purchase) {
        return new PurchaseEntity(
                purchase.getId(),
                purchase.getQr(),
                purchase.getEvent().getId(),
                purchase.getUser().getId(),
                listOfPairToString(purchase.getPurchases()),
                purchase.getPrice(),
                purchase.isScanned()
        );
    }

    private static Set<String> listOfPairToString (List<android.util.Pair<Integer, Integer>> list) {

        Set<String> stringSet = new HashSet<>();
        for (android.util.Pair<Integer, Integer> p : list) {
            stringSet.add(p.toString());
        }
        return stringSet;
    }

    public static Purchase fromEntity(PurchaseEntity purchaseEntity){
        return new Purchase(
                purchaseEntity.getId(),
                purchaseEntity.getQr(),
                //EventMapper.fromEntity(eventDAO.getEvent(purchaseEntity.getIdEvent())),
                new Event(),
                UserMapper.fromEntity(userDAO.getUser(purchaseEntity.getIdUser())),
                listOfStringToPair(purchaseEntity.getPurchases()),
                purchaseEntity.getPrice(),
                purchaseEntity.isScanned()
        );
    }

    private static List<android.util.Pair<Integer, Integer>> listOfStringToPair (Set<String> list) {

        List<android.util.Pair<Integer, Integer>> pairList = new ArrayList<>();
        for (String p : list) {
            String [] par = p.split("=");

            pairList.add(new Pair<>(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
        }
        return pairList;
    }
}
