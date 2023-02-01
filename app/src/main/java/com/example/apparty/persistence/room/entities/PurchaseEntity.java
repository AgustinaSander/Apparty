package com.example.apparty.persistence.room.entities;
import android.util.Pair;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.apparty.model.Event;
import com.example.apparty.model.User;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "purchase")
public class PurchaseEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_purchase")
    private int id;
    @ColumnInfo(name = "id_event")
    private int idEvent;
    @ColumnInfo(name = "id_user")
    private int idUser;
    //Lista tickets y cantidad asociada
    //private List<Pair<Integer, Integer>> purchases;
    private double price;
}
