package com.example.myshiftapp_new;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {

    private Connection databaseLink;

    public Connection getConnection(){

        String url = "jdbc:sqlite:src/main/resources/MyShiftDB.db";

        try{
            databaseLink = DriverManager.getConnection(url);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
