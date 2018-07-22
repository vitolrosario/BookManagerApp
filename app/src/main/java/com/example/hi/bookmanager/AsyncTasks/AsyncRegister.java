package com.example.hi.bookmanager.AsyncTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.hi.bookmanager.DataAccess.User;
import com.example.hi.bookmanager.DataBase.BookManagerDB;
import com.example.hi.bookmanager.GeneralClass;
import com.example.hi.bookmanager.RegisterActivity;

public class AsyncRegister extends AsyncTask<Void, Void, Void>{

    private Context context = null;
    private User user = null;
    private int status = 0;

    public void AsyncRegister(Context context, User user)
    {
        this.context = context;
        this.user = user;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... Params) {

        final BookManagerDB db = BookManagerDB.getDatabase(context);//getApplicationContext());

        User response =  db.userDao().ValidateUsername(user.getUsername());

        if (response != null) {
            status = 1;
        }
        else {
            db.userDao().insertUser(user);
            status = 2;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        RegisterActivity registerActivity = (RegisterActivity)context;
        registerActivity.SetLoading(false);

        if (status == 1)
            GeneralClass.showErrorDialog("Error", "Username already Exists!", context);
        else if(status == 2)
        {
            SharedPreferences mSettings = registerActivity.getSharedPreferences("Settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(GeneralClass.Values.NameLogged, user.getFirstName() + " " + user.getLastName());
            editor.putBoolean(GeneralClass.Values.islogged, true);
            editor.apply();

            registerActivity.goMainActivity();
        }

    }

}