package com.example.myshiftapp_new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManageAccountsController {

    @FXML
    private Button goBackAdmin;

    @FXML
    private Button manageEmpAccount;

    @FXML
    private Button manageAdminAccount;

    @FXML
    private ListView<String> activeUsers;

    private ObservableList<String> ActiveUsersList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        displayCurrentUsers();
        activeUsers.setItems(ActiveUsersList);
    }

    @FXML
    private void displayCurrentUsers(){

        String currentUsers = "SELECT username FROM Employees";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(currentUsers)){

            ResultSet resultSet = statement.executeQuery();

            ActiveUsersList.clear();

            while (resultSet.next()){
                String employeeName = resultSet.getString("username");

                String currentUser = employeeName;
                ActiveUsersList.add(currentUser);
            }
            activeUsers.setItems(ActiveUsersList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminAccount() throws IOException {
        Stage stage = (Stage) manageAdminAccount.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminAccount.fxml");
    }

    @FXML
    private void handleEmployeeAccount() throws IOException {
        Stage stage = (Stage) manageEmpAccount.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "ManageEmpAccount.fxml");
    }

    @FXML
    private void handleAdminGoBack() throws IOException {
        Stage stage = (Stage) goBackAdmin.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "AdminMainScreen.fxml");
    }
}
