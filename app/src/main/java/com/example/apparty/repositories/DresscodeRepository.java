package com.example.apparty.repositories;

import com.example.apparty.model.DressCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<DressCode> findAllByIds(List<Integer> dressCodeList) {
        List<DressCode> dressCodes = new ArrayList<>();
        dressCodeList.stream().forEach(d -> {
            Optional<DressCode> dresscode = getDresscodes().stream().filter(dc -> dc.getId() == d).findFirst();
            if(dresscode.isPresent())
                dressCodes.add(dresscode.get());
        });
        return dressCodes;
    }
}
