package com.example.apparty.persistence.room.entities;
import android.util.Pair;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.apparty.model.Event;
import com.example.apparty.model.User;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(tableName = "purchase",
        indices = @Index(value = {"id_event", "id_user"}),
        foreignKeys = {
        @ForeignKey(entity = EventEntity.class, parentColumns = "id_event", childColumns = "id_event"),
        @ForeignKey(entity = UserEntity.class, parentColumns = "id_user", childColumns = "id_user")})
public class PurchaseEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_purchase")
    private int id;
    private String qr;
    @ColumnInfo(name = "id_event")
    private int idEvent;
    @ColumnInfo(name = "id_user")
    private int idUser;
    //Lista tickets y cantidad asociada en un string que se genera
    private Set<String> purchases;
    private double price;
    private boolean isScanned;
}
