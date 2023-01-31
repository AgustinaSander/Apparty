package com.example.apparty.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.apparty.dao.EventDAO;
import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;
import com.example.apparty.model.User;

@Database(entities = {Event.class, Address.class, DressCode.class, Stock.class, User.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    //Create DAO
    public abstract EventDAO eventDAO();
}
