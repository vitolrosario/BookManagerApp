package com.example.hi.bookmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;
import com.apptakk.http_request.HttpResponse;
import com.example.hi.bookmanager.Books.Book;
import com.example.hi.bookmanager.Books.BookAdapter;
import com.example.hi.bookmanager.Books.Item;
import com.example.hi.bookmanager.Books.RecyclerViewClickListener;
import com.example.hi.bookmanager.Books.VolumeInfo;
import com.example.hi.bookmanager.DataAccess.User;
import com.example.hi.bookmanager.DataBase.BookManagerDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements RecyclerViewClickListener {


    Book BookModel;
    ImageButton btnSearch;
    TextView queryTextView;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetLoading(false);

        ListenSettings();

        ListenSearch();

    }

    private void ListenSettings()
    {
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btn_settings);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsFilterPopup(v);
            }
        });
    }

    private void ListenItemSettings()
    {
        ImageButton btnItemSettings = (ImageButton) findViewById(R.id.btn_item_settings);

        btnItemSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemSettingsFilterPopup(v);
            }
        });
    }

    private void showSettingsFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // Inflate the menu from xml
        popup.inflate(R.menu.settings_menu);
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_favorites:
                        goFavoritesActivity();
                        return true;
                    case R.id.menu_logout:
                        SharedPreferences mSettings = getBaseContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putString(GeneralClass.Values.NameLogged, null);
                        editor.putBoolean(GeneralClass.Values.islogged, false);
                        editor.apply();

                        goLoginActivity();

                        return true;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void showItemSettingsFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // Inflate the menu from xml
        popup.inflate(R.menu.item_settings_menu);
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add_favorite:

                        return true;

                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void AddFavorite(Item book)
    {
        final BookManagerDB db = BookManagerDB.getDatabase(this);

        //db.bookDao().insertBook(book.getVolumeInfo());
    }

    @Override
    public void recyclerViewListClicked(View v, Item book) {
        CallDetailIntent(book);
    }

    private void ListenSearch()
    {
        queryTextView = (TextView) findViewById(R.id.book_query);

        queryTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    MakeSearch(v.getRootView());
                    handled = true;
                }
                return handled;
            }
        });

        btnSearch = (ImageButton) findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeSearch(view);
            }
        });
    }

    private void MakeSearch(View view)
    {
        TextView searchQuery = (TextView) findViewById(R.id.book_query);
        String query = searchQuery.getText().toString();
        if (query != null && !query.isEmpty())
            searchBoookList(query);

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void CallDetailIntent(Item book)
    {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("detail", book);
        startActivity(intent);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    public void searchBoookList(String query)
    {
        SetLoading(true);

        new HttpRequestTask(
                new HttpRequest("https://www.googleapis.com/books/v1/volumes?q=" + query, HttpRequest.GET, null),
                new HttpRequest.Handler() {
                    @Override
                    public void response(HttpResponse response) {
                        try {
                            Type bookType = new TypeToken<Book>(){}.getType();
                            BookModel = new Gson().fromJson(response.body, bookType);

                            FillAdapter(BookModel);

                            SetLoading(false);

                            ListenItemSettings();

                        }
                        catch (Exception e)
                        {

                        }
                    }
                }).execute();
    }

    public void SendNotificacion(Context context)
    {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.search_icon)
                .setContentTitle("BookManager")
                .setContentText("While you're charging your phone, Check out some books ;)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(100, mBuilder.build());
    }

    private void FillAdapter(Book bookModel)
    {
        RecyclerView rvBook = (RecyclerView) findViewById(R.id.rvBooks);
        BookAdapter adapter = new BookAdapter(bookModel.getItems(), MainActivity.this, this);
        rvBook.setAdapter(adapter);
//        rvBook.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvBook.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
    }

    public void SetLoading(boolean show)
    {
        if (show)
            findViewById(R.id.main_loadingPanel).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.main_loadingPanel).setVisibility(View.GONE);
    }

    public void goLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goFavoritesActivity()
    {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

}
