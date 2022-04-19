package com.example.tuan_8_firebase;

import android.content.Context;
import androidx.room.RoomDatabase;
import androidx.room.Database;
import androidx.room.Room;


@Database(entities = {User.class}, version = 1)
public abstract class UserDB extends RoomDatabase {
    private static final String DATABASE_NAME = "user.db";
    private static UserDB instance;

    public static synchronized UserDB getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract com.example.tuan_8_firebase.UserDao userDao();
}
