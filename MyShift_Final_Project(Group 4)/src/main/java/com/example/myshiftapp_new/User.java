package com.example.myshiftapp_new;

public class User {

    //Maybe you need to change int to String.
    private int id;
    private String username;

    public User(int id, String username){
        this.id = id;
        this.username = username;
    }

    public int getID(){
        return id;
    }

    public String getUsername(){
        return username;
    }

}
