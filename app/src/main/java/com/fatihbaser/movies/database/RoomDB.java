package com.fatihbaser.movies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fatihbaser.movies.service.Dao;


@Database(entities = {MovieData.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {

    public static RoomDB database;

    private static String DATABASE_NAME = "movies_database";

    public abstract Dao dao();

    public synchronized static RoomDB getInstance(Context context){

        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
