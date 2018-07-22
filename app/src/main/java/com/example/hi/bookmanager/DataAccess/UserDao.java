package com.example.hi.bookmanager.DataAccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User where Username = :username")
    public User ValidateUsername(String username);

    @Query("SELECT * FROM User where Username = :username and Password = :password")
    public User ValidateLogin(String username, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insertUser(User user);

    @Delete
    public void deleteUser(User user);
}