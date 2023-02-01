package com.example.apparty.persistence.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.apparty.persistence.room.daos.AddressDAO;
import com.example.apparty.persistence.room.daos.DressCodeDAO;
import com.example.apparty.persistence.room.daos.EventDAO;
import com.example.apparty.persistence.room.daos.PurchaseDAO;
import com.example.apparty.persistence.room.daos.TicketDAO;
import com.example.apparty.persistence.room.daos.UserDAO;
import com.example.apparty.persistence.room.entities.AddressEntity;
import com.example.apparty.persistence.room.entities.DressCodeEntity;
import com.example.apparty.persistence.room.entities.EventEntity;
import com.example.apparty.persistence.room.entities.PurchaseEntity;
import com.example.apparty.persistence.room.entities.TicketEntity;
import com.example.apparty.persistence.room.entities.UserEntity;

@Database(entities = {EventEntity.class, AddressEntity.class, DressCodeEntity.class, TicketEntity.class, UserEntity.class, PurchaseEntity.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    //Create DAO
    public abstract EventDAO eventDAO();
    public abstract AddressDAO addressDAO();
    public abstract DressCodeDAO dressCodeDAO();
    public abstract PurchaseDAO purchaseDAO();
    public abstract TicketDAO ticketDAO();
    public abstract UserDAO userDAO();

}

