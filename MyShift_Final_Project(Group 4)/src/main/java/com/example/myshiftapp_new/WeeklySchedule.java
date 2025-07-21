package com.example.myshiftapp_new;

public class WeeklySchedule {

    private String employeeName;
    private String displaySunday;
    private String displayMonday;
    private String displayTuesday;
    private String displayWednesday;
    private String displayThursday;
    private String displayFriday;
    private String displaySaturday;

    public WeeklySchedule(String employeeName, String displaySunday, String displayMonday, String displayTuesday, String displayWednesday, String displayThursday, String displayFriday, String displaySaturday){
        this.employeeName = employeeName;
        this.displaySunday = displaySunday;
        this.displayMonday = displayMonday;
        this.displayTuesday = displayTuesday;
        this.displayWednesday = displayWednesday;
        this.displayThursday = displayThursday;
        this.displayFriday = displayFriday;
        this.displaySaturday = displaySaturday;
    }

    public String getEmployeeName(){
        return employeeName;
    }

    public String getDisplaySunday(){
        return displaySunday;
    }

    public String getDisplayMonday(){
        return displayMonday;
    }

    public String getDisplayTuesday(){
        return displayTuesday;
    }

    public String getDisplayWednesday(){
        return displayWednesday;
    }

    public String getDisplayThursday(){
        return displayThursday;
    }

    public String getDisplayFriday(){
        return displayFriday;
    }

    public String getDisplaySaturday(){
        return displaySaturday;
    }
}
