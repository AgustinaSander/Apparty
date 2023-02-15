package com.example.apparty.persistence.repos;

import android.content.Context;

import com.example.apparty.model.Address;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.AddressDAO;
import com.example.apparty.persistence.room.daos.AddressDAO_Impl;
import com.example.apparty.persistence.room.entities.AddressEntity;
import com.example.apparty.persistence.room.mappers.AddressMapper;

import java.util.List;

public class AddressRepositoryImpl implements AddressRepository {

    /*
    public EventRepositoryImpl(Context context){
        RoomDB db = RoomDB.getInstance(context);
        this.dao = db.eventDAO();
    }
     */

    AddressDAO dao;

    public AddressRepositoryImpl(Context context){
        RoomDB db = RoomDB.getInstance(context);
        this.dao = db.addressDAO();
    }

    @Override
    public List<Address> getAllAddresses() {
        return AddressMapper.fromEntityList(dao.getAllAddresses());
    }

    @Override
    public Address getAddressById(int id) {
        return AddressMapper.fromEntity(dao.getAddress(id));
    }

    @Override
    public void insertAddress(Address address) {
        dao.insertAddress(AddressMapper.toEntity(address));
    }

    @Override
    public void deleteAddress(Address address) {
        dao.deleteAddress(AddressMapper.toEntity(address));
    }
}
