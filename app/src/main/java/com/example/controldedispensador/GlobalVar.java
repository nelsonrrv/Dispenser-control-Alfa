package com.example.controldedispensador;

import android.app.Application;

public class GlobalVar extends Application {

    private static GlobalVar instance;

    private String actual_user = "";

    public String getActualUser() {
        return actual_user;
    }

    public String setActualUser(String str) {
        actual_user = str;
        return str;
    }
}