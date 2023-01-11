package com.example.apparty.gestores;

import com.example.apparty.model.DressCode;
import com.example.apparty.repositories.DresscodeRepository;

import java.util.List;

public class GestorEvent {
    private static GestorEvent gestorEvent;
    private DresscodeRepository dresscodeRepository = new DresscodeRepository();
    private List<DressCode> dressCodeList;

    private GestorEvent(){
        dressCodeList = dresscodeRepository.getDresscodes();
    }

    public static GestorEvent getInstance(){
        if(gestorEvent == null){
            gestorEvent = new GestorEvent();
        }

        return gestorEvent;
    }

    public List<DressCode> getDressCodeList(){
        return this.dressCodeList;
    }
}
