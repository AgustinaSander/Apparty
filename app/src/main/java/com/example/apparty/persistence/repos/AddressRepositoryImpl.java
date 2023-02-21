package com.example.apparty.persistence.repos;

import android.content.Context;

import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Ticket;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.AddressDAO;
import com.example.apparty.persistence.room.daos.DressCodeDAO;
import com.example.apparty.persistence.room.daos.TicketDAO;
import com.example.apparty.persistence.room.mappers.AddressMapper;
import com.example.apparty.persistence.room.mappers.DressCodeMapper;
import com.example.apparty.persistence.room.mappers.TicketMapper;

import java.util.List;

public class AddressRepositoryImpl implements AddressRepository {

    AddressDAO dao;
    DressCodeDAO dressCodeDAO;
    TicketDAO ticketDAO;


    public AddressRepositoryImpl(Context context){
        RoomDB db = RoomDB.getInstance(context);
        this.dao = db.addressDAO();
        this.dressCodeDAO = db.dressCodeDAO();
        this.ticketDAO = db.ticketDAO();
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

    @Override
    public DressCode getDressCodeById(int id){
        return DressCodeMapper.fromEntity(dressCodeDAO.getDressCode(id));
    }

    @Override
    public List<Ticket> getAllTickets(){
        return TicketMapper.fromEntityList(ticketDAO.getAllTickets());
    }

}
