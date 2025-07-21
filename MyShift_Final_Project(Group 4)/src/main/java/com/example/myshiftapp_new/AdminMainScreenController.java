package com.example.myshiftapp_new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminMainScreenController {
    @FXML
    private Button adminTimeDisplay;

    @FXML
    private Button adminAnnouncements;

    @FXML
    private Button account;

    @FXML
    private Button setAvailability;

    @FXML
    private Button punchInOut;

    @FXML
    private Button benefits;

    @FXML
    private Button notes;

    @FXML
    private Button viewAvailability;

    @FXML
    private Button logOut;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label weekOfLabel;

    @FXML
    private TableView<WeeklySchedule> viewAdminSchedule;

    @FXML
    private TableColumn<WeeklySchedule, String> employeeName;

    @FXML
    private TableColumn<WeeklySchedule, String> displaySunday;

    @FXML
    private TableColumn<WeeklySchedule, String> displayMonday;

    @FXML
    private TableColumn<WeeklySchedule, String> displayTuesday;

    @FXML
    private TableColumn<WeeklySchedule, String> displayWednesday;

    @FXML
    private TableColumn<WeeklySchedule, String> displayThursday;

    @FXML
    private TableColumn<WeeklySchedule, String> displayFriday;

    @FXML
    private TableColumn<WeeklySchedule, String> displaySaturday;

    @FXML
    private TableView <TaskAssigned> viewTaskAssigned;

    @FXML
    private TableColumn<TaskAssigned, String> getName;

    @FXML
    private TableColumn<TaskAssigned, String> getPositionTitle;

    @FXML
    private TableColumn<TaskAssigned, String> getTaskAssigned;

    private final ObservableList<WeeklySchedule> WeeklyScheduleList = FXCollections.observableArrayList();
    private final ObservableList<TaskAssigned> TaskAssignedList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        User currentUser = CurrentUser.getInstance().getLoggedInUser();
        usernameLabel.setText(currentUser.getUsername() + "!");

        employeeName.setCellValueFactory((new PropertyValueFactory<>("employeeName")));
        displaySunday.setCellValueFactory(new PropertyValueFactory<>("displaySunday"));
        displayMonday.setCellValueFactory(new PropertyValueFactory<>("displayMonday"));
        displayTuesday.setCellValueFactory(new PropertyValueFactory<>("displayTuesday"));
        displayWednesday.setCellValueFactory(new PropertyValueFactory<>("displayWednesday"));
        displayThursday.setCellValueFactory(new PropertyValueFactory<>("displayThursday"));
        displayFriday.setCellValueFactory(new PropertyValueFactory<>("displayFriday"));
        displaySaturday.setCellValueFactory(new PropertyValueFactory<>("displaySaturday"));

        getName.setCellValueFactory((new PropertyValueFactory<>("name")));
        getPositionTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        getTaskAssigned.setCellValueFactory(new PropertyValueFactory<>("taskAssigned"));

        displayWeeklySchedule();
        displayTaskAssigned();
    }

    @FXML
    private void displayWeeklySchedule(){

        WeeklyScheduleList.clear();

        String displaySchedule = "SELECT Firstname, WeekOf, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday FROM Schedule";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(displaySchedule)){

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                String weekOf = resultSet.getString("WeekOf");
                weekOfLabel.setText(weekOf);

                String employeeName = resultSet.getString("Firstname");
                String displaySunday = resultSet.getString("Sunday");
                String displayMonday = resultSet.getString("Monday");
                String displayTuesday = resultSet.getString("Tuesday");
                String displayWednesday = resultSet.getString("Wednesday");
                String displayThursday = resultSet.getString("Thursday");
                String displayFriday = resultSet.getString("Friday");
                String displaySaturday = resultSet.getString("Saturday");

                WeeklyScheduleList.add(new WeeklySchedule(employeeName, displaySunday, displayMonday, displayTuesday, displayWednesday, displayThursday, displayFriday, displaySaturday));
            }
            viewAdminSchedule.setItems(WeeklyScheduleList);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void displayTaskAssigned(){

        TaskAssignedList.clear();

        String displaySchedule = "SELECT Firstname, Title, TaskName FROM Schedule";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(displaySchedule)){

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String getName = resultSet.getString("Firstname");
                String getPositionTitle = resultSet.getString("Title");
                String getTaskAssigned = resultSet.getString("TaskName");

                TaskAssignedList.add(new TaskAssigned(getName, getPositionTitle, getTaskAssigned));
            }
            viewTaskAssigned.setItems(TaskAssignedList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminTimeDisplay() throws IOException {
        Stage stage = (Stage) adminTimeDisplay.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminTimeDisplay.fxml");
    }

    @FXML
    private void handleCreateAnnouncements() throws IOException{
        Stage stage = (Stage) adminAnnouncements.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "CreateAnnouncement.fxml");
    }

    @FXML
    private void handleManageAccounts() throws IOException{
        Stage stage = (Stage) account.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "ManageAccounts.fxml");
    }

    @FXML
    private void handleSetAvailability() throws IOException{
        Stage stage = (Stage) setAvailability.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminSetAvailability.fxml");
    }

    @FXML
    private void handleAdminPunchInOut() throws IOException{
        Stage stage = (Stage) punchInOut.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminPunchInOut.fxml");
    }

    @FXML
    private void handleBenefits() throws IOException{
        Stage stage = (Stage) benefits.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminBenefits.fxml");
    }

    @FXML
    private void handleAdminNotes() throws IOException{
        Stage stage = (Stage) notes.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminNotes.fxml");
    }

    @FXML
    private void handleViewEmployeeAvailability() throws IOException{
        Stage stage = (Stage) viewAvailability.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminViewAvailability.fxml");
        stage.setWidth(1000);
        stage.setHeight(600);
    }

    @FXML
    private void handleLogOut() throws IOException{
        CurrentUser.getInstance().setLoggedInUser(null);
        Stage stage = (Stage) logOut.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "LoginScreen.fxml");
    }

}
