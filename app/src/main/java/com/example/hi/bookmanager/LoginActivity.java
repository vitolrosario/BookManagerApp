package com.example.hi.bookmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

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

        ListenLogin();

        ListenRegister();


    }

    private void ListenLogin()
    {
        buttonLogin = findViewById(R.id.btn_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = "";
                String password = "";
                if(inputUsername!=null){
                    username = inputUsername.getText().toString().toLowerCase();
                }
                if(inputUsername!=null){
                    password = inputPassword.getText().toString().toLowerCase();
                }

                if(username.isEmpty() || password.isEmpty()){
                    showErrorDialog();
                    inputUsername.setText("");
                    inputPassword.setText("");
                }
                else{
                    if (ValidateLogin(username, password))
                        goMainActivity();
                }
            }
        });

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

    private boolean ValidateLogin(final String username, final String password)
    {

        User user = new User();

        final BookManagerDB db = BookManagerDB.getDatabase(this);

        // Define the task
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                User user =  db.userDao().ValidateLogin(username, password);

                if (user != null) {
                    isValidated = true;
                    SharedPreferences mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(GeneralClass.Values.NameLogged, user.getFirstName() + " " + user.getLastName());
                    editor.putBoolean(GeneralClass.Values.islogged, true);
                    editor.apply();
                }
                else {
                    isValidated = false;
                }

            }
        });

        return isValidated;
    }

    private void goMainActivity()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void goRegisterActivity()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void showErrorDialog(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Wrong Username/Passoword");
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

}
