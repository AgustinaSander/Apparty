package com.example.apparty.persistence.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(tableName = "dressCode")
public class DressCodeEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_dressCode")
    private int id;
    private String dressCode;

    public DressCodeEntity(String dressCode) {
        this.dressCode = dressCode;
    }
}
