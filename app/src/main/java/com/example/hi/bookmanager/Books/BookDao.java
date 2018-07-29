package com.example.hi.bookmanager.Books;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.hi.bookmanager.DataAccess.User;

import java.util.List;

@Dao
public interface BookDao {
   /* @Query("SELECT * FROM VolumeInfo where Username = :username")
    public List<VolumeInfo> getBooksByUser(String username);

    @Query("SELECT * FROM ImageLinks where Username = :username and BookTittle = :book_tittle")
    public ImageLinks getImageByTittle(String username, String book_tittle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insertBook(VolumeInfo book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insertImage(ImageLinks image);

    @Delete
    public void deleteBook(VolumeInfo book);*/
}