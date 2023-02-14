package com.example.apparty.persistence.repos;

import com.example.apparty.model.Address;
import com.example.apparty.persistence.room.daos.AddressDAO;
import com.example.apparty.persistence.room.entities.AddressEntity;
import com.example.apparty.persistence.room.mappers.AddressMapper;

import java.util.List;

public class AddressRepositoryImpl implements AddressRepository{

    AddressDAO dao;

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
