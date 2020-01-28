package com.ammase.cartkotlin.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { Cart.class }, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {
    private static final String DB_NAME ="UserDb";
    private static CartDatabase instance;
    public abstract CartDAO userDAO();

    public synchronized static CartDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, CartDatabase.class, DB_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
}
