package com.example.myshiftapp_new;

public class Announcement {
    private String date;
    private String message;
    private String sender;

    public Announcement(String date, String message, String sender){
        this.date = date;
        this.message = message;
        this.sender = sender;
    }

    public String getDate(){
        return date;
    }

    public String getMessage(){
        return message;
    }

    public String getSender(){
        return sender;
    }
}
