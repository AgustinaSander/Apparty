package com.example.apparty.persistence.room.mappers;

import com.example.apparty.model.DressCode;
import com.example.apparty.persistence.room.entities.DressCodeEntity;

import java.util.ArrayList;
import java.util.List;

public class DressCodeMapper {
    private DressCodeMapper() {}

    public static DressCodeEntity toEntity (DressCode dressCode){
        return new DressCodeEntity(
                dressCode.getDressCode()
        );
    }

    public static DressCode fromEntity (DressCodeEntity dressCode){
        return new DressCode(
                dressCode.getId(),
                dressCode.getDressCode()
        );
    }

    public static List<DressCode> fromEntityList (List<DressCodeEntity> dressCodes){

        List<DressCode> dressCodeList = new ArrayList<>();

        for (DressCodeEntity dc : dressCodes) {
            dressCodeList.add(DressCodeMapper.fromEntity(dc));
        }
        return dressCodeList;
    }

    public static List<DressCodeEntity> toEntityList (List<DressCode> dressCodes){

        List<DressCodeEntity> dressCodeList = new ArrayList<>();

        for (DressCode dc : dressCodes) {
            dressCodeList.add(DressCodeMapper.toEntity(dc));
        }
        return dressCodeList;
    }


}
