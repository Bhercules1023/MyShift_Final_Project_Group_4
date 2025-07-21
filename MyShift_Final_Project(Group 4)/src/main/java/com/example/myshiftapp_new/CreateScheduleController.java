package com.example.myshiftapp_new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateScheduleController {

    @FXML
    private ListView<String> scheduledUsers;

    @FXML
    private TextField firstName;

    @FXML
    private TextField enterPositionTitle;

    @FXML
    private TextField enterWeekOf;

    @FXML
    private TextField enterSunday;

    @FXML
    private TextField enterMonday;

    @FXML
    private TextField enterTuesday;

    @FXML
    private TextField enterWednesday;

    @FXML
    private TextField enterThursday;

    @FXML
    private TextField enterFriday;

    @FXML
    private TextField enterSaturday;

    @FXML
    private TextField enterUserTask;

    @FXML
    private Label messageLabel;

    private ObservableList<String> CurrentlyScheduledList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        displayCurrentlyScheduled();
        scheduledUsers.setItems(CurrentlyScheduledList);
    }

    @FXML
    private void displayCurrentlyScheduled(){

        String currentlyScheduled = "SELECT Firstname, Title FROM Schedule";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(currentlyScheduled)){

            ResultSet resultSet = statement.executeQuery();

            CurrentlyScheduledList.clear();

            while (resultSet.next()){
                String getName = resultSet.getString("Firstname");
                String positionTitle = resultSet.getString("Title");

                String displayUser = getName + " (" + positionTitle + ")";
                CurrentlyScheduledList.add(displayUser);
            }
            scheduledUsers.setItems(CurrentlyScheduledList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateSchedule(){
        String firstname = firstName.getText();
        String title = enterPositionTitle.getText();
        String weekOf = enterWeekOf.getText();
        String sunday = enterSunday.getText();
        String monday = enterMonday.getText();
        String tuesday = enterTuesday.getText();
        String wednesday = enterWednesday.getText();
        String thursday = enterThursday.getText();
        String friday = enterFriday.getText();
        String saturday = enterSaturday.getText();
        String taskName = enterUserTask.getText();

        String scheduleUser = "INSERT INTO Schedule (Firstname, Title, WeekOf, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, TaskName)" +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(scheduleUser)){

            statement.setString(1, firstname);
            statement.setString(2, title);
            statement.setString(3, weekOf);
            statement.setString(4, sunday);
            statement.setString(5, monday);
            statement.setString(6, tuesday);
            statement.setString(7, wednesday);
            statement.setString(8, thursday);
            statement.setString(9, friday);
            statement.setString(10, saturday);
            statement.setString(11, taskName);

            int update = statement.executeUpdate();

            if (update > 0){
                messageLabel.setText("User Was Successfully Added To The Schedule!");

                firstName.clear();
                enterPositionTitle.clear();
                enterWeekOf.clear();
                enterSunday.clear();
                enterMonday.clear();
                enterTuesday.clear();
                enterWednesday.clear();
                enterThursday.clear();
                enterFriday.clear();
                enterSaturday.clear();
                enterUserTask.clear();

                displayCurrentlyScheduled();
            }
            else{
                messageLabel.setText("Failed To Add User To The Schedule!");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteSchedule(){
        String selectedUser = scheduledUsers.getSelectionModel().getSelectedItem();

        int openIndex = selectedUser.indexOf(" (");
        int closeIndex = selectedUser.indexOf(")");

        String firstname = selectedUser.substring(0, openIndex);
        String title = selectedUser.substring(openIndex + 2, closeIndex);

        String deleteScheduledUser = "DELETE FROM Schedule WHERE Firstname = ? AND Title = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(deleteScheduledUser)){

            statement.setString(1, firstname);
            statement.setString(2, title);

            int removedUser = statement.executeUpdate();

            if (removedUser > 0){
                messageLabel.setText("User Successfully Removed!");
                displayCurrentlyScheduled();
            }
            else{
                messageLabel.setText("User Could Not Be Removed!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
