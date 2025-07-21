package com.example.myshiftapp_new;

public class TaskAssigned {

    private String name;
    private String title;
    private String taskAssigned;

    public TaskAssigned(String name, String title, String taskAssigned){
        this.name = name;
        this.title = title;
        this.taskAssigned = taskAssigned;
    }

    public String getName(){
        return name;
    }

    public String getTitle(){
        return title;
    }

    public String getTaskAssigned(){
        return taskAssigned;
    }

}
