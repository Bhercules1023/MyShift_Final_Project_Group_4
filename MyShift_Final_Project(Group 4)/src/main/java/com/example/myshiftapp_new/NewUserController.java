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

public class NewUserController {

    @FXML
    private Label messageLabel;

    @FXML
    private Button goBack;

    @FXML
    private TextField getNewUsername;

    @FXML
    private PasswordField getNewPassword;

    @FXML
    private PasswordField confirmNewPassword;

    @FXML
    private TextField getNewEmail;

    @FXML
    private TextField confirmNewEmail;

    @FXML
    private TextField getNewBirthday;

    @FXML
    private void handleCreateAccount() {
        String username = getNewUsername.getText();
        String password = getNewPassword.getText();
        String confirmPassword = confirmNewPassword.getText();
        String email = getNewEmail.getText();
        String confirmEmail = confirmNewEmail.getText();
        String birthday = getNewBirthday.getText();

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("PASSWORDS DO NOT MATCH!");
            return;
        }

        if (!email.equals(confirmEmail)) {
            messageLabel.setText("EMAILS DO NOT MATCH!");
            return;
        }

        String newEmployee = "INSERT INTO Employees(username, password, birthday, Email) VALUES(?, ?, ?, ?)";

        try (Connection connect = new DatabaseConnection().getConnection();
             PreparedStatement statement = connect.prepareStatement(newEmployee)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, birthday);
            statement.setString(4, email);

            int newUser = statement.executeUpdate();

            if (newUser > 0) {
                messageLabel.setText("Successfully Created Account!");
            } else {
                messageLabel.setText("Failed to Create Account!");
            }

            getNewUsername.clear();
            getNewPassword.clear();
            confirmNewPassword.clear();
            getNewEmail.clear();
            confirmNewEmail.clear();
            getNewBirthday.clear();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        MyShiftApplication.switchScene(stage, "LoginScreen.fxml");
    }

}
