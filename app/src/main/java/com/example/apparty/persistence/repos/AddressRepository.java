package com.example.apparty.persistence.repos;

import com.example.apparty.model.Address;
import com.example.apparty.model.Event;

import java.util.List;

public interface AddressRepository {

    List<Address> getAllAddresses();
    Address getAddressById(int id);
    void insertAddress(Address address);
    void deleteAddress(Address address);

}
