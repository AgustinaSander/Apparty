package com.example.apparty.persistence.room.mappers;

import com.example.apparty.model.DressCode;
import com.example.apparty.persistence.room.entities.DressCodeEntity;

public class DressCodeMapper {
    private DressCodeMapper() {}

    public static DressCodeEntity toEntity (DressCode dressCode){
        return new DressCodeEntity(
                dressCode.getId(),
                dressCode.getDressCode()
        );
    }

    public static DressCode fromEntity (DressCodeEntity dressCode){
        return new DressCode(
                dressCode.getId(),
                dressCode.getDressCode()
        );
    }
}
