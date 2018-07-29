package com.example.hi.bookmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hi.bookmanager.AsyncTasks.AsyncLogin;
import com.example.hi.bookmanager.AsyncTasks.AsyncRegister;
import com.example.hi.bookmanager.DataAccess.User;
import com.example.hi.bookmanager.DataAccess.UserDao;
import com.example.hi.bookmanager.DataBase.BookManagerDB;

public class LoginActivity extends AppCompatActivity {
    EditText inputUsername;
    EditText inputPassword;
    private AppCompatButton buttonLogin;
    private AppCompatButton buttonRegister;

    boolean isValidated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);

        SharedPreferences mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        if (mSettings.getBoolean(GeneralClass.Values.islogged, false))
        {
            goMainActivity();
        }


        SetLoading(false);

        ListenLogin();

        ListenRegister();
    }

    private void ListenLogin()
    {
        inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    ValidateLogin(v.getRootView());
                    handled = true;
                }
                return handled;
            }
        });

        buttonLogin = findViewById(R.id.btn_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateLogin(view);
            }
        });

    }

    private void ValidateLogin(View view)
    {
        String username = "";
        String password = "";
        if(inputUsername!=null){
            username = inputUsername.getText().toString().toLowerCase();
        }
        if(inputPassword!=null){
            password = inputPassword.getText().toString().toLowerCase();
        }

        if(username.isEmpty() || password.isEmpty()){
            GeneralClass.showErrorDialog("Error", "Missing Information!", view.getContext());
        }
        else{
            LoginUser(username, password);
        }
    }

    private void ListenRegister()
    {
        buttonRegister = findViewById(R.id.btn_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegisterActivity();
            }
        });

    }

    private void LoginUser(final String username, final String password)
    {
        AsyncLogin asyncLogin = new AsyncLogin();
        asyncLogin.AsyncLogin(this, username, password);
        asyncLogin.execute();
    }

    public void goMainActivity()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void goRegisterActivity()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void SetLoading(boolean show)
    {
        if (show)
            findViewById(R.id.log_loadingPanel).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.log_loadingPanel).setVisibility(View.GONE);
    }
}
