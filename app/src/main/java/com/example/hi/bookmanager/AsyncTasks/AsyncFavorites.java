package com.example.hi.bookmanager.AsyncTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.hi.bookmanager.Books.ImageLinks;
import com.example.hi.bookmanager.Books.Item;
import com.example.hi.bookmanager.Books.VolumeInfo;
import com.example.hi.bookmanager.DataAccess.User;
import com.example.hi.bookmanager.DataBase.BookManagerDB;
import com.example.hi.bookmanager.FavoritesActivity;
import com.example.hi.bookmanager.GeneralClass;
import com.example.hi.bookmanager.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class AsyncFavorites extends AsyncTask<Void, Void, Void>{

    private Context context = null;
    private List<Item> items = null;
    private int status = 0;

    public void AsyncFavorites(Context context)
    {
        this.context = context;
//        this.volumeInfo = volumeInfo;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... Params) {
        /*FavoritesActivity favoritesActivity = (FavoritesActivity)context;

        SharedPreferences mSettings = favoritesActivity.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String username = mSettings.getString(GeneralClass.Values.NameLogged, null);

        final BookManagerDB db = BookManagerDB.getDatabase(context);//getApplicationContext());

        List<VolumeInfo> volumeInfoResponse =  db.bookDao().getBooksByUser(username);

        for (VolumeInfo volumeInfo : volumeInfoResponse) {
            ImageLinks ImageLinksResponse =  db.bookDao().getImageByTittle(username, volumeInfo.getTitle());
            volumeInfo.setImageLinks(ImageLinksResponse);

            Item item = new Item();
            item.setVolumeInfo(volumeInfo);

            this.items.add(item);
        }

        if (volumeInfoResponse != null && volumeInfoResponse.size() > 0) {
            status = 1;
        }
        else
        {
            status = 0;
        }
*/
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        FavoritesActivity favoritesActivity = (FavoritesActivity)context;

        favoritesActivity.SetLoading(false);

        if (status == 0)
            GeneralClass.showErrorDialog("Error", "No Favorites added!", context);
        else if(status == 1)
        {
            favoritesActivity.FillAdapter(this.items);
        }

    }

}