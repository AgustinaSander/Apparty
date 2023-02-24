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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EventEntity.class, AddressEntity.class, DressCodeEntity.class, TicketEntity.class, UserEntity.class, PurchaseEntity.class}, version = 18, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class RoomDB extends RoomDatabase {

    private static final String DATABASE_NAME = "apparty_db";
    private static RoomDB instance;

    //Create DAO
    public abstract EventDAO eventDAO();
    public abstract AddressDAO addressDAO();
    public abstract DressCodeDAO dressCodeDAO();
    public abstract PurchaseDAO purchaseDAO();
    public abstract TicketDAO ticketDAO();
    public abstract UserDAO userDAO();

    public static final ExecutorService EXECUTOR_DB = Executors.newSingleThreadExecutor();

    public synchronized static RoomDB getInstance(final Context context) {

        if (instance == null) {
            synchronized (RoomDB.class) {
                if (instance == null) {
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
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
            EXECUTOR_DB.execute( () -> {
                System.out.println("Executing");
                EventDAO eventDAO = instance.eventDAO();
                AddressDAO addressDAO = instance.addressDAO();
                DressCodeDAO dressCodeDAO = instance.dressCodeDAO();
                TicketDAO ticketDAO = instance.ticketDAO();
                UserDAO userDAO = instance.userDAO();

                AddressEntity addressEntity1 = new AddressEntity("Breas 6475", "Argentina", "Santa Fe", "Colastine");
                addressDAO.insertAddress(addressEntity1);
                AddressEntity addressEntity2 = new AddressEntity("Castelli 1621", "Argentina", "Santa Fe", "Santa Fe");
                addressDAO.insertAddress(addressEntity2);
                AddressEntity addressEntity3 = new AddressEntity("Pedro Vittori 3537", "Argentina", "Santa Fe", "Santa Fe");
                addressDAO.insertAddress(addressEntity3);

                UserEntity userEntity1 = new UserEntity("Agustina","Sander","42331387","asander00@hotmail.com","Hola");
                userDAO.insertUser(userEntity1);
                UserEntity userEntity2 = new UserEntity("Araceli","Sarina","42271660","arasarina@hotmail.com","Chau");
                userDAO.insertUser(userEntity2);
                UserEntity userEntity3 = new UserEntity("Agustina","Vergara","42919685","agusv369@gmail.com","Aloha");
                userDAO.insertUser(userEntity3);

                DressCodeEntity dressCodeEntity1 = new DressCodeEntity("Formal");
                DressCodeEntity dressCodeEntity2 = new DressCodeEntity("Informal");
                DressCodeEntity dressCodeEntity3 = new DressCodeEntity("Elegante Sport");

                dressCodeDAO.insertDressCode(dressCodeEntity1);
                dressCodeDAO.insertDressCode(dressCodeEntity2);
                dressCodeDAO.insertDressCode(dressCodeEntity3);


                long ticket1 = ticketDAO.insertTicket(new TicketEntity("Entrada basica", 100, 100, 1500));
                long ticket2 = ticketDAO.insertTicket(new TicketEntity("Entrada vip", 20, 20, 1800));
                long ticket3 = ticketDAO.insertTicket(new TicketEntity("Entrada vip + consumicion", 5, 5, 2000));


                Set<String> tickets = new HashSet<>();
                tickets.add(Long.toString(ticket1));
                tickets.add(Long.toString(ticket2));
                tickets.add(Long.toString(ticket3));


                EventEntity eventEntity1 = new EventEntity("Fiesta en Salones del puerto", 2, tickets, LocalDate.of(2023, 1, 15).toString(), LocalTime.of(17,30).toString(), 1, 1, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity2 = new EventEntity("Poolparty en ATE", 1, tickets, LocalDate.of(2023, 1, 20).toString(), LocalTime.of(17,30).toString(), 2, 1, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity3 = new EventEntity("Fiesta en la playa", 1, tickets, LocalDate.of(2023, 1, 13).toString(), LocalTime.of(17,30).toString(), 2, 3, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity4 = new EventEntity("Evento privado", 2, tickets, LocalDate.now().toString(), LocalTime.of(22,30).toString(), 3, 3, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity5 = new EventEntity("Bresh", 3, tickets, LocalDate.of(2023, 3, 11).toString(), LocalTime.of(23,15).toString(), 2, 3, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity6 = new EventEntity("BlackTie Party", 2, tickets, LocalDate.now().plusDays(3).toString(), LocalTime.of(23,15).toString(), 1, 2, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");

                for (EventEntity event : Arrays.asList(eventEntity1, eventEntity2, eventEntity3, eventEntity4, eventEntity5, eventEntity6)) {
                    eventDAO.insertEvent(event);
                }
            });
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            EXECUTOR_DB.execute( () -> {
                System.out.println("Executing");
                EventDAO eventDAO = instance.eventDAO();
                AddressDAO addressDAO = instance.addressDAO();
                DressCodeDAO dressCodeDAO = instance.dressCodeDAO();
                TicketDAO ticketDAO = instance.ticketDAO();
                UserDAO userDAO = instance.userDAO();

                AddressEntity addressEntity1 = new AddressEntity("Breas 6475", "Argentina", "Santa Fe", "Colastine");
                addressDAO.insertAddress(addressEntity1);
                AddressEntity addressEntity2 = new AddressEntity("Castelli 1621", "Argentina", "Santa Fe", "Santa Fe");
                addressDAO.insertAddress(addressEntity2);
                AddressEntity addressEntity3 = new AddressEntity("Pedro Vittori 3537", "Argentina", "Santa Fe", "Santa Fe");
                addressDAO.insertAddress(addressEntity3);

                UserEntity userEntity1 = new UserEntity("Agustina","Sander","42331387","asander00@hotmail.com","Hola");
                userDAO.insertUser(userEntity1);
                UserEntity userEntity2 = new UserEntity("Araceli","Sarina","42271660","arasarina@hotmail.com","Chau");
                userDAO.insertUser(userEntity2);
                UserEntity userEntity3 = new UserEntity("Agustina","Vergara","42919685","agusv369@gmail.com","Aloha");
                userDAO.insertUser(userEntity3);

                DressCodeEntity dressCodeEntity1 = new DressCodeEntity("Formal");
                DressCodeEntity dressCodeEntity2 = new DressCodeEntity("Informal");
                DressCodeEntity dressCodeEntity3 = new DressCodeEntity("Elegante Sport");

                dressCodeDAO.insertDressCode(dressCodeEntity1);
                dressCodeDAO.insertDressCode(dressCodeEntity2);
                dressCodeDAO.insertDressCode(dressCodeEntity3);


                long ticket1 = ticketDAO.insertTicket(new TicketEntity("Entrada basica", 100, 100, 1500));
                long ticket2 = ticketDAO.insertTicket(new TicketEntity("Entrada vip", 20, 20, 1800));
                long ticket3 = ticketDAO.insertTicket(new TicketEntity("Entrada vip + consumicion", 5, 5, 2000));


                Set<String> tickets = new HashSet<>();
                tickets.add(Long.toString(ticket1));
                tickets.add(Long.toString(ticket2));
                tickets.add(Long.toString(ticket3));


                EventEntity eventEntity1 = new EventEntity("Fiesta en Salones del puerto", 2, tickets, LocalDate.of(2023, 1, 15).toString(), LocalTime.of(17,30).toString(), 1, 1, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity2 = new EventEntity("Poolparty en ATE", 1, tickets, LocalDate.of(2023, 1, 20).toString(), LocalTime.of(17,30).toString(), 2, 1, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity3 = new EventEntity("Fiesta en la playa", 1, tickets, LocalDate.of(2023, 1, 13).toString(), LocalTime.of(17,30).toString(), 2, 3, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity4 = new EventEntity("Evento privado", 2, tickets, LocalDate.now().toString(), LocalTime.of(22,30).toString(), 3, 3, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity5 = new EventEntity("Bresh", 3, tickets, LocalDate.of(2023, 3, 11).toString(), LocalTime.of(23,15).toString(), 2, 3, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");
                EventEntity eventEntity6 = new EventEntity("BlackTie Party", 2, tickets, LocalDate.now().plusDays(3).toString(), LocalTime.of(23,15).toString(), 1, 2, "Aca irian los comentarios acerca de la fiesta, descripcion, caracteristicas");

                for (EventEntity event : Arrays.asList(eventEntity1, eventEntity2, eventEntity3, eventEntity4, eventEntity5, eventEntity6)) {
                    eventDAO.insertEvent(event);
                }
            });

        }
    };
}

