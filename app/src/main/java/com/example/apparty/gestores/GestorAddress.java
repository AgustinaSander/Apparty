package com.example.apparty.gestores;

import android.content.Context;

import com.example.apparty.model.Address;
import com.example.apparty.persistence.repos.AddressRepositoryImpl;

public class GestorAddress {

    private static GestorAddress gestorAddress;
    private AddressRepositoryImpl addressRepository;
    private Address addressById;
    private long idAddressInserted;

    private GestorAddress(Context context){
        this.addressRepository = new AddressRepositoryImpl(context);
    }

    public static GestorAddress getInstance(Context context){
        if(gestorAddress == null){
            gestorAddress = new GestorAddress(context);
        }
        return gestorAddress;
    }

    public Address saveAddress(Address address){
        Thread hilo1 = new Thread( () -> {
            idAddressInserted = addressRepository.insertAddress(address);
        });
        hilo1.start();
        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        address.setId((int) idAddressInserted);
        return address;
    }

    public Address getAddressById(int id){
        Thread hilo1 = new Thread( () -> {
            addressById = addressRepository.getAddressById(id);
        });
        hilo1.start();
        try {
            hilo1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return addressById;
    }

}
