package com.example.hi.bookmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

//        mDrawerList = (ListView) findViewById(R.id.listSideMenu);



        SetLoading(false);

        //addDrawerItems();

        ListenSearch();

    }

    private void addDrawerItems() {
        String[] osArray = { "Favorites", "Logout"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

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
            findViewById(R.id.log_loadingPanel).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.log_loadingPanel).setVisibility(View.GONE);
    }

}
