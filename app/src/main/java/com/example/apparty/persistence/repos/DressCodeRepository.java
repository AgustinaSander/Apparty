package com.example.apparty.persistence.repos;

import com.example.apparty.model.DressCode;

import java.util.List;

public interface DressCodeRepository {

    List<DressCode> getAllDressCodes();
    DressCode getDressCodeById(int id);
    void insertDressCode(DressCode dressCode);
    void deleteDressCode(DressCode dressCode);
}
