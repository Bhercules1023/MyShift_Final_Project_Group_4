package com.example.myshiftapp_new;

public class CurrentUser {

    private static CurrentUser instance;
    private User currentUser;

    private CurrentUser(){}

    public static CurrentUser getInstance(){
        if(instance == null){
            instance = new CurrentUser();
        }
        return instance;
    }

    public User getLoggedInUser(){
        return currentUser;
    }

    public void setLoggedInUser(User user){
        this.currentUser = user;
    }

}
