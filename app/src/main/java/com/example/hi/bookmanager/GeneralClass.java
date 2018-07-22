package com.example.hi.bookmanager;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class GeneralClass {

    public static void showErrorDialog(String tittle, String message, Context context){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(tittle);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    //Check if device is in portrait mode
    /*public static boolean isPortrait(Context context) {
        return context.getResources().getBoolean(R.bool.is_portrait);
    }*/

    public static class Values {
        public static String NameLogged = "NAME_LOGGED";
        public static String islogged = "IS_LOGGED";
    }


}