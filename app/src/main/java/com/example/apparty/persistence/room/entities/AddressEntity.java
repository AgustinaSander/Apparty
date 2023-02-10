package com.example.apparty.persistence.room.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(tableName = "address")
public class AddressEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_address")
    private int id;
    private String address;
    private String country;
    private String province;
    private String localty;

}
