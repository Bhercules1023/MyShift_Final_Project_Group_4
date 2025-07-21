package com.example.myshiftapp_new;

public class AdminTimeChart {

    private String date;
    private String clockIn;
    private String clockOut;

    public AdminTimeChart(String date, String clockIn, String clockOut){
        this.date = date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }

    public String getDate(){
        return date;
    }

    public String getClockIn(){
        return clockIn;
    }

    public String getClockOut(){
        return clockOut;
    }

}
