package com.example.myshiftapp_new;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminAccountController {

    @FXML
    private TextField currentUsername;

    @FXML
    private TextField newUsername;

    @FXML
    private PasswordField currentPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private TextField currentEmail;

    @FXML
    private TextField newEmail;

    @FXML
    private Button goBackAdmin;

    @FXML
    private Label errorLabel;


    @FXML
    private void updateAdminUsername() throws IOException{
        String currentUserName = currentUsername.getText();
        String newUserName = newUsername.getText();

        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        String updateUsername = "UPDATE Management SET adminUsername = ? WHERE ID = ? AND adminUsername = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(updateUsername)){

            statement.setString(1, newUserName);
            statement.setInt(2, currentUser.getID());
            statement.setString(3,currentUserName);

            int updated = statement.executeUpdate();

            currentUsername.clear();
            newUsername.clear();

            if(updated > 0){
                errorLabel.setText("Username Successfully Updated!");
            }
            else{
                errorLabel.setText("Failed to Update Username!");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void updateAdminPassword() throws IOException {
        String currentPassWord = currentPassword.getText();
        String newPassWord = newPassword.getText();

        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        String updatePassword = "UPDATE Management SET adminPassword = ? WHERE ID = ? AND adminPassword = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(updatePassword)){

            statement.setString(1, newPassWord);
            statement.setInt(2, currentUser.getID());
            statement.setString(3,currentPassWord);

            int updated = statement.executeUpdate();

            currentPassword.clear();
            newPassword.clear();

            if(updated > 0){
                errorLabel.setText("Password Successfully Updated!");
            }
            else{
                errorLabel.setText("Failed to Update Password!");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void updateAdminEmail() throws IOException {
        String current_Email = currentEmail.getText();
        String new_Email = newEmail.getText();

        User currentUser = CurrentUser.getInstance().getLoggedInUser();

        String updateEmail = "UPDATE Management SET Email = ? WHERE ID = ? AND Email = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(updateEmail)){

            statement.setString(1, new_Email);
            statement.setInt(2, currentUser.getID());
            statement.setString(3,current_Email);

            int updated = statement.executeUpdate();

            currentEmail.clear();
            newEmail.clear();

            if(updated > 0){
                errorLabel.setText("Email Successfully Updated!");
            }
            else{
                errorLabel.setText("Failed to Update Email!");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminGoBack() throws IOException {
        Stage stage = (Stage) goBackAdmin.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "ManageAccounts.fxml");
    }

}
