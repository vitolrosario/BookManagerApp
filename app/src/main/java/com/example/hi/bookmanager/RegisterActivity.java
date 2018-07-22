package com.example.hi.bookmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

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

    boolean isValidated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                }
                else{
                    if (InsertUser())
                        goMainActivity();


                }
            }
        });

    }

    private boolean InsertUser()
    {
        final BookManagerDB db = BookManagerDB.getDatabase(this);

        final UserDao userDao = db.userDao();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                User response =  db.userDao().ValidateUsername(user.getUsername());

                if (response != null) {
                    isValidated = false;
                    /*SharedPreferences mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(GeneralClass.Values.NameLogged, user.getFirstName() + " " + user.getLastName());
                    editor.putBoolean(GeneralClass.Values.islogged, true);
                    editor.apply();*/
                }
                else {
                    userDao.insertUser(user);
                    isValidated = true;
                }

                return null;
            }
        }.execute();

        /*db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                User response =  db.userDao().ValidateUsername(user.getUsername());

                if (response != null) {
                    isValidated = false;
                    *//*SharedPreferences mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(GeneralClass.Values.NameLogged, user.getFirstName() + " " + user.getLastName());
                    editor.putBoolean(GeneralClass.Values.islogged, true);
                    editor.apply();*//*
                }
                else {
                    userDao.insertUser(user);
                    isValidated = true;
                }

            }
        });*/

        return isValidated;
    }


    private void goMainActivity()
    {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
