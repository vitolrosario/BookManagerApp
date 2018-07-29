package com.example.hi.bookmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.hi.bookmanager.AsyncTasks.AsyncFavorites;
import com.example.hi.bookmanager.AsyncTasks.AsyncLogin;
import com.example.hi.bookmanager.Books.Book;
import com.example.hi.bookmanager.Books.BookAdapter;
import com.example.hi.bookmanager.Books.Item;
import com.example.hi.bookmanager.Books.RecyclerViewClickListener;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements RecyclerViewClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        SetLoading(false);


    }

    @Override
    public void recyclerViewListClicked(View v, Item book)
    {

    }

    private void GetFavorites()
    {
        AsyncFavorites asyncFavorites = new AsyncFavorites();
        asyncFavorites.AsyncFavorites(this);
        asyncFavorites.execute();
    }

    public void SetLoading(boolean show)
    {
        if (show)
            findViewById(R.id.fav_loadingPanel).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.fav_loadingPanel).setVisibility(View.GONE);
    }

    public void FillAdapter(List<Item> itemList)
    {
        RecyclerView rvFavorites = (RecyclerView) findViewById(R.id.rvFavorites);
        BookAdapter adapter = new BookAdapter(itemList, FavoritesActivity.this, this);
        rvFavorites.setAdapter(adapter);
//        rvFavorites.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvFavorites.setLayoutManager(new GridLayoutManager(FavoritesActivity.this, 2));
    }

}
