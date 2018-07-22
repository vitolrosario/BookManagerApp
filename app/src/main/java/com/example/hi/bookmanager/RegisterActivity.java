package com.example.hi.bookmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.hi.bookmanager.AsyncTasks.AsyncRegister;
import com.example.hi.bookmanager.DataAccess.User;
import com.example.hi.bookmanager.DataAccess.UserDao;
import com.example.hi.bookmanager.DataBase.BookManagerDB;

public class RegisterActivity extends AppCompatActivity {

    EditText inputFirstname;
    EditText inputLastname;
    EditText inputEmail;
    EditText inputUsername;
    EditText inputPassword;

    User user;

    private AppCompatButton buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SetLoading(false);

        ListenRegister();

    }

    private void ListenRegister()
    {

        inputFirstname = findViewById(R.id.reg_first_name);
        inputLastname = findViewById(R.id.reg_last_name);
        inputEmail = findViewById(R.id.reg_email);
        inputUsername = findViewById(R.id.reg_username);
        inputPassword = findViewById(R.id.reg_password);


        buttonRegister = findViewById(R.id.btn_create_account);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SetLoading(true);

                user = new User();
                user.setFirstName("");
                user.setLastName("");
                user.setEmail("");
                user.setUsername("");
                user.setPassword("");

                if(inputFirstname!=null){
                    user.setFirstName(inputFirstname.getText().toString().toLowerCase());
                }
                if(inputLastname!=null){
                    user.setLastName(inputLastname.getText().toString().toLowerCase());
                }
                if(inputEmail!=null){
                    user.setEmail(inputEmail.getText().toString().toLowerCase());
                }
                if(inputUsername!=null){
                    user.setUsername(inputUsername.getText().toString().toLowerCase());
                }
                if(inputPassword!=null){
                    user.setPassword(inputPassword.getText().toString().toLowerCase());
                }

                if(user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty()){
                    GeneralClass.showErrorDialog("Error", "Missing Information!", RegisterActivity.this);

                    SetLoading(false);
                }
                else{
                    InsertUser();
                    //goMainActivity();
                }
            }
        });

    }

    private void InsertUser()
    {
        AsyncRegister asyncRegister =  new AsyncRegister();
        asyncRegister.AsyncRegister(this, user);
        asyncRegister.execute();
    }

    public void goMainActivity()
    {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void SetLoading(boolean show)
    {
        if (show)
            findViewById(R.id.reg_loadingPanel).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.reg_loadingPanel).setVisibility(View.GONE);
    }
}
