package com.example.hi.bookmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetLoading(false);

    }


    public void SetLoading(boolean show)
    {
        if (show)
            findViewById(R.id.log_loadingPanel).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.log_loadingPanel).setVisibility(View.GONE);
    }
}
