package com.example.apparty.persistence.room.mappers;

import android.util.Pair;

import com.example.apparty.model.Event;
import com.example.apparty.model.Purchase;
import com.example.apparty.persistence.room.entities.PurchaseEntity;
import com.example.apparty.persistence.room.entities.UserEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseMapper {

    private PurchaseMapper() {
    }

    public static PurchaseEntity toEntity(Purchase purchase) {
        return new PurchaseEntity(
                //purchase.getId(),
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

    public static Purchase fromEntity(PurchaseEntity purchaseEntity, Event event, UserEntity user){
        return new Purchase(
                (int) purchaseEntity.getId(),
                purchaseEntity.getQr(),
                event,
                UserMapper.fromEntity(user),
                listOfStringToPair(purchaseEntity.getPurchases()),
                purchaseEntity.getPrice(),
                purchaseEntity.isScanned()
        );
    }

    private static List<Pair<Integer, Integer>> listOfStringToPair (Set<String> list) {
        String listParam = String.valueOf(list);
        List<Pair<Integer, Integer>> pairList = new ArrayList<>();
        System.out.println("ORIGINAL "+listParam);
        String [] par = listParam.split(",");
        for(String p: par){
            System.out.println("TEXTO "+p);
            String [] ints = p.trim().split("\\s+");
            System.out.println(ints[0]+"  /   "+ints[1]);
            int firstInt = Integer.parseInt(ints[0].replaceAll("[^0-9]", ""));
            System.out.println("FIRST "+firstInt);
            int secondInt = Integer.parseInt(ints[1].replaceAll("[^0-9]", ""));
            System.out.println("SECOND "+secondInt);
            pairList.add(new Pair<>(firstInt, secondInt));
        }
        return pairList;
    }
}
/*
    Entity

    private int id;
    private String qr;
    private int idEvent;
    private int idUser;
    //Lista tickets y cantidad asociada en un string que se genera
    private Set<String> purchases;
    private double price;
    private boolean isScanned;

    Model

    private int id;
    private String qr;
    private Event event;
    private User user;
    //Lista tickets y cantidad asociada
    private List<Pair<Integer, Integer>> purchases;
    private double price;
    private boolean isScanned;
}
 */
