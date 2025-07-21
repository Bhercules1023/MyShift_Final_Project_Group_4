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

public class ManageEmpAccountController {

    @FXML
    private Button updateEmpAccount;

    @FXML
    private TextField newUsername;

    @FXML
    private TextField getEmail;

    @FXML
    private TextField getBirthday;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Label errorLabel;

    @FXML
    private void resetUsername() throws IOException {
        String newUserName = newUsername.getText();
        String empEmail = getEmail.getText();
        String empBirthday = getBirthday.getText();

        String updateUsername = "UPDATE Employees SET username = ? WHERE Email = ? AND birthday = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(updateUsername)){

            statement.setString(1, newUserName);
            statement.setString(2, empEmail);
            statement.setString(3, empBirthday);

            int updated = statement.executeUpdate();

            newUsername.clear();
            getEmail.clear();
            getBirthday.clear();

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
    private void resetPassword() throws IOException {
        String newPassWord = newPassword.getText();
        String empEmail = getEmail.getText();
        String empBirthday = getBirthday.getText();

        String updatePassword = "UPDATE Employees SET password = ? WHERE Email = ? AND birthday = ?";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(updatePassword)){

            statement.setString(1, newPassWord);
            statement.setString(2, empEmail);
            statement.setString(3, empBirthday);

            int update = statement.executeUpdate();

            newPassword.clear();
            getEmail.clear();
            getBirthday.clear();

            if(update > 0){
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
    private void handleAccountUpdate() throws IOException {
        Stage stage = (Stage) updateEmpAccount.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "ManageAccounts.fxml");
    }

}
