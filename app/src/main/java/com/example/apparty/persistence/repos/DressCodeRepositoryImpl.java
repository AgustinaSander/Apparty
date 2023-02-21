package com.example.apparty.persistence.repos;

import android.content.Context;

import com.example.apparty.model.DressCode;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.DressCodeDAO;
import com.example.apparty.persistence.room.mappers.DressCodeMapper;

import java.util.List;

public class DressCodeRepositoryImpl implements DressCodeRepository {

    private final DressCodeDAO dressCodeDAO;

    public DressCodeRepositoryImpl(Context context) {
        RoomDB roomDB = RoomDB.getInstance(context);
        this.dressCodeDAO = roomDB.dressCodeDAO();
    }


    @Override
    public List<DressCode> getAllDressCodes() {
        return DressCodeMapper.fromEntityList(dressCodeDAO.getAllDressCodes());
    }

    @Override
    public DressCode getDressCodeById(int id) {
        return DressCodeMapper.fromEntity(dressCodeDAO.getDressCode(id));
    }

    @Override
    public void insertDressCode(DressCode dressCode) {
        RoomDB.EXECUTOR_DB.execute(
                () -> dressCodeDAO.insertDressCode(DressCodeMapper.toEntity(dressCode))
        );
    }

    @Override
    public void deleteDressCode(DressCode dressCode) {
        RoomDB.EXECUTOR_DB.execute(
                () -> dressCodeDAO.deleteDressCode(DressCodeMapper.toEntity(dressCode))
        );
    }
}
