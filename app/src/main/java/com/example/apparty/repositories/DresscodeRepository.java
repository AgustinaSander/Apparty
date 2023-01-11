package com.example.apparty.repositories;

import com.example.apparty.model.DressCode;

import java.util.List;

public class DresscodeRepository {
    public DresscodeRepository(){}

    private List<DressCode> _DRESSCODES = List.of(
        new DressCode(1, "Formal"),
        new DressCode(2, "Informal"),
        new DressCode(3, "Elegante Sport")
    );

    public List<DressCode> getDresscodes(){
        return _DRESSCODES;
    }
}
