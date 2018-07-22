package com.example.hi.bookmanager.AsyncTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.hi.bookmanager.DataAccess.User;
import com.example.hi.bookmanager.DataBase.BookManagerDB;
import com.example.hi.bookmanager.GeneralClass;
import com.example.hi.bookmanager.LoginActivity;
import com.example.hi.bookmanager.RegisterActivity;

public class AsyncLogin extends AsyncTask<Void, Void, Void>{

    private Context context = null;
    private String username = null;
    private String password = null;
    private boolean status = false;

    public void AsyncLogin(Context context, String username, String password)
    {
        this.context = context;
        this.username = username;
        this.password = password;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... Params) {

        final BookManagerDB db = BookManagerDB.getDatabase(context);

        User response =  db.userDao().ValidateLogin(username, password);

        if (response != null) {
            status = true;
        }
        else {
            status = false;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        LoginActivity loginActivity = (LoginActivity)context;
        loginActivity.SetLoading(false);

        if (status)
        {
            SharedPreferences mSettings = loginActivity.getSharedPreferences("Settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(GeneralClass.Values.NameLogged, username + " " + password);
            editor.putBoolean(GeneralClass.Values.islogged, true);
            editor.apply();

            loginActivity.goMainActivity();
        }
        else
            GeneralClass.showErrorDialog("Error", "Invalid username/password", context);

    }

}