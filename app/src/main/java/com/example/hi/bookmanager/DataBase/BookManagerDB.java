package com.example.hi.bookmanager.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.hi.bookmanager.Books.BookDao;
import com.example.hi.bookmanager.Books.ImageLinks;
import com.example.hi.bookmanager.Books.VolumeInfo;
import com.example.hi.bookmanager.DataAccess.User;
import com.example.hi.bookmanager.DataAccess.UserDao;

@Database(entities = {User.class/*, VolumeInfo.class, ImageLinks.class*/}, version = 1)
public abstract class BookManagerDB extends RoomDatabase {

    private static BookManagerDB INSTANCE;

    public abstract UserDao userDao();
    public abstract BookDao bookDao();

    public static BookManagerDB getDatabase(Context context) {

        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BookManagerDB.class, "BookManagerDB").build();

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}