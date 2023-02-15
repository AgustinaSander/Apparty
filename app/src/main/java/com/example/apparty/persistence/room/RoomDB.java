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

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EventEntity.class, AddressEntity.class, DressCodeEntity.class, TicketEntity.class, UserEntity.class, PurchaseEntity.class}, version = 2, exportSchema = true)
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

    public static RoomDB getInstance(final Context context) {
        if(instance == null){
            synchronized (RoomDB.class){
                if (instance == null){
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RoomDB.class,
                            DATABASE_NAME
                    )
                    .addCallback(mRoomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return instance;
    }

    //Prepoblar la base de datos con callback
    private static final RoomDatabase.Callback mRoomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            EXECUTOR_DB.execute( () -> {
                EventDAO eventDAO = instance.eventDAO();
                AddressDAO addressDAO = instance.addressDAO();
                DressCodeDAO dressCodeDAO = instance.dressCodeDAO();
                TicketDAO ticketDAO = instance.ticketDAO();
                UserDAO userDAO = instance.userDAO();

                AddressEntity addressEntity1 = new AddressEntity(1, "Breas 6475", "Argentina", "Santa Fe", "Santa Fe");
                addressDAO.insertAddress(addressEntity1);

                UserEntity userEntity1 = new UserEntity(1, "Agustina","Sander","42331387","asander00@hotmail.com","Hola");
                userDAO.insertUser(userEntity1);

                DressCodeEntity dressCodeEntity1 = new DressCodeEntity(1, "Formal");
                DressCodeEntity dressCodeEntity2 = new DressCodeEntity(2, "Informal");
                DressCodeEntity dressCodeEntity3 = new DressCodeEntity(3, "Elegante Sport");

                dressCodeDAO.insertDressCode(dressCodeEntity1);
                dressCodeDAO.insertDressCode(dressCodeEntity2);
                dressCodeDAO.insertDressCode(dressCodeEntity3);

                /*
                ticketDAO.insertTicket(new TicketEntity(1, "Entrada basica", 10, 10, 1500));
                ticketDAO.insertTicket(new TicketEntity(2, "Entrada vip", 5, 5, 1800));
                ticketDAO.insertTicket(new TicketEntity(3, "Entrada vip + consumicion", 2, 2, 2000));
                 */

                EventEntity eventEntity1 = new EventEntity(1, "Fiesta en Salones del puerto", addressEntity1.getId(), LocalDate.of(2023, 1, 15).getLong(ChronoField.EPOCH_DAY), LocalTime.of(17,30).getLong(ChronoField.EPOCH_DAY), dressCodeEntity1.getId(), userEntity1.getId(), "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity2 = new EventEntity(2, "Poolparty en ATE", addressEntity1.getId(), LocalDate.of(2023, 1, 20).getLong(ChronoField.EPOCH_DAY), LocalTime.of(17,30).getLong(ChronoField.EPOCH_DAY), dressCodeEntity2.getId(), userEntity1.getId(), "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity3 = new EventEntity(3, "Fiesta en la playa", addressEntity1.getId(), LocalDate.of(2023, 1, 13).getLong(ChronoField.EPOCH_DAY), LocalTime.of(17,30).getLong(ChronoField.EPOCH_DAY), dressCodeEntity3.getId(), userEntity1.getId(), "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");

            });
        }
    };

    /*
    public synchronized static RoomDB getInstance(final Context context){
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
                .fallbackToDestructiveMigration()
                .build();
    }*/
}

