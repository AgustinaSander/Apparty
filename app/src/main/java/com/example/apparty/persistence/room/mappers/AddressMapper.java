package com.example.apparty.persistence.room.mappers;

import com.example.apparty.model.Address;
import com.example.apparty.persistence.room.entities.AddressEntity;

import java.util.ArrayList;
import java.util.List;

public class AddressMapper {

    private AddressMapper() {}

    public static AddressEntity toEntity (Address address){
        return new AddressEntity (
                address.getAddress(),
                address.getCountry(),
                address.getProvince(),
                address.getCountry()
        );
    }

    public static Address fromEntity (AddressEntity address){
        return new Address(
                address.getId(),
                address.getAddress(),
                address.getCountry(),
                address.getProvince(),
                address.getLocalty()
        );
    }


    public static List<Address> fromEntityList (List<AddressEntity> address){

        List<Address> addressList = new ArrayList<>();

        for (AddressEntity a : address) {
            addressList.add(AddressMapper.fromEntity(a));
        }
        return addressList;
    }

    public static List<AddressEntity> toEntityList (List<Address> address){

        List<AddressEntity> addressList = new ArrayList<>();

        for (Address a : address) {
            addressList.add(AddressMapper.toEntity(a));
        }
        return addressList;
    }
}

/*    Entity
    private int id;
    private String address;
    private String country;
    private String province;
    private String localty;

    Model
    private int id;
    private String address;
    private String country;
    private String province;
    private String localty;*/