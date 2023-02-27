package com.example.apparty.persistence.repos;

import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Ticket;

import java.util.List;

public interface AddressRepository {

    List<Address> getAllAddresses();
    Address getAddressById(int id);
    long insertAddress(Address address);
    void deleteAddress(Address address);

    DressCode getDressCodeById(int id);
    List<Ticket> getAllTickets();
}
