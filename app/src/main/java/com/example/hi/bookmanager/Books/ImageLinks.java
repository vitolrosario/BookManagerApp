package com.example.hi.bookmanager.Books;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
public class ImageLinks  implements Serializable {
    private String smallThumbnail;
    private String thumbnail;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private String BookTittle;

    private String Username;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getBookTittle() {
        return BookTittle;
    }

    public void setBookTittle(String bookTittle) {
        BookTittle = bookTittle;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}