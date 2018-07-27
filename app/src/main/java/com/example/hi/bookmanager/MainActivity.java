package com.example.hi.bookmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;
import com.apptakk.http_request.HttpResponse;
import com.example.hi.bookmanager.Books.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetLoading(false);

        SearchView sv = (SearchView)findViewById(R.id.search_book);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }



    public void searchBoookList(String query)
    {
        SetLoading(true);

        new HttpRequestTask(
                new HttpRequest("https://restcountries.eu/rest/v2/name/" + query, HttpRequest.GET, null),
                new HttpRequest.Handler() {
                    @Override
                    public void response(HttpResponse response) {
                        try {
                            Type listType = new TypeToken<ArrayList<Book>>(){}.getType();
                            /*countryListModel = new Gson().fromJson(response.body, listType);

                            FillAdapter(countryListModel);

                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);*/



                        }
                        catch (Exception e)
                        {

                        }
                    }
                }).execute();
    }


    public void SetLoading(boolean show)
    {
        if (show)
            findViewById(R.id.log_loadingPanel).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.log_loadingPanel).setVisibility(View.GONE);
    }
}
