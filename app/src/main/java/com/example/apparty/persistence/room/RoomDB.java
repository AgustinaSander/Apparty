package com.example.apparty.persistence.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
import com.example.apparty.persistence.room.mappers.Converters;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EventEntity.class, AddressEntity.class, DressCodeEntity.class, TicketEntity.class, UserEntity.class, PurchaseEntity.class}, version = 1, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class RoomDB extends RoomDatabase {

    private static final String DATABASE_NAME = "apparty_db";
    private static RoomDB instance;
    private static Converters converters;

    //Create DAO
    public abstract EventDAO eventDAO();
    public abstract AddressDAO addressDAO();
    public abstract DressCodeDAO dressCodeDAO();
    public abstract PurchaseDAO purchaseDAO();
    public abstract TicketDAO ticketDAO();
    public abstract UserDAO userDAO();

    public static final ExecutorService EXECUTOR_DB = Executors.newSingleThreadExecutor();

    public synchronized static RoomDB getInstance(Context context){
        if (instance==null){
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static RoomDB buildDatabase(final Context context){
        return Room.databaseBuilder(context, RoomDB.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                })
                .addTypeConverter(converters)
                .fallbackToDestructiveMigration()
                .build();
    }



}

