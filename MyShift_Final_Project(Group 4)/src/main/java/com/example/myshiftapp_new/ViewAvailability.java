package com.example.myshiftapp_new;

public class ViewAvailability {

    private String username;
    private String positionTitle;
    private String sunday;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;

    public ViewAvailability(String getUsername, String positionTitle, String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday){
        this.username = getUsername;
        this.positionTitle = positionTitle;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public String getUserName(){ return username;}

    public String getPositionTitle(){ return positionTitle;}

    public String getSunday(){
        return sunday;
    }

    public String getMonday(){
        return monday;
    }

    public String getTuesday(){
        return tuesday;
    }

    public String getWednesday(){
        return wednesday;
    }

    public String getThursday(){
        return thursday;
    }

    public String getFriday(){
        return friday;
    }

    public String getSaturday(){
        return saturday;
    }
}
